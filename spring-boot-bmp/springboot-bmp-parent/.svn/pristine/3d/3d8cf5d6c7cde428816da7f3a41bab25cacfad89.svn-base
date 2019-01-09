package com.newcore.bmp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.newcore.bmp.models.webvo.RunningBatchVo;
import com.newcore.bmp.models.webvo.ServerInfo;
import com.newcore.bmp.models.webvo.SystemCommandDef;
import com.newcore.bmp.models.webvo.ZongKongBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcraft.jsch.JSchException;
import com.newcore.bmp.dao.api.TaskRunMonitorDao;
import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.errorJudge.ErrorDefine;
import com.newcore.bmp.models.webvo.BatchVO;
import com.newcore.bmp.models.webvo.DatabaseConnection;
import com.newcore.bmp.models.webvo.FailBatchVo;
import com.newcore.bmp.models.webvo.JsonFirstLayer;
import com.newcore.bmp.models.webvo.JsonResponce;
import com.newcore.bmp.models.webvo.JsonSecondLayer;
import com.newcore.bmp.models.webvo.MapBatch;
import com.newcore.bmp.models.webvo.QueryBatch;
import com.newcore.bmp.models.webvo.QueryCond;
import com.newcore.bmp.models.webvo.QueryServer;
import com.newcore.bmp.service.api.TaskRunMonitor;
import com.newcore.bmp.service.impl.httpclient.HttpUtil;
import com.newcore.bmp.service.impl.ssh.JschUtil;

@Service("taskRunMonitor")
public class TaskRunMonitorImpl implements TaskRunMonitor {

	/**
	 * 日志记录
	 */
	private final Logger logger = LoggerFactory.getLogger(TaskRunMonitorImpl.class);

	@Autowired
	TaskRunMonitorDao taskRunMonitorDao;

	public Map<String, Object> GetFailBatchList(QueryCond qc) {
		return taskRunMonitorDao.GetFailBatchList(qc.getTopic(), qc.getProvCode(), qc.getBatchId(),
				qc.getBatchTypeDetail(), qc.getErrScale(), qc.getErrReasonId(), qc.getOrder(), qc.getLength(),
				qc.getStart());
	}

	public Map<String, Object> GetRunningBatchList(QueryCond qc) {
		return taskRunMonitorDao.GetRunningBatchList(qc.getTopic(), qc.getProvCode(), qc.getBatchId(),
				qc.getBatchTypeDetail(), qc.getOrder(), qc.getLength(), qc.getStart());
	}

	// 根据 “机构号”条件，得到“机构名称”条件
	public List<RunningBatchVo> GetProNameByProCode(QueryCond qc) {
		return taskRunMonitorDao.GetProNameByProCode(qc.getProvCode());
	}

	// case3 根据 “机构名称”条件，得到“机构号”条件
	public List<RunningBatchVo> GetProCodeByProName(QueryCond qc) {
		return taskRunMonitorDao.GetProCodeByProName(qc.getProName());
	}

	// 1.1.2.4 case4 根据 “作业id”条件，得到“作业名称”条件
	public List<RunningBatchVo> GetBatchNameByBatchId(QueryCond qc) {
		return taskRunMonitorDao.GetBatchNameByBatchId(qc.getTopic(), qc.getBatchId());
	}

	// 1.1.2.5 case5 根据 “作业名称”条件，得到“作业id”条件
	public List<RunningBatchVo> GetBatchIdByBatchName(QueryCond qc) {
		return taskRunMonitorDao.GetBatchIdByBatchName(qc.getTopic(), qc.getBatchName());
	}

	// 1.1.2.6 case6 根据“业务系统分类”，得到“作业id”,”作业名称”列表（未实现）
	public List<RunningBatchVo> GetBatchIdAndBatchNameByTopicAndBatchType(QueryCond qc) {
		return taskRunMonitorDao.GetBatchIdAndBatchNameByTopicAndBatchType(qc.getTopic(), qc.getBatchTypeDetail());
	}

	public List<FailBatchVo> GetErrReasonByTopicAndErrScale(QueryCond qc) {
		return taskRunMonitorDao.GetErrReasonByTopicAndErrScale(qc.getTopic(), qc.getErrScale());
	}

	public List<BatchVO> GetProvinceMapping(QueryCond qc) {
		return taskRunMonitorDao.GetProvinceMapping(qc.getProvCode());
	}

	public Map<String, Object> GetHistoryBatchList(QueryCond qc) {
		return taskRunMonitorDao.GetHistoryBatchList(qc.getTopic(), qc.getProvCode(), qc.getBatchId(),
				qc.getBatchTypeDetail(), qc.getOrder(), qc.getLength(), qc.getStart(), qc.getExecStat(),
				qc.getStartExecTime(), qc.getEndExecTime());
	}

	@Transactional()
	public boolean insertBatch(QueryBatch qc) {
//		return taskRunMonitorDao.insertBatch(qc);
		boolean result1 = taskRunMonitorDao.insertBatchDef(qc);
		logger.info("result1 = " + result1);

		String id = taskRunMonitorDao.selectPerticularBatchId(qc);
		logger.info("id = " + id);

		qc.setId(id);

		boolean result2 = taskRunMonitorDao.insertBatchCommandDef(qc);
		logger.info("result2 = " + result2);

		return result2;
	}

	@Transactional()
	public boolean updateBatch(QueryBatch qc) {
//		return taskRunMonitorDao.updateBatch(qc);
		boolean result1 = taskRunMonitorDao.updateBatchDef(qc);
		logger.info("result1 = " + result1);

		if (taskRunMonitorDao.getBatchCommandCount(qc) == 0) {
			boolean result2 = taskRunMonitorDao.insertBatchCommandDef(qc);
			logger.info("result2 = " + result2);
			return result2;
		} else {
			boolean result2 = taskRunMonitorDao.updateBatchCommandDef(qc);
			logger.info("result2 = " + result2);
			return result2;

		}
	}

	@Transactional()
	public boolean deleteBatch(QueryBatch qc) {
//		return taskRunMonitorDao.deleteBatch(qc.getId());
		boolean result1 = taskRunMonitorDao.deleteBatchDef(qc.getId());
		logger.info("result1 = " + result1);

		boolean result2 = taskRunMonitorDao.deleteBatchCommandDef(qc.getId());
		logger.info("result2 = " + result2);

		return result2;
	}

	public Map<String, Object> selectBatch(QueryBatch qc) {
		return taskRunMonitorDao.selectBatch(qc.getTopic(), qc.getBatchTypeDetail(), qc.getBatchId(), qc.getOrder(),
				qc.getLength(), qc.getStart());
	}

	public Map<String, Object> getZongKongBatches(QueryCond qc) {
		Map<String, Object> amap = new HashMap<String, Object>();

		// select批作业
		List<ZongKongBatch> results = taskRunMonitorDao.getBatches(qc.getTopic(), qc.getBatchTypeDetail(),
				qc.getOrder());

		// “异常规则”记录遍历
		int size = results.size();
		for (int i = 0; i < size; i++) {
			// 取出一条记录
			ZongKongBatch batch = results.get(i);

			if (qc.getBatchTypeDetail().equals("日间作业")) { // 日间
				// 计算”运行作业数量”
				int runningCount = taskRunMonitorDao.getRunningCount(qc.getTopic(), qc.getProvCode(),
						batch.getBatchId());
				batch.setRunningCount(String.valueOf(runningCount));

				// 计算”运行状态”
				String runningState = runningCount > 0 ? "正在运行" : "停止";
				batch.setRunningState(runningState);

				// 计算”异常数量”
				int errorCount = taskRunMonitorDao.getErrorCount(qc.getTopic(), qc.getProvCode(), batch.getBatchId());
				batch.setErrorCount(String.valueOf(errorCount));

				// 计算”运行状态”
				String errorState = errorCount > 0 ? "是" : "否";
				batch.setErrorState(errorState);

				// 赋值topic,province_code,batchType
				batch.setTopic(qc.getTopic());
				batch.setProvCode(qc.getProvCode());
				batch.setBatchTypeDetail(qc.getBatchTypeDetail());
//				batch.setBatchType(String.valueOf(qc.getBatchType()));

			} else if (qc.getBatchTypeDetail().equals("日终作业")) { // 日终
				// 计算”运行作业数量” 和 ”运行状态”
				int runningCount = taskRunMonitorDao.getRunningCount(qc.getTopic(), qc.getProvCode(),
						batch.getBatchId());
				if (runningCount > 0) { // 运行数量>0
					batch.setRunningCount(String.valueOf(runningCount));
					batch.setRunningState("正在运行");

				} else {// 运行数量=0
					int todayStoppedCount = taskRunMonitorDao.getTodayStoppedCount(qc.getTopic(), qc.getProvCode(),
							batch.getBatchId());
					batch.setRunningCount(String.valueOf(todayStoppedCount));
					batch.setRunningState(todayStoppedCount > 0 ? "已运行" : "未运行");

				}

				// 计算”异常数量”
				int errorCount = taskRunMonitorDao.getErrorCount(qc.getTopic(), qc.getProvCode(), batch.getBatchId());
				batch.setErrorCount(String.valueOf(errorCount));

				// 计算”运行状态”
				String errorState = errorCount > 0 ? "是" : "否";
				batch.setErrorState(errorState);

				// 赋值topic,province_code,batchType
				batch.setTopic(qc.getTopic());
				batch.setProvCode(qc.getProvCode());
//				batch.setBatchType(String.valueOf(qc.getBatchType()));
				batch.setBatchTypeDetail(qc.getBatchTypeDetail());

			} else { // 其他类型批作业

			}

			results.set(i, batch);

		}
		amap.put("data", results);

		return amap;
	}

