package com.newcore.bmp.service.api.errorMonitor;

public interface ErrorMonitorService {

	
	//异常判断
	public void errorJudge(String system);
	
	//异常消除逻辑
	public void errorEliminate(String system);
	
}
