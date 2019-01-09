package top.ywwxhz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StringUtils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String timeDiff = (String) dataMap.get("timeDiff");
		if (StringUtils.isEmpty(timeDiff)) {
			timeDiff = "5";
		}
		DateTime now = DateUtil.date();
		DateTime dateTime = DateUtil.offset(now, DateField.MINUTE, Integer.parseInt(timeDiff));
		try {
			log.info("from {} ~ {}", dateTime, now);
		} catch (Exception e) {
			throw new JobExecutionException("job 执行失败", e);
		}
	}
}