package com.newcore.bmp.dao.api.errorMonitor;



import java.util.List;

import com.newcore.bmp.models.errorJudge.ErrorDefine;
import com.newcore.bmp.models.errorJudge.ErrorTrail;
import com.newcore.bmp.models.errorJudge.SelectField;
import com.newcore.bmp.models.webvo.RunningBatchVo;

public interface ErrorMonitorDao {
	
	//Select “异常规则定义表”的所有记录到内存中
	public List<ErrorDefine> GetErrorDefine(String system);
	
	//Select MONITOR_QUEUE_V8左连接BATCH_DEF，where条件=“异常规则”的“异常判定条件”，到内存中(即该异常规则对应的异常记录的集合)
	public List<SelectField> SelectField(String system, String errorReasonDetail, String whereCondition);
	
	//根据(机构号，批作业ID，执行条件，异常原因ID）到异常轨迹表中查找对应记录
	public List<ErrorTrail> FindInErrorTrail(String system, String provinceCode,String batchTxNo, String whereClause, String errReasonId, String startExecTime);
	
	//insert一条“异常记录”到异常轨迹表,记录“机构号”，“批作业ID”，“执行条件”，“异常原因ID”；设置”异常累计判定次数”,“异常首次判定时间”，“异常最新判定时间”
	public void InsertErrorTrail( String system, SelectField sf,ErrorDefine ed);
	
	// 根据（机构号，批作业ID，执行条件，异常原因ID），update异常轨迹表，更新“异常最新判定时间”,”异常累计判定次数”
	public void updateErrorTrail(final List<ErrorTrail> resET_has_where_clause,
			final List<ErrorTrail> resET_has_not_where_clause);
	//-----------------------------------------
	
	//异常消除----------------------------------------------------------------------
	
	public List<ErrorTrail> GetErrorRecord(String system);
	

	public void eliminateErrorTrail(final List<ErrorTrail> resET_has_where_clause, final List<ErrorTrail> resET_has_not_where_clause);
	
//	public int FindInMonitorQueue(String system, String provBranchCode, String batchTxNo, String whereClause,
//			String errEliminateCondition, String errReasonId, String startExecTime);


	//  异常消除----------------------------------------------------------------------

	public int FindInMonitorQueue(String system, String provBranchCode, String batchTxNo, String whereClause,
			String errEliminateCondition, String errReasonId, String startExecTime, String errReasonDetail);
}
