package top.ywwxhz.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理定时任务
 * 
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskInfo extends TaskBaseInfo {

	/** 任务状态 */
	private String jobStatus;

	private String createTime;
	
	private String nextTime;
}