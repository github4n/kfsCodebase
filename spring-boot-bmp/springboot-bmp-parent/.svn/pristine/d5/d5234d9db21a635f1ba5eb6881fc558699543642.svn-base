package com.newcore.bmp.utils.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.newcore.bmp.service.api.email.EmailService;
import com.newcore.bmp.service.api.sendDayReport.SendDayReportService;

import lombok.extern.slf4j.Slf4j;

@Component("sendDayReport")
@Slf4j
public class SendDayReport {

	//@Autowired
//	EmailService emailService;

	@Autowired
	SendDayReportService sendDayReportService;
	
	
	// 发日报的email
	public void sendDayReportEmail(String system) {	

		
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " sendDayReport_start_" + system);
		
		 try {
			 sendDayReportService.sendDayReportEmail(system);
		} catch (Exception e) {
			log.error("Catch Exception in sendDayReport()");
			e.printStackTrace();
		}

		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " sendDayReport_end_" + system);
	}
	
}
