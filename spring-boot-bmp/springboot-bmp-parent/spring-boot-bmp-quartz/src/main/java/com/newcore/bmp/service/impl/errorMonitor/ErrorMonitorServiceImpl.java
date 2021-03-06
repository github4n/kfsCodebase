package com.newcore.bmp.service.impl.errorMonitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newcore.bmp.dao.api.errorMonitor.ErrorMonitorDao;
import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.models.errorJudge.ErrorDefine;
import com.newcore.bmp.models.errorJudge.ErrorTrail;
import com.newcore.bmp.models.errorJudge.SelectField;
import com.newcore.bmp.service.api.email.EmailService;
import com.newcore.bmp.service.api.errorMonitor.ErrorMonitorService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Service("errorMonitorService")
@Slf4j
public class ErrorMonitorServiceImpl implements ErrorMonitorService {
	@Autowired
	ErrorMonitorDao errorMonitorDao;

	@Autowired
	EmailService emailService;

	@Value("${errorMonitorFlag:true}")
	boolean errorMonitorFlag;

	// 异常判定
	public void errorJudge(String system) {

		if (!errorMonitorFlag) {
			return;
		}

		List<ErrorTrail> resET_has_where_clause = new ArrayList<ErrorTrail>();
		List<ErrorTrail> resET_has_not_where_clause = new ArrayList<ErrorTrail>();

		// 取出"异常规则定义表"的所有记录到内存中
		List<ErrorDefine> resED = errorMonitorDao.GetErrorDefine(system);

		// “异常规则”记录遍历
		int sizeED = resED.size();
		for (int i = 0; i < sizeED; i++) {
			ErrorDefine recordED = resED.get(i);
			log.info("第" + i + "个异常规则=" + recordED);

			// 取异常规则对应的异常记录的集合
			List<SelectField> resSF = errorMonitorDao.SelectField(system, recordED.getErrReasonDetail(),
					recordED.getErrJudgeCondition());

			// 遍历异常记录
			int sizeSF = resSF.size();
			for (int j = 0; j < sizeSF; j++) {

				SelectField recordSF = resSF.get(j);
				log.info("第" + i + "个异常规则的" + "第" + j + "个异常记录=" + recordSF);

				// 根据（机构号，批作业编号，执行条件，异常编号），到ERROR_TRAIL表中查相应记录
				List<ErrorTrail> resET = errorMonitorDao.FindInErrorTrail(system, recordSF.getProvBranchCode(),
						recordSF.getBatchTxNo(), recordSF.getWhereClause(), recordED.getErrReasonId(),
						recordSF.getStartExecTime());

				int sizeET = resET.size();
				log.info("异常记录在异常轨迹表中的记录数=" + resET.size());

				if (sizeET == 0) { // 异常轨迹表中没记录
					// 将异常记录插入异常轨迹表
					errorMonitorDao.InsertErrorTrail(system, recordSF, recordED);
				} else { // 异常轨迹表中有相应记录

					// 取出异常轨迹
					ErrorTrail recordET = resET.get(0);

					// 放到数组里保存
					if (recordET.getWhereClause() == null) {
						resET_has_not_where_clause.add(recordET);
					} else {
						resET_has_where_clause.add(recordET);
					}
				}

			}

			// 根据（机构号，批作业ID，执行条件，异常原因ID,开始时间）,批量 在异常轨迹表中更新异常消除标志位，异常消除时间
			errorMonitorDao.updateErrorTrail(resET_has_where_clause, resET_has_not_where_clause);
		}
	}

	// 针对异常的出单批作业，发送邮件，短信告警
	public void chudanBatchWarning(String system) {
///*				
		// 取出轨迹表中的数据
		List<ErrorDefine> edList = errorMonitorDao.selectChudanErrorReasonId(system);
		log.info("edList.size()= " + edList.size());

		// Iterator<ErrorDefine> edIt = edList.iterator();
		// while(edIt.hasNext()) {
		for (int i = 0; i < edList.size(); i++) {
			// ErrorDefine ed = (ErrorDefine) edIt.next();
			ErrorDefine ed = edList.get(i);

			// temp
			if (ed.getErrReasonDetail().equals("批作业启动后没有处理任何数据")
					|| ed.getErrReasonDetail().equals("日间批作业在计划运行时间内异常终止")) {
				log.info(ed.getErrReasonDetail() + "pass");
				continue;
			}
			// temp

			List<ErrorTrail> etList = errorMonitorDao.selectChudanErrorRecord(system, ed.getErrReasonId(),
					ed.getMonitorIntervalCount());
			log.info("etList.size()=" + etList.size());

			Iterator<ErrorTrail> etIt = etList.iterator();
			while (etIt.hasNext()) {
				ErrorTrail et = etIt.next();

				//如果是只发一次的异常，并且已经发送过一次，则跳过发邮件
				//log.info(et.toString());

				if (et.getOnlyMailOnceFlag().equals("1") 
						&& Integer.parseInt(et.getErrJudgeCount()) > 1) {

					continue;
				}


				
				//发邮件
				this.prepareAndSendChudanWarningMailThroughNP(system, et);
			}
		}

	}

