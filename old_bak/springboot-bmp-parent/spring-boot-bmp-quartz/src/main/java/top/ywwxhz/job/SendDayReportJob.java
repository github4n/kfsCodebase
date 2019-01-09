package top.ywwxhz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import com.newcore.bmp.utils.email.SendDayReport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendDayReportJob implements Job {

	@Autowired
	SendDayReport sendDayReport;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取参数
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String system = (String) dataMap.get("system");

		try {
			sendDayReport.sendDayReportEmail(system);			
		} catch (Exception e) {
			throw new JobExecutionException("SendDayReportJob 执行失败", e);
		}
	}
}