package com.newcore.bmp.utils.errorMonitor;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.newcore.bmp.service.api.errorMonitor.ErrorMonitorService;

public class ErrorMonitor {

	private static Logger LOGGER = LoggerFactory.getLogger(ErrorMonitor.class);

	@Autowired
	ErrorMonitorService errorMonitorService;

	// 八版异常判断
	public void errorJudgeOnV8() {
		//定义要扫描的系统
		String system = "V8";
		
		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_start_"+system);
		
		 //"运行中的批作业"的异常扫描
		 errorMonitorService.errorJudging(system);
		
		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_end_"+system);
	}
	
	// 八版异常消除
	public void errorEliminateOnV8() {
		//定义要扫描的系统
		String system = "V8";
		
		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_start_"+system);

		 //"运行中的批作业"的异常扫描
		 errorMonitorService.errorEliminate("V8");

		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_end_"+system);

	}
	
//------------------------------------------------------------------------------------------------------------------------
	// 短险异常判断
	public void errorJudgeOnSL() {
		//定义要扫描的系统
		String system = "SL";
		
		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_start_"+system);
		
		 //"运行中的批作业"的异常扫描
		 errorMonitorService.errorJudging(system);
		
		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_end_"+system);
	}
	
	// 短险异常消除
	public void errorEliminateOnSL() {
		//定义要扫描的系统
		String system = "SL";
		
		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_start_"+system);

		 //"运行中的批作业"的异常扫描
		 errorMonitorService.errorEliminate("SL");

		LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_end_"+system);

	}
}
