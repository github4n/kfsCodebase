package com.newcore.bmp.utils.errorMonitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newcore.bmp.service.api.errorMonitor.ErrorMonitorService;

import lombok.extern.slf4j.Slf4j;

@Component("errorMonitor")
@Slf4j
public class ErrorMonitor {

	@Autowired
	ErrorMonitorService errorMonitorService;
	
	// 异常判断
	public void errorJudge(String system) {
		
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_start_"+system);
		
		 errorMonitorService.errorJudge(system);
		
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_end_"+system);
	}
	
	// 异常消除
	public void errorEliminate(String system) {
		
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_start_"+system);

		 errorMonitorService.errorEliminate(system);

		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_end_"+system);

	}
	
}
