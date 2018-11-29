package com.newcore.bmp.models.webvo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QueryBatch {
	String company;
	String taskId;
	
	String plcno;
	
	String[] taskIdList;
	int allStopFlag;
	
	String taskIds;

	
	
	
	public String getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}
	public int getAllStopFlag() {
		return allStopFlag;
	}
	public void setAllStopFlag(int allStopFlag) {
		this.allStopFlag = allStopFlag;
	}
	public String[] getTaskIdList() {
		return taskIdList;
	}
	public void setTaskIdList(String[] taskIdList) {
		this.taskIdList = taskIdList;
	}
	public String getPlcno() {
		return plcno;
	}
	public void setPlcno(String plcno) {
		this.plcno = plcno;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}



	String id;
	String batchId; 
	String batchName; 
	String batchTypeDetail;
	String scheduleFrequency; 
	String itFramework;
	String LoopFlag;
	String operationStaff; 
	String operationMode; 
	String state;
	String ruleStartExecTime; 
	String ruleEndExecTime; 	
	String topic; 
	String startCommand;
	String startParameters;
	String haltCommand;
	String haltParameters;
	
	String order; 
	String length;
	String start;
	
	String command;
	String parameters;
	String description;
	
	
		
	String provCode;
	String ips;
	String usernames;
	
	String ip;
	String username;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getBatchTypeDetail() {
		return batchTypeDetail;
	}
	public void setBatchTypeDetail(String batchTypeDetail) {
		this.batchTypeDetail = batchTypeDetail;
	}
	public String getScheduleFrequency() {
		return scheduleFrequency;
	}
	public void setScheduleFrequency(String scheduleFrequency) {
		this.scheduleFrequency = scheduleFrequency;
	}
	public String getItFramework() {
		return itFramework;
	}
	public void setItFramework(String itFramework) {
		this.itFramework = itFramework;
	}
	public String getLoopFlag() {
		return LoopFlag;
	}
	public void setLoopFlag(String loopFlag) {
		LoopFlag = loopFlag;
	}
	public String getOperationStaff() {
		return operationStaff;
	}
	public void setOperationStaff(String operationStaff) {
		this.operationStaff = operationStaff;
	}
	public String getOperationMode() {
		return operationMode;
	}
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRuleStartExecTime() {
		return ruleStartExecTime;
	}
	public void setRuleStartExecTime(String ruleStartExecTime) {
		this.ruleStartExecTime = ruleStartExecTime;
	}
	public String getRuleEndExecTime() {
		return ruleEndExecTime;
	}
	public void setRuleEndExecTime(String ruleEndExecTime) {
		this.ruleEndExecTime = ruleEndExecTime;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getStartCommand() {
		return startCommand;
	}
	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}
	public String getStartParameters() {
		return startParameters;
	}
	public void setStartParameters(String startParameters) {
		this.startParameters = startParameters;
	}
	public String getHaltCommand() {
		return haltCommand;
	}
	public void setHaltCommand(String haltCommand) {
		this.haltCommand = haltCommand;
	}
	public String getHaltParameters() {
		return haltParameters;
	}
	public void setHaltParameters(String haltParameters) {
		this.haltParameters = haltParameters;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getIps() {
		return ips;
	}
	public void setIps(String ips) {
		this.ips = ips;
	}
	public String getUsernames() {
		return usernames;
	}
	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

	
}
