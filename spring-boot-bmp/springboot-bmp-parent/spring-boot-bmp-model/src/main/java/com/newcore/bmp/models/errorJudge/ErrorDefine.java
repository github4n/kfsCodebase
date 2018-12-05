package com.newcore.bmp.models.errorJudge;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ErrorDefine {
	String errReasonId; // 异常原因序列
	String errReasonDetail; // 异常原因
	String errScale; // 异常等级
	String errJudgeCondition; // 异常判定条件
	String errEliminateCondition; // 异常消除条件
	String system; // 所属系统
	String monitorIntervalCount;
	
	

	public String getMonitorIntervalCount() {
		return monitorIntervalCount;
	}

	public void setMonitorIntervalCount(String monitorIntervalCount) {
		this.monitorIntervalCount = monitorIntervalCount;
	}

	public String getErrReasonId() {
		return errReasonId;
	}

	public void setErrReasonId(String errReasonId) {
		this.errReasonId = errReasonId;
	}

	public String getErrReasonDetail() {
		return errReasonDetail;
	}

	public void setErrReasonDetail(String errReasonDetail) {
		this.errReasonDetail = errReasonDetail;
	}

	public String getErrScale() {
		return errScale;
	}

	public void setErrScale(String errScale) {
		this.errScale = errScale;
	}

	public String getErrJudgeCondition() {
		return errJudgeCondition;
	}

	public void setErrJudgeCondition(String errJudgeCondition) {
		this.errJudgeCondition = errJudgeCondition;
	}

	public String getErrEliminateCondition() {
		return errEliminateCondition;
	}

	public void setErrEliminateCondition(String errEliminateCondition) {
		this.errEliminateCondition = errEliminateCondition;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSelfDescription() {
		return " ERRREASONID:" + this.getErrReasonId() + " ERR_REASON_DETAIL:" + this.getErrReasonDetail()
				+ " ERR_SCALE:" + this.getErrScale() + " ERR_JUDGE_CONDITION:" + this.getErrJudgeCondition()
				+ " ERR_ELIMINATE_CONDITION:" + this.getErrEliminateCondition() + " SYSTEM:" + this.getSystem();
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
