package com.newcore.bmp.service.impl.errorMonitor;



import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newcore.bmp.dao.api.email.EmailDao;
import com.newcore.bmp.dao.api.errorMonitor.ErrorMonitorDao;
import com.newcore.bmp.models.errorJudge.ErrorDefine;
import com.newcore.bmp.models.errorJudge.ErrorTrail;
import com.newcore.bmp.models.errorJudge.SelectField;
import com.newcore.bmp.service.api.errorMonitor.ErrorMonitorService;

@Service("errorMonitorService")
public class ErrorMonitorServiceImpl implements ErrorMonitorService {
	@Autowired
	ErrorMonitorDao errorMonitorDao;

	@Autowired
	EmailDao emailDao;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ErrorMonitorServiceImpl.class);

	
	// 异常判定逻辑
	public void errorJudging(String system) {
		// add
		List<ErrorTrail> resET_has_where_clause = new ArrayList<ErrorTrail>();
		List<ErrorTrail> resET_has_not_where_clause = new ArrayList<ErrorTrail>();
		// add

		// 取出"异常规则定义表"的所有记录到内存中
		List<ErrorDefine> resED = errorMonitorDao.GetErrorDefine(system);

		// “异常规则”记录遍历
		int sizeED = resED.size();
		for (int i = 0; i < sizeED; i++) {
			ErrorDefine recordED = resED.get(i);
			// System.out.println(recordED.getSelfDescription());
			LOGGER.info("第"+i+"个异常规则="+recordED);
			
			// 取异常规则对应的异常记录的集合
			List<SelectField> resSF = errorMonitorDao.SelectField(system, recordED.getErrReasonDetail(),
					recordED.getErrJudgeCondition());

			// 遍历异常记录
			int sizeSF = resSF.size();
			for (int j = 0; j < sizeSF; j++) {

				SelectField recordSF = resSF.get(j);
				// System.out.println(recordSF.getSelfDescription());
				LOGGER.info("errorJudging第"+i+"个异常记录="+recordSF);
				

				// 根据（机构号，批作业编号，执行条件，异常编号），到ERROR_TRAIL表中查相应记录

				List<ErrorTrail> resET = errorMonitorDao.FindInErrorTrail(system, recordSF.getProvBranchCode(),
						recordSF.getBatchTxNo(), recordSF.getWhereClause(), recordED.getErrReasonId(),
						recordSF.getStartExecTime());

				int sizeET = resET.size();

				// System.out.println("sizeET"+resET.size());
				LOGGER.info("异常记录在异常轨迹表中的记录数="+resET.size());

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

			// 根据（机构号，批作业ID，执行条件，异常原因ID,开始时间），批量 在异常轨迹表中更新异常消除标志位，异常消除时间
			errorMonitorDao.updateErrorTrail(resET_has_where_clause, resET_has_not_where_clause);
		}
	}

	// 异常消除逻辑
	public void errorEliminate(String system) {

		List<ErrorTrail> resET_has_where_clause = new ArrayList<ErrorTrail>();
		List<ErrorTrail> resET_has_not_where_clause = new ArrayList<ErrorTrail>();

		// 取出没有异常消除的结果集
		List<ErrorTrail> resET = errorMonitorDao.GetErrorRecord(system);

		// 遍历异常记录
		int sizeET = resET.size();
		// System.out.println("sizeET="+sizeET);
		LOGGER.info("errorEliminate异常记录数="+sizeET);

		for (int i = 0; i < sizeET; i++) {
			ErrorTrail recordET = resET.get(i);
			// System.out.println(recordET.getSelfDescription());
			LOGGER.info("errorEliminate第"+i+"个异常记录="+recordET);


			// 根据（机构号，批作业ID，执行条件，异常原因ID），where 条件=
			// 异常消除条件，到monitor_queue表中查找是否有记录？
			int countInMQ = errorMonitorDao.FindInMonitorQueue(system, recordET.getProvBranchCode(),
					recordET.getBatchTxNo(), recordET.getWhereClause(), recordET.getErrEliminateCondition(),
					recordET.getErrReasonId(), recordET.getStartExecTime(),recordET.getErrReasonDetail());

			// System.out.println("countInMQ="+countInMQ);
			LOGGER.info("errorEliminate第"+i+"个异常记录在monitor_queue中满足消除条件的记录数="+recordET);
	
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
