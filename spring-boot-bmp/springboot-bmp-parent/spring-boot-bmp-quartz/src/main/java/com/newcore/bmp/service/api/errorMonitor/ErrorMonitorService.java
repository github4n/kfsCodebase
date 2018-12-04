package com.newcore.bmp.service.api.errorMonitor;

public interface ErrorMonitorService {

	
	//异常判断
	public void errorJudge(String system);
	
	//针对异常的出单批作业，发送邮件，短信告警
	public void chudanBatchWarning(String system);
	
	//异常消除逻辑
	public void errorEliminate(String system);
	
}
