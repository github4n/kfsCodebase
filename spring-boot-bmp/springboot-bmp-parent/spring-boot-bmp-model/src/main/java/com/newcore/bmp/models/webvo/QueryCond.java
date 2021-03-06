package com.newcore.bmp.models.webvo;

import java.sql.Date;
import java.sql.Timestamp;

public class QueryCond {
	
	Timestamp currTime;	//当前时间
	int execStat;		//运行状态
	int summaryFlag;	//汇总标志
	
	String topic;		//表格来源	
	String provCode;		//省级机构	
	String batchId;		//作业编号
	int batchType;		//批作业类型（全部/日终/日间）
	String order;		//排序
	String proName;		//省级机构名称
	String batchName ;		//作业名称
	String errScale;	//异常级别
	String errReasonId;	//异常原因代码
	String length;		//一次查询最多条数
	String start;		//查询起点
	
	String clerkNo; //工号

	String startExecTime ; //工号

	String endExecTime ; //工号
	
	String ip;
	String userName;
	String command;
	
	String batchTypeDetail;
	
	
	
	
	
	
	

	
	public String getBatchTypeDetail() {
		return batchTypeDetail;
	}
	public void setBatchTypeDetail(String batchTypeDetail) {
		this.batchTypeDetail = batchTypeDetail;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getClerkNo() {
		return clerkNo;
	}
	public void setClerkNo(String clerkNo) {
		this.clerkNo = clerkNo;
	}
	public String getErrReasonId() {
		return errReasonId;
	}
	public void setErrReasonId(String errReasonId) {
		this.errReasonId = errReasonId;
	}
	public String getErrScale() {
		return errScale;
	}
	public void setErrScale(String errScale) {
		this.errScale = errScale;
	}
	public Timestamp getCurrTime() {
		return currTime;
	}
	public void setCurrTime(Timestamp currTime) {
		this.currTime = currTime;
	}
	public int getExecStat() {
		return execStat;
	}
	public void setExecStat(int execStat) {
		this.execStat = execStat;
	}
	public int getSummaryFlag() {
		return summaryFlag;
	}
	public void setSummaryFlag(int summaryFlag) {
		this.summaryFlag = summaryFlag;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public int getBatchType() {
		return batchType;
	}
	public void setBatchType(int batchType) {
		this.batchType = batchType;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	
	public void setLength(String length) {
		this.length = length;
	}

	public String getLength() {
		return length;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStart() {
		return start;
	}
}


