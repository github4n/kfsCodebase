package com.newcore.bmp.service.api.syncConditionMonitor;



public interface SyncConditionMonitorService {
	
	//kafka与实际系统实时同步的相差时间的监控
	public void syncConditionMonitor(String system) throws Exception;
	
}
