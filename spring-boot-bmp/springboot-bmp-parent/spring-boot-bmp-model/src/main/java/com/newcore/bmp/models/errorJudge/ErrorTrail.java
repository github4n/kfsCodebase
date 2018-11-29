package com.newcore.bmp.models.errorJudge;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ErrorTrail {
	String id; // 序列（主键）
	String taskId; // 运行任务标识
	String batchTxNo; // 批作业标识
	String provBranchCode; // 所在省份
	String whereClause; // 执行条件
	String errReasonId; // 异常原因ID
	String errJudgeCount; // 异常累计发现次数
	String errElimitateFlag; // 异常消除标志
	String errFirstJudgeTime; // 异常首次判定时间
	String errRecentJudgeTime; // 异常最新判定时间
	String errElimitateTime; // 异常消除时间

	String errEliminateCondition; // 异常消除条件

	String startExecTime; // 批作业开始时间

	String system; // 所属系统

	String provBranchName; // 所在省份名称
	String batchName; // 批作业名称
	String errReasonDetail; // 异常原因名称

	String batchTypeDetail; // 批作业类型

	public String getBatchTypeDetail() {
		return batchTypeDetail;
	}

	public void setBatchTypeDetail(String batchTypeDetail) {
		this.batchTypeDetail = batchTypeDetail;
	}

	public String getProvBranchName() {
		return provBranchName;
	}

	public void setProvBranchName(String provBranchName) {
		this.provBranchName = provBranchName;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getErrReasonDetail() {
		return errReasonDetail;
	}

	public void setErrReasonDetail(String errReasonDetail) {
		this.errReasonDetail = errReasonDetail;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getStartExecTime() {
		return startExecTime;
	}

	public void setStartExecTime(String startExecTime) {
		this.startExecTime = startExecTime;
	}

	public String getErrEliminateCondition() {
		return errEliminateCondition;
	}

	public void setErrEliminateCondition(String errEliminateCondition) {
		this.errEliminateCondition = errEliminateCondition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBatchTxNo() {
		return batchTxNo;
	}

	public void setBatchTxNo(String batchTxNo) {
		this.batchTxNo = batchTxNo;
	}

	public String getProvBranchCode() {
		return provBranchCode;
	}

	public void setProvBranchCode(String provBranchCode) {
		this.provBranchCode = provBranchCode;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

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

	public String getErrElimitateTime() {
		return errElimitateTime;
	}

	public void setErrElimitateTime(String errElimitateTime) {
		this.errElimitateTime = errElimitateTime;
	}

	public String getSelfDescription() {
		return " id:" + this.getId() + " taskId:" + this.getTaskId() + " batchTxNo:" + this.getBatchTxNo()
				+ " provBranchCode:" + this.getProvBranchCode() + " whereClause:" + this.getWhereClause()
				+ " errReasonId:" + this.getErrReasonId() + " errJudgeCount:" + this.getErrJudgeCount()
				+ " errElimitateFlag:" + this.getErrRecentJudgeTime() + " errFirstJudgeTime:"
				+ this.getErrFirstJudgeTime() + " errRecentJudgeTime:" + this.getErrRecentJudgeTime()
				+ " errElimitateTime:" + this.getErrElimitateTime() + "errEliminateCondition:"
				+ this.getErrEliminateCondition() + "startExecTime:" + this.getStartExecTime();

	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