	// 远程ssh连接服务器，执行命令
	public Map<String, Object> runBatch(QueryBatch qc)  {
		// JschUtil jschUtil = JschUtil.getInstance();

		String command = qc.getCommand();
		// 根据IP取出对应的port和username
		String port = taskRunMonitorDao.getPortByServerInfo(qc.getTopic(), qc.getProvCode(), qc.getIp());
		String userName = taskRunMonitorDao.getUserNameByServerInfo(qc.getTopic(), qc.getProvCode(), qc.getIp());

		JschUtil jschUtil = new JschUtil();
		jschUtil.setJschHost(qc.getIp());
		jschUtil.setJschPort(Integer.parseInt(port));
		jschUtil.setJschUserName(userName);

		boolean isConnected = false;
		try {
			isConnected = jschUtil.connect();
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (isConnected == true) {

			/* 执行命令 */
			Map<String, Object> result = null;

			try {
				result = jschUtil.execCmmmand(command);

				// System.out.println(result.get("result").toString());
				// System.out.println(result.get("returnCode").toString());

				// 把批作业信息附加在result上
				result.put("batchId", qc.getBatchId());
				result.put("batchName", qc.getBatchName());
				result.put("provCode", qc.getProvCode());
				result.put("topic", qc.getTopic());
				result.put("ip", qc.getIp());
				result.put("command", qc.getCommand());

				jschUtil.close();

				return result;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	// 通过调云运维平台执行批作业
	@SuppressWarnings("finally")
	public Map<String, Object> runBatchThroughPlatform(QueryBatch qc) throws Exception {

		// 获取数据库名字，密码，实例名
		DatabaseConnection databaseConnection = taskRunMonitorDao.getDatabaseInfo(qc.getTopic(), qc.getProvCode());
		logger.info("databaseConnection = " + databaseConnection);
		String databaseName = databaseConnection.getDatabaseName();
		String databasePassword = databaseConnection.getDatabasePassword();
		String instanceName = databaseConnection.getInstanceName();

		// 获取batchId,company, taskId
		String batchId = qc.getBatchId();
		String company = qc.getCompany();
		String taskId = qc.getTaskId();
		logger.info("batchId" + batchId);
		logger.info("company" + company);
		logger.info("taskId" + taskId);

		// 读取command
		SystemCommandDef systemCommandDef = taskRunMonitorDao.getSystemCommandDef(qc.getTopic(), qc.getProvCode());
		String command = systemCommandDef.getHaltCommand();
		logger.info("original command = " + command);

		// 替换command
		command = command.replaceAll("@JOBID@", batchId);
		command = command.replaceAll("@DATABASENAME@", databaseName);
		command = command.replaceAll("@DATABASEPASSWORD@", databasePassword);
		command = command.replaceAll("@INSTANCENAME@", instanceName);
		command = command.replaceAll("@COMPANY@", company);
		command = command.replaceAll("@TASKID@", taskId);
		logger.info("after changed, command = " + command);

		// 准备command
		/*
		 * String command = qc.getCommand(); command =
		 * "#停止批作业\n\nOS=`uname`  \nif [ \"$OS\" != \"Linux\" ];then \n. $HOME/.profile \nelse \n. $HOME/.bash_profile \nfi \n\n. $HOME/batch/env.sh\n\n\nexport LOGDIR=$HOME/batch/log\nexport datestring=`date \"+%Y-%m-%d\"`\nexport datetime=`date +\"%Y%m%d%H%M%S\"`\n\nif [ ! -d  $LOGDIR ];then\n   mkdir -p $HOME/batch/log\nfi\n\nJOBID=@JOBID@\nDATABASENAME=@DATABASENAME@\nDATABASEPASSWORD=@DATABASEPASSWORD@\nINSTANCENAME=@INSTANCENAME@\nCOMPANY=@COMPANY@\nTASKID=@TASKID@\n\n#STAFFNO=@STAFFNO@\n#BRANCH=@BRANCH@\n\n#temp\nJOBID=872990\nDATABASENAME=BJCBPSPCL\nDATABASEPASSWORD=RpV8kuT1\nINSTANCENAME=BJCBPS\nCOMPANY=88\nTASKID=2718082\n#temp\n\necho $JOBID\necho $DATABASENAME\necho $DATABASEPASSWORD\necho $INSTANCENAME\necho $COMPANY\necho $STAFFNO\necho $BRANCH\necho $TASKID\n\n#判断MONITOR_QUEUE是否在运行\nval=`sqlplus -s $DATABASENAME/$DATABASEPASSWORD@$INSTANCENAME<<EOF    \n\nset heading off;\nset echo off;\nset verify off;\nset pagesize 0;\n\ncol coun new_value v_coun\n\nselect count(*) coun from  monitor_queue\nwhere to_char(exec_stat) = '1'\nand to_char(batch_tx_no) = '$JOBID'\nand to_char(task_id) = '$TASKID'\n;\n\nexit v_coun\n\nEOF`\n\nvalue=\"$?\"\necho \"value=$value\"\n\nif [ $value -eq  0 ];then\n\n\necho  \"$JOBID 的'TASKID=$TASKID'的保单已经处于停止状态,无需停止!\" >> $LOGDIR/${JOBID}.log.${datestring}\necho  \"$JOBID 的'TASKID=$TASKID'的保单已经处于停止状态,无需停止!\" \n\nexit 1\nfi\n\necho \"$JOBID的'TASKID=$TASKID'的保单正在执行停止操作 start time [`date \"+%H%M%S\"`]\"  >>$LOGDIR/${JOBID}.log.${datestring}\n\n\nstopVal=`sqlplus -s $DATABASENAME/$DATABASEPASSWORD@$INSTANCENAME<<EOF    \n\nset heading off;\n\nset echo off;\n\nset verify off;\n\nset pagesize 0;\n\nupdate monitor_queue set exec_stat = 3 where  to_char(exec_stat)  = '1' and to_char(batch_tx_no) = '$JOBID' and to_char(task_id) = '$TASKID';\n\nquit;\n\nEOF`\n\necho \"stopVal=$stopVal\"\n\necho \"$JOBID的'TASKID=$TASKID'的保单成功停止 end time [`date \"+%H%M%S\"`]\"  >>$LOGDIR/${JOBID}.log.${datestring}\n\n\necho \"${JOBID}的'TASKID=$TASKID'的保单停止成功!\"\nexit 0\n\n#if [[ $stopVal != *update* ]];then\n#   echo \"$JOBID的'TASKID=$TASKID'的保单停止失败!\" >> $LOGDIR/${JOBID}.log.${datestring}\n#   echo \"$JOBID的'TASKID=$TASKID'的保单停止失败!\"\n#   exit 2 \n#else\n#   echo \"${JOBID}的'TASKID=$TASKID'的保单停止成功!\" >> $LOGDIR/${JOBID}.log.${datestring}\n#   echo \"${JOBID}的'TASKID=$TASKID'的保单停止成功!\"\n#   exit 0\n#fi\n"
		 * ; logger.info("fake command= "+command);
		 */

		// 获取ip
		String ip = taskRunMonitorDao.getServerIpByQueryInfo(qc.getTopic(), qc.getProvCode());
		logger.info("ip = " + ip);

		// 根据IP取出对应的username
		String userName = taskRunMonitorDao.getUserNameByServerInfo(qc.getTopic(), qc.getProvCode(), ip);
		logger.info("userName = " + userName);

		// url
		final String url = "http://10.21.39.61:8080/api/v1/runCmdByIps"; // 云运维平台的api

		// 拼请求的报文内容
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("runAs", userName);
		jsonObject.addProperty("command", command);

		// 拼ip jsonArray
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(ip);
		jsonObject.add("ips", jsonArray);

		logger.info("jsonObject.toString() = " + jsonObject.toString());

		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			// 向云运维平台发送http post请求
			String str_jsonResponse = HttpUtil.doPost(url, jsonObject.toString());
//			System.out.println("str_jsonResponse = "+str_jsonResponse);

			JsonResponce jsonResponce = new Gson().fromJson(str_jsonResponse, JsonResponce.class); // Json字符串转Java对象
//			System.out.println("jsonResponce.success = " + jsonResponce.getSuccess());

			// 发送http请求 获取日志
			final String obj_url = "http://10.21.39.61:8080/api/v1/viewBatchCmd"; // 云运维平台获取日志的api接口

			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("batchId", jsonResponce.getObj());
			logger.info("obj=" + jsonResponce.getObj());
			logger.info("jsonObject2.toString()=" + jsonObject2.toString());

			String endtime = null;
			JsonFirstLayer jsonFirstLayer = null;
			JsonSecondLayer jsonSecondLayer = null;
			while (endtime == null) {
//				Thread.sleep(1000);
				String str_obj_jsonResponse = HttpUtil.doPost(obj_url, jsonObject2.toString());

//	        	String str_obj_jsonResponse = HttpUtil.doPost(obj_url, obj_json_params);
				System.out.println(str_obj_jsonResponse);

				// json字符串转java对象
				jsonFirstLayer = new Gson().fromJson(str_obj_jsonResponse, JsonFirstLayer.class);
				// logger.info("jsonFirstLayer"+jsonFirstLayer);

				jsonSecondLayer = jsonFirstLayer.getObj().get(0);
				// logger.info("jsonSecondLayer"+jsonSecondLayer);

				endtime = jsonSecondLayer.getEndtime();
			}

			result.put("result", jsonSecondLayer.getMessage());
			result.put("returnCode", jsonSecondLayer.getReturncode());

			// 把批作业信息附加在result上
			result.put("batchId", qc.getBatchId());
			result.put("batchName", qc.getBatchName());
			result.put("provCode", qc.getProvCode());
			result.put("topic", qc.getTopic());
			result.put("ip", qc.getIp());
			result.put("command", qc.getCommand());

			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	// 通过调云运维平台执行批作业
	public Map<String, Object> stopBatch(QueryBatch qc) throws Exception {
		String taskId = null;
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取该批作业当前的task_id列表
		List<QueryBatch> taskIdList = taskRunMonitorDao.getTaskIdList(qc.getTopic(), qc.getProvCode(), qc.getBatchId());
		logger.info("taskIdList.size()=" + taskIdList.size());

		// 依次发请求
		for (int i = 0; i < taskIdList.size(); i++) {
			taskId = taskIdList.get(i).getTaskId();
			logger.info("taskIdList[" + i + "]=" + taskId);

			result = this.stopBatchBaseOnTaskId(qc, taskId);
			logger.info("result[" + i + "].returnCode=" + (String) result.get("returnCode"));
			// 记录结果 todo

		}
		result.put("result", "执行成功");
		result.put("returnCode", 0);

		return result;
	}

	// 通过调云运维平台执行批作业
	public Map<String, Object> stopSomeBatch(QueryBatch qc) throws Exception {
		String taskId = null;
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取该批作业当前的task_id列表
		List<String> taskIdList = Arrays.asList(qc.getTaskIdList());
		logger.info("taskIdList.size()=" + taskIdList.size());

		// 依次发请求
		for (int i = 0; i < taskIdList.size(); i++) {
			taskId = taskIdList.get(i);
			logger.info("taskIdList[" + i + "]=" + taskId);

			result = this.stopBatchBaseOnTaskId(qc, taskId);
			logger.info("result[" + i + "].returnCode=" + (String) result.get("returnCode"));
			// 记录结果 todo

		}
		result.put("result", "执行成功");
		result.put("returnCode", 0);

		return result;
	}

	// 根据taskId通过调云运维平台执行批作业
	public Map<String, Object> stopBatchBaseOnTaskId(QueryBatch qc, String taskId) throws Exception {

		// 得到taskId
		logger.info("taskId = " + taskId);

		// 获取数据库名字，密码，实例名
		DatabaseConnection databaseConnection = taskRunMonitorDao.getDatabaseInfo(qc.getTopic(), qc.getProvCode());
		logger.info("databaseConnection = " + databaseConnection);
		String databaseName = databaseConnection.getDatabaseName();
		String databasePassword = databaseConnection.getDatabasePassword();
		String instanceName = databaseConnection.getInstanceName();

		// 获取batchId,company,
		String batchId = qc.getBatchId();
		String company = qc.getCompany();
		logger.info("batchId" + batchId);
		logger.info("company" + company);

		// 读取command
		SystemCommandDef systemCommandDef = taskRunMonitorDao.getSystemCommandDef(qc.getTopic(), qc.getProvCode());
		String command = systemCommandDef.getHaltCommand();
		logger.info("original command = " + command);

		// 替换command
		command = command.replaceAll("@JOBID@", batchId);
		command = command.replaceAll("@DATABASENAME@", databaseName);
		command = command.replaceAll("@DATABASEPASSWORD@", databasePassword);
		command = command.replaceAll("@INSTANCENAME@", instanceName);
		command = command.replaceAll("@COMPANY@", company);
		command = command.replaceAll("@TASKID@", taskId);
		logger.info("after changed, command = " + command);

		// 获取ip
		String ip = taskRunMonitorDao.getServerIpByQueryInfo(qc.getTopic(), qc.getProvCode());
		logger.info("ip = " + ip);

		// 根据IP取出对应的username
		String userName = taskRunMonitorDao.getUserNameByServerInfo(qc.getTopic(), qc.getProvCode(), ip);
		logger.info("userName = " + userName);

		// 发送命令
		JsonSecondLayer jsonSecondLayer = this.sendToPlatform(ip, userName, command);

		// 总结结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", jsonSecondLayer.getMessage());
		result.put("returnCode", jsonSecondLayer.getReturncode());

		// 把批作业信息附加在result上
		result.put("batchId", qc.getBatchId());
		result.put("provCode", qc.getProvCode());
		result.put("topic", qc.getTopic());

		return result;

	}

	// 启动批作业
	public Map<String, Object> startBatch(QueryBatch qc) throws Exception {

		// 得到plcno(有区别)
//		String plcno = qc.getPlcno();
//		logger.info("plcno="+plcno);

		// 获取数据库名字，密码，实例名
		DatabaseConnection databaseConnection = taskRunMonitorDao.getDatabaseInfo(qc.getTopic(), qc.getProvCode());
		logger.info("databaseConnection = " + databaseConnection);
		String databaseName = databaseConnection.getDatabaseName();
		String databasePassword = databaseConnection.getDatabasePassword();
		String instanceName = databaseConnection.getInstanceName();

		// 获取batchId,company,
		String batchId = qc.getBatchId();
//		String company = qc.getCompany();
		logger.info("batchId" + batchId);
//		logger.info("company"+company);

		// 得到命令那一行
		String commandLine = taskRunMonitorDao.getPerticularCommandLine(batchId);
		logger.info("commandLine1 = " + commandLine);
		// 特殊字符转义
		commandLine = java.util.regex.Matcher.quoteReplacement(commandLine);
		logger.info("commandLine2 = " + commandLine);

		// 读取command(有区别)
		SystemCommandDef systemCommandDef = taskRunMonitorDao.getSystemCommandDef(qc.getTopic(), qc.getProvCode());
		String command = systemCommandDef.getStartCommand();
		logger.info("original command = " + command);

		// 替换命令那一行
		command = command.replaceAll("@COMMANDLINE@", commandLine);

		// 替换command
		command = command.replaceAll("@JOBID@", batchId);
		command = command.replaceAll("@DATABASENAME@", databaseName);
		command = command.replaceAll("@DATABASEPASSWORD@", databasePassword);
		command = command.replaceAll("@INSTANCENAME@", instanceName);
//		command = command.replaceAll("@COMPANY@", company);		
		// (有区别)
//		command = command.replaceAll("@PLCNO@", plcno);

		logger.info("after changed, command = " + command);

		// 获取ip
		String ip = taskRunMonitorDao.getServerIpByQueryInfo(qc.getTopic(), qc.getProvCode());
		logger.info("ip = " + ip);

		// 根据IP取出对应的username
		String userName = taskRunMonitorDao.getUserNameByServerInfo(qc.getTopic(), qc.getProvCode(), ip);
		logger.info("userName = " + userName);

		// 发送命令
		JsonSecondLayer jsonSecondLayer = this.sendToPlatform(ip, userName, command);

		// 总结结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", jsonSecondLayer.getMessage());
		result.put("returnCode", jsonSecondLayer.getReturncode());

		// 把批作业信息附加在result上
		result.put("batchId", qc.getBatchId());
		result.put("provCode", qc.getProvCode());
		result.put("topic", qc.getTopic());

		return result;

	}

	// 向云运维平台发送命令
	JsonSecondLayer sendToPlatform(String ip, String userName, String command) {
		// url
		final String url = "http://10.21.39.61:8080/api/v1/runCmdByIps"; // 云运维平台的api

		// 拼请求的报文内容
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("runAs", userName);
		jsonObject.addProperty("command", command);

		// 拼ip jsonArray
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(ip);
		jsonObject.add("ips", jsonArray);

		logger.info("jsonObject.toString() = " + jsonObject.toString());

		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			// 向云运维平台发送http post请求
			String str_jsonResponse = HttpUtil.doPost(url, jsonObject.toString());
//			System.out.println("str_jsonResponse = "+str_jsonResponse);

			JsonResponce jsonResponce = new Gson().fromJson(str_jsonResponse, JsonResponce.class); // Json字符串转Java对象
//			System.out.println("jsonResponce.success = " + jsonResponce.getSuccess());

			// 发送http请求 获取日志
			final String obj_url = "http://10.21.39.61:8080/api/v1/viewBatchCmd"; // 云运维平台获取日志的api接口

			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("batchId", jsonResponce.getObj());
			logger.info("obj=" + jsonResponce.getObj());
			logger.info("jsonObject2.toString()=" + jsonObject2.toString());

			String endtime = null;
			JsonFirstLayer jsonFirstLayer = null;
			JsonSecondLayer jsonSecondLayer = null;
			while (endtime == null) {
//				Thread.sleep(1000);
				String str_obj_jsonResponse = HttpUtil.doPost(obj_url, jsonObject2.toString());

//	        	String str_obj_jsonResponse = HttpUtil.doPost(obj_url, obj_json_params);
				System.out.println(str_obj_jsonResponse);

				// json字符串转java对象
				jsonFirstLayer = new Gson().fromJson(str_obj_jsonResponse, JsonFirstLayer.class);
				// logger.info("jsonFirstLayer"+jsonFirstLayer);

				jsonSecondLayer = jsonFirstLayer.getObj().get(0);
				// logger.info("jsonSecondLayer"+jsonSecondLayer);

				endtime = jsonSecondLayer.getEndtime();
			}

			return jsonSecondLayer;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public Map<String, Object> selectServer(QueryServer qc) {
		return taskRunMonitorDao.selectServer(qc.getSystem(), qc.getProvCode(), qc.getOrder(), qc.getLength(),
				qc.getStart());
	}

	public boolean updateServer(QueryServer qc) {
		return taskRunMonitorDao.updateServer(qc);
	}

	public boolean insertServer(QueryServer qc) {
		return taskRunMonitorDao.insertServer(qc);
	}

	public boolean deleteServer(QueryServer qc) {
		return taskRunMonitorDao.deleteServer(qc.getId());
	}

	public QueryBatch getBatchServerInfo(QueryBatch qc) {
		return taskRunMonitorDao.getBatchServerInfo(qc.getTopic(), qc.getProvCode(), qc.getBatchId());
	}

	public QueryBatch getBatchTaskIds(QueryBatch qc) {
		return taskRunMonitorDao.getBatchTaskIds(qc.getTopic(), qc.getProvCode(), qc.getBatchId());
	}

	// 得到地图数据
	public Map<String, Object> getMapBatches(QueryCond qc) {

		String topic = qc.getTopic();

		Map<String, Object> map = new HashMap<String, Object>();

		// 得到所有省份
		List<MapBatch> results = taskRunMonitorDao.getProvinces();

		// “异常规则”记录遍历
		int size = results.size();
		int maxErrorCount = 0;
		for (int i = 0; i < size; i++) {
			MapBatch mapBatch = results.get(i);
			String provCode = mapBatch.getProvCode();

			// 得到该省的异常批作业数量
			int errorCount = taskRunMonitorDao.getErrorCountOfOneProvince(topic, provCode);
			mapBatch.setErrorCount(String.valueOf(errorCount));
				
			//给系统赋值
			mapBatch.setTopic(topic);
			
			results.set(i, mapBatch);

			maxErrorCount = ((maxErrorCount >= errorCount) ? maxErrorCount : errorCount);
			logger.info("errorCount = " + errorCount);
			logger.info("maxErrorCount = " + maxErrorCount);

		}
		map.put("data", results);
		map.put("maxErrorCount", maxErrorCount);

		return map;
	}

	// 得到36个省的正运行的数据
	public Map<String, Object> getProvinceDimensionInfo(QueryCond qc) {

		// 接收传参
		String topic = qc.getTopic();

		// 储存结果
		Map<String, Object> map = new HashMap<String, Object>();

		// 得到日间批作业数量
		int allRiJianCount = taskRunMonitorDao.getBatchCountBaseOnSystemAndType(topic, "日间作业");

		// 得到日终批作业数量
		int allRiZhongCount = taskRunMonitorDao.getBatchCountBaseOnSystemAndType(topic, "日终作业");

		// 得到接口类作业数量
		int allInterfaceBatchCount = taskRunMonitorDao.getBatchCountBaseOnSystemAndType(topic, "接口类作业");

		// 得到功能批作业数量
		int allFunctionalBatchCount = taskRunMonitorDao.getBatchCountBaseOnSystemAndType(topic, "功能性作业");

		// 得到个性化批作业数量
		int allPersonalBatchCount = taskRunMonitorDao.getBatchCountBaseOnSystemAndType(topic, "个性化作业");

		// 得到所有省份
		List<MapBatch> results = taskRunMonitorDao.getProvinces();

		int size = results.size();
		for (int i = 0; i < size; i++) {
			MapBatch mapBatch = results.get(i);
			String provCode = mapBatch.getProvCode();

			// 得到正运行的的各种批作业数量（日终是运行完毕的）
			int runningRiJianCount = taskRunMonitorDao.getRunningCountBaseOnSystemAndType(topic, provCode, "日间作业");
			int runedRiZhongCount = taskRunMonitorDao.getRunnedCountBaseOnSystemAndType(topic, provCode, "日终作业");
			int runningInterfaceBatchCount = taskRunMonitorDao.getRunningCountBaseOnSystemAndType(topic, provCode,
					"接口类作业");
			int runningFunctionalBatchCount = taskRunMonitorDao.getRunningCountBaseOnSystemAndType(topic, provCode,
					"功能性作业");
			int runningPersonalBatchCount = taskRunMonitorDao.getRunningCountBaseOnSystemAndType(topic, provCode,
					"个性化作业");

			// 赋值
			mapBatch.setAllRiJianCount(String.valueOf(allRiJianCount));
			mapBatch.setAllRiZhongCount(String.valueOf(allRiZhongCount));
			mapBatch.setAllInterfaceBatchCount(String.valueOf(allInterfaceBatchCount));
			mapBatch.setAllFunctionalBatchCount(String.valueOf(allFunctionalBatchCount));
			mapBatch.setAllPersonalBatchCount(String.valueOf(allPersonalBatchCount));

			mapBatch.setRunningRiJianCount(String.valueOf(runningRiJianCount));
			mapBatch.setRunedRiZhongCount(String.valueOf(runedRiZhongCount));
			mapBatch.setRunningInterfaceBatchCount(String.valueOf(runningInterfaceBatchCount));
			mapBatch.setRunningFunctionalBatchCount(String.valueOf(runningFunctionalBatchCount));
			mapBatch.setRunningPersonalBatchCount(String.valueOf(runningPersonalBatchCount));

			mapBatch.setTopic(topic);
			
			results.set(i, mapBatch);
		}

		map.put("data", results);

		return map;
	}

	// 得到出单批作业状态
	public Map<String, Object> getChuDanDimensionInfo(QueryCond qc) {

		// 接收传参

		// 储存结果
		List<MapBatch> results = new ArrayList<MapBatch>();
		Map<String, Object> map = new HashMap<String, Object>();

		// 涉及出单的批作业
		String arr[][] = { { "V8", "41021" }, { "SL", "41021" }, { "V8", "70205" }, { "SL", "70201" },
				{ "V8", "41101" }, { "V8", "301810" }, { "SL", "41101" }, { "V8", "41105" }, { "SL", "41105" },
				{ "V8", "41103" }, { "SL", "41103" } };

		for (int i = 0; i < arr.length; i++) {
			MapBatch mapBatch = new MapBatch();

			String topic = arr[i][0];
			String batchId = arr[i][1];

			int warningCount = taskRunMonitorDao.getChuDanWarningCount(topic, batchId);
			logger.info("warningCount = " + warningCount);

			int warningFlag = warningCount > 0 ? 1 : 0;

			String warningInfo = "";
			if (warningCount > 0) {
				warningInfo = taskRunMonitorDao.getChuDanWarningInfo(topic, batchId);
				logger.info("warningInfo = " + warningInfo);
				warningInfo = warningInfo.replace(",", "<br>");
				logger.info("warningInfo = " + warningInfo);
			}

			int errorCount = taskRunMonitorDao.getChuDanErrorCount(topic, batchId);
			logger.info("errorCount = " + errorCount);

			int errorFlag = errorCount > 0 ? 1 : 0;

			String errorInfo = "";
			if (errorCount > 0) {
				errorInfo = taskRunMonitorDao.getChuDanErrorInfo(topic, batchId);
				logger.info("errorInfo = " + errorInfo);
				errorInfo = errorInfo.replace(",", "<br>");
				logger.info("errorInfo = " + errorInfo);
			}

			mapBatch.setTopic(topic);
			mapBatch.setBatchId(batchId);
			mapBatch.setWarningFlag(warningFlag);
			mapBatch.setWarningInfo(warningInfo);
			mapBatch.setErrorFlag(errorFlag);
			mapBatch.setErrorInfo(errorInfo);

			results.add(mapBatch);

		}

		// 得到所有省份

		map.put("data", results);

		return map;
	}

	// 得到批作业状态
		public Map<String, Object> getBatchDimensionInfo(QueryCond qc) {

			// 接收传参
			String topic = qc.getTopic();
			String batchTypeDetail = qc.getBatchTypeDetail();
			
			
			// 储存结果
			Map<String, Object> map = new HashMap<String, Object>();				
			
			List<MapBatch> result = taskRunMonitorDao.getBatchesBaseOnTopicAndBatchTypeDetail(topic, batchTypeDetail);
			for (int i = 0;  i < result.size(); i++) {
				MapBatch mapBatch = result.get(i);
				if (batchTypeDetail.equals("日间作业")) {
					int runningProvinceCount = taskRunMonitorDao.getRunningProvinceCount(topic,mapBatch.getBatchId());
					mapBatch.setRunningProvinceCount(runningProvinceCount);
				}
				else if (batchTypeDetail.equals("日终作业")) {
					int runnedProvinceCount = taskRunMonitorDao.getRunnedProvinceCount(topic,mapBatch.getBatchId());
					mapBatch.setRunnedProvinceCount(runnedProvinceCount);

				}
				else {
					
				}
				result.set(i, mapBatch);
			}

			map.put("data", result);

			return map;
		}
	
		// 得到批作业状态
				public Map<String, Object> getOneBatchStatusOfOneProvince(QueryCond qc) {
					
					// 接收传参
					String topic = qc.getTopic();
					String batchId = qc.getBatchId();
					String batchTypeDetail = taskRunMonitorDao.getBatchTypeDetailOfBatchId(topic, batchId);
					
					// 储存结果
					Map<String, Object> map = new HashMap<String, Object>();				
					
					// 得到所有省份
					List<MapBatch> results = taskRunMonitorDao.getProvinces();

					int size = results.size();
					for (int i = 0; i < size; i++) {
						MapBatch mapBatch = results.get(i);
						String provCode = mapBatch.getProvCode();
						
						// 得到正运行的的各种批作业数量（日终是运行完毕的）
						if (batchTypeDetail.equals("日间作业")) {
							int runningCount = taskRunMonitorDao.getRunningCountOfOneRiJianBatch(topic,provCode,batchId);
							String runningFlag = runningCount > 0 ? "是" : "否";
							mapBatch.setRunningFlag(runningFlag);
							
						}
						else if (batchTypeDetail.equals("日终作业")) {
							int runnedCount = taskRunMonitorDao.getRunnedCountOfOneRiZhongBatch(topic,provCode,batchId);
							String runnedFlag = runnedCount > 0 ? "是" : "否";
							mapBatch.setRunnedFlag(runnedFlag);

						}
						else {
							
						}

						// 赋值
						mapBatch.setTopic(topic);
						mapBatch.setBatchId(batchId);
						mapBatch.setBatchTypeDetail(batchTypeDetail);
						
						results.set(i, mapBatch);
					}

					map.put("data", results);

					return map;
				}
		
}
