package com.newcore.bmp.models.webvo;

public class FailBatchVo extends BatchVO{
	String topic; //业务系统
    String proCode;	  //机构号
    String proName;		//机构名称
    String batchId; //作业ID
    String batchName; //作业名称
    String whereClause; //运行条件
    String startExecTime; //开始运行时间
    String endExecTime; //结束运行时间

    String endProcRecord; //完成数量
    String errProcRecord; //错误数量
    String execProcRecord; //已处理数量

    
    String oclkBranchNo; //运行人员机构
    String oclkClerkNo; //运行人员工号
    String errReason;	//异常原因
    String errScale;	//异常级别
    
    
    String batchType; //批作业类型
    String errJudgeCount; 

    String errElimitateFlag; 

    String errFirstJudgeTime; 

    String errRecentJudgeTime; 

    String errReasonId;	//异常原因ID

    
    
    

	public String getErrReasonId() {
		return errReasonId;
	}
	public void setErrReasonId(String errReasonId) {
		this.errReasonId = errReasonId;
	}
	public String getErrJudgeCount() {
		return errJudgeCount;
	}
	public void setErrJudgeCount(String errJudgeCount) {
		this.errJudgeCount = errJudgeCount;
	}
	public String getErrElimitateFlag() {
		return errElimitateFlag;
	}
	public void setErrElimitateFlag(String errElimitateFlag) {
		this.errElimitateFlag = errElimitateFlag;
	}
	public String getErrFirstJudgeTime() {
		return errFirstJudgeTime;
	}
	public void setErrFirstJudgeTime(String errFirstJudgeTime) {
		this.errFirstJudgeTime = errFirstJudgeTime;
	}
	public String getErrRecentJudgeTime() {
		return errRecentJudgeTime;
	}
	public void setErrRecentJudgeTime(String errRecentJudgeTime) {
		this.errRecentJudgeTime = errRecentJudgeTime;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
	
	public String getExecProcRecord() {
		return execProcRecord;
	}
	public void setExecProcRecord(String execProcRecord) {
		this.execProcRecord = execProcRecord;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getWhereClause() {
		return whereClause;
	}
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	public String getStartExecTime() {
		return startExecTime;
	}
	public void setStartExecTime(String startExecTime) {
		this.startExecTime = startExecTime;
	}
	public String getEndExecTime() {
		return endExecTime;
	}
	public void setEndExecTime(String endExecTime) {
		this.endExecTime = endExecTime;
	}
	public String getEndProcRecord() {
		return endProcRecord;
	}
	public void setEndProcRecord(String endProcRecord) {
		this.endProcRecord = endProcRecord;
	}
	public String getErrProcRecord() {
		return errProcRecord;
	}
	public void setErrProcRecord(String errProcRecord) {
		this.errProcRecord = errProcRecord;
	}
	public String getOclkBranchNo() {
		return oclkBranchNo;
	}
	public void setOclkBranchNo(String oclkBranchNo) {
		this.oclkBranchNo = oclkBranchNo;
	}
	public String getOclkClerkNo() {
		return oclkClerkNo;
	}
	public void setOclkClerkNo(String oclkClerkNo) {
		this.oclkClerkNo = oclkClerkNo;
	}
	public String getErrReason() {
		return errReason;
	}
	public void setErrReason(String errReason) {
		this.errReason = errReason;
	}
	public String getErrScale() {
		return errScale;
	}
	public void setErrScale(String errScale) {
		this.errScale = errScale;
	}

}
