package com.newcore.bmp.utils.errorMonitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.curator.retry.RetryUntilElapsed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.newcore.bmp.service.api.errorMonitor.ErrorMonitorService;

import lombok.extern.slf4j.Slf4j;

@Component("errorMonitor")
@Slf4j
public class ErrorMonitor {

	@Autowired
	ErrorMonitorService errorMonitorService;

	//邮件告警的时间格式
	String format = "HH:mm:ss";

	@Value("${chudanBatchWarningStartTime:00:00:00}")
	String start; //邮件告警的开始时间
	
	@Value("${chudanBatchWarningEndTime:23:59:59}")
	String end;   //邮件告警的结束时间
	

	
	// 异常判断
	public void errorJudge(String system) {

		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_start_" + system);

		// 异常判断
		errorMonitorService.errorJudge(system);

		// 在每天特定时间内，针对异常的出单批作业，发送邮件，短信告警
		if (isInPeriod()) {
			errorMonitorService.chudanBatchWarning(system);
		}

		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorJudge_end_" + system);
	}

	

	// 异常消除
	public void errorEliminate(String system) {

		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_start_" + system);

		errorMonitorService.errorEliminate(system);

		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " errorEliminate_end_" + system);

	}

	
	private boolean isInPeriod() {
		try {
			String now = new SimpleDateFormat(format).format(new Date());
			Date nowTime = new SimpleDateFormat(format).parse(now);
			Date startTime = new SimpleDateFormat(format).parse(start);
			Date endTime = new SimpleDateFormat(format).parse(end);


			log.info("nowTime" + nowTime.toString());
			log.info("startTime" + startTime.toString());
			log.info("endTime" + endTime.toString());

			if (this.isEffectiveDate(nowTime, startTime, endTime)) {

				return true;
			}


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	private static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

}