	private void prepareAndSendChudanWarningMailThroughNP(String system, ErrorTrail et) {

		// 准备要发送的邮件，短信内容
		String title = "作业管理平台告警";

		// 获取text内容
		String batchId = et.getBatchTxNo();
		String lineBatchId = "批作业ID: " + batchId;

		String batchName = et.getBatchName();
		String lineBatchName = "批作业名称: " + batchName;

		String provName = et.getProvBranchName();
		String provCode = et.getProvBranchCode();
		String lineProvName = "省份: " + provName + "(" + provCode + ")";

		String error = et.getErrReasonDetail();
		String lineError = "异常: " + error;

		String interval = et.getStartExecTime() + " ~ " + DateUtil.now();
		String lineInterval = "检测区间: " + interval;

		// 拼接手机text
		StringBuffer mobile_text_sb = new StringBuffer();
		String mobileLineSeparator = "\n";
		mobile_text_sb.append(lineBatchId).append(mobileLineSeparator).append(lineBatchName).append(mobileLineSeparator)
				.append(lineProvName).append(mobileLineSeparator).append(lineError).append(mobileLineSeparator).append(lineInterval)
				.append(mobileLineSeparator);
		String mobileText = "[作业管理平台监控告警]" + mobileLineSeparator;
		mobileText = mobileText + "系统: " + system + mobileLineSeparator;
		mobileText = mobileText + mobile_text_sb.toString();

		//拼接邮件text
		StringBuffer email_text_sb = new StringBuffer();
		String emailLineSeparator = "<br>";
		email_text_sb.append(lineBatchId).append(emailLineSeparator).append(lineBatchName).append(emailLineSeparator)
				.append(lineProvName).append(emailLineSeparator).append(lineError).append(emailLineSeparator).append(lineInterval)
				.append(emailLineSeparator);
		String emailText = "系统: " + system + emailLineSeparator;
		emailText = emailText + email_text_sb.toString();

		// 获取收件人
		StringBuffer phone_sb = new StringBuffer();
		StringBuffer email_sb = new StringBuffer();
		StringBuffer copyMail_sb = new StringBuffer();
		String separator = ",";
		List<EmailSubscription> listEmailSubscription = emailService.SelectEmailSubscriptionOfChudan(system);
		Iterator<EmailSubscription> iterator = listEmailSubscription.iterator();
		while (iterator.hasNext()) {
			EmailSubscription es = iterator.next();
			// log.info(es.toString());

			// 拼接电话号
			phone_sb.append(es.getTel()).append(separator);

			if (es.getType().equals("TO")) {
				// 拼接收件人
				email_sb.append(es.getEmailAddress()).append(separator);

			} else if (es.getType().equals("CC")) {
				// 拼接抄送人
				copyMail_sb.append(es.getEmailAddress()).append(separator);
			} else {
				// do nothing
			}
		}

		// 拼接电话号
		if (phone_sb.length() > 0) {
			phone_sb = phone_sb.deleteCharAt(phone_sb.length() - 1);
		}
		String phone = phone_sb.toString();
		log.info("phone = " + phone);
		
		// 拼接收件人
		if (email_sb.length() > 0) {
			email_sb = email_sb.deleteCharAt(email_sb.length() - 1);

		}
		String email = email_sb.toString();
		log.info("email = " + email);

		// 拼接抄送人
		if (copyMail_sb.length() > 0) {
			copyMail_sb = copyMail_sb.deleteCharAt(copyMail_sb.length() - 1);
		}
		String copyMail = copyMail_sb.toString();
		log.info("copyMail = " + copyMail);

		
		// 拼接密送人
		String blindMail = "";

		// 拼接云助理Id
		String cloudId = "";

		// 拼接时间区间
		String opDate = DateUtil.now();

		// 通过通知中心发送邮件
		String operation = "MAIL";
		emailService.sendEmailThroughNotificationPlatform(title, mobileText, emailText, phone, email, copyMail, blindMail, cloudId,
				opDate, operation);
	}

	// 异常消除
	public void errorEliminate(String system) {

		if (!errorMonitorFlag) {
			return;
		}

		List<ErrorTrail> resET_has_where_clause = new ArrayList<ErrorTrail>();
		List<ErrorTrail> resET_has_not_where_clause = new ArrayList<ErrorTrail>();

		// 取出没有异常消除的结果集
		List<ErrorTrail> resET = errorMonitorDao.GetErrorRecord(system);

		// 遍历异常记录
		int sizeET = resET.size();
		log.info("errorEliminate异常记录数=" + sizeET);

		for (int i = 0; i < sizeET; i++) {
			ErrorTrail recordET = resET.get(i);
			log.info("errorEliminate第" + i + "个异常记录=" + recordET);

			// 根据（机构号，批作业ID，执行条件，异常原因ID），where 条件=异常消除条件，到monitor_queue表中查找是否有记录
			int countInMQ = errorMonitorDao.FindInMonitorQueue(system, recordET.getProvBranchCode(),
					recordET.getBatchTxNo(), recordET.getWhereClause(), recordET.getErrEliminateCondition(),
					recordET.getErrReasonId(), recordET.getStartExecTime(), recordET.getErrReasonDetail());

			log.info("errorEliminate第" + i + "个异常记录在monitor_queue中满足消除条件的记录数=" + countInMQ);

			if (countInMQ == 0) { // MONITOR_QUEUE中该条记录没有符合异常消除条件
				// do nothing
				continue;
			} else { // MONITOR_QUEUE中该条记录符合异常消除条件

				// 放到数组里保存
				if (recordET.getWhereClause() == null) {
					resET_has_not_where_clause.add(recordET);
				} else {
					resET_has_where_clause.add(recordET);
				}

			}
		}

		// 根据（机构号，批作业ID，执行条件，异常原因ID,开始时间），批量 在异常轨迹表中更新异常消除标志位，异常消除时间
		errorMonitorDao.eliminateErrorTrail(resET_has_where_clause, resET_has_not_where_clause);
	}

}
