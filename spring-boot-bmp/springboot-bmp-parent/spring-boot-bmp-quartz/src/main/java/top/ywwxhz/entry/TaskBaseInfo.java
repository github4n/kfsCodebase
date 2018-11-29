package top.ywwxhz.entry;

import java.util.Map;

import lombok.Data;

@Data
public class TaskBaseInfo {
	private int id = 0;

	/** 任务名称 */
	private String jobName;

	/** 任务名称 */
	private String jobClass;

	/** 任务分组 */
	private String jobGroup;

	/** 任务描述 */
	private String jobDescription;

	/** 任务表达式 */
	private String cronExpression;
	
	private Map<String, Object> jobDataMap;
}
