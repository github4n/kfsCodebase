package com.newcore.bmp.utils.syncConditionMonitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newcore.bmp.service.api.syncConditionMonitor.SyncConditionMonitorService;

import lombok.extern.slf4j.Slf4j;


@Component("syncConditionMonitor")
@Slf4j
public class SyncConditionMonitor {

	@Autowired
	SyncConditionMonitorService syncConditionMonitorService;
	
	// 消费程序实时同步情况监控
	public void syncConditionMonitor(String system) throws Exception {
		
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " syncConditionMonitor_start_"+system);
		 
		syncConditionMonitorService.syncConditionMonitor(system);
		
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " syncConditionMonitor_end_"+system);
	}
}
