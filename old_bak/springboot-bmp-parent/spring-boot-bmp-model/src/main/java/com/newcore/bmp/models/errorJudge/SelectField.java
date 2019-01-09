package com.newcore.bmp.models.errorJudge;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SelectField {
	String taskId;
	String startExecTime; // 开始运行时间
	String batchTxNo; // 作业ID
	String whereClause; // 运行条件
	String endExecTime; // 运行结束时间
	String execStat; // 运行状态
	String endProcRec; // 已处理数量
	String errProcRec; // 错误数量
	String execProcRec; // 错误数量
	String priClass;// 优先级
	String bizParamDesc;
	String oclkBranchNo;
	String oclkClerkNo;
	String provBranchCode;
	String lastUpdTime;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStartExecTime() {
		return startExecTime;
	}

	public void setStartExecTime(String startExecTime) {
		this.startExecTime = startExecTime;
	}

	public String getBatchTxNo() {
		return batchTxNo;
	}

	public void setBatchTxNo(String batchTxNo) {
		this.batchTxNo = batchTxNo;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getEndExecTime() {
		return endExecTime;
	}

	public void setEndExecTime(String endExecTime) {
		this.endExecTime = endExecTime;
	}

	public String getExecStat() {
		return execStat;
	}

	public void setExecStat(String execStat) {
		this.execStat = execStat;
	}

	public String getEndProcRec() {
		return endProcRec;
	}

	public void setEndProcRec(String endProcRec) {
		this.endProcRec = endProcRec;
	}

	public String getErrProcRec() {
		return errProcRec;
	}

	public void setErrProcRec(String errProcRec) {
		this.errProcRec = errProcRec;
	}

	public String getExecProcRec() {
		return execProcRec;
	}

	public void setExecProcRec(String execProcRec) {
		this.execProcRec = execProcRec;
	}

	public String getPriClass() {
		return priClass;
	}

	public void setPriClass(String priClass) {
		this.priClass = priClass;
	}

	public String getBizParamDesc() {
		return bizParamDesc;
	}

	public void setBizParamDesc(String bizParamDesc) {
		this.bizParamDesc = bizParamDesc;
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

	public String getProvBranchCode() {
		return provBranchCode;
	}

	public void setProvBranchCode(String provBranchCode) {
		this.provBranchCode = provBranchCode;
	}

	public String getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(String lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public String getSelfDescription() {
		return " TASK_ID:" + this.getTaskId() + " startExecTime:" + this.getStartExecTime() + " batchTxNo:"
				+ this.getBatchTxNo() + " whereClause:" + this.getWhereClause() + " endExecTime:"
				+ this.getEndExecTime() + " execStat:" + this.getExecStat() + " endProcRec:" + this.getEndProcRec()
				+ " errProcRec:" + this.getErrProcRec() + " execProcRec:" + this.getExecProcRec() + " priClass:"
				+ this.getPriClass() + " bizParamDesc:" + this.getBizParamDesc() + " oclkBranchNo:"
				+ this.getOclkBranchNo() + " oclkClerkNo:" + this.getOclkClerkNo() + " provBranchCode:"
				+ this.getProvBranchCode() + " lastUpdTime:" + this.getLastUpdTime();
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
