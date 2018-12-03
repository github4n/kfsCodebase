package top.ywwxhz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.newcore.bmp.utils.errorMonitor.ErrorMonitor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorEliminateMonitorJob implements Job {

	@Autowired
	ErrorMonitor errorMonitor;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取参数
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String system = (String) dataMap.get("system");

		try {
			errorMonitor.errorEliminate(system);		
		} catch (Exception e) {
			throw new JobExecutionException("ErrorEliminateMonitorJob 执行失败", e);
		}
	}
}