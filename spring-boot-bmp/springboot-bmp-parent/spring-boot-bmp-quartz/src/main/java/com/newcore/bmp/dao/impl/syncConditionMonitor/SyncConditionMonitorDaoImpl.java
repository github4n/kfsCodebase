package com.newcore.bmp.dao.impl.syncConditionMonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import org.springframework.stereotype.Repository;
import com.newcore.bmp.dao.api.syncConditionMonitor.SyncConditionMonitorDao;

@Repository("syncConditionMonitorDao")
public class SyncConditionMonitorDaoImpl implements SyncConditionMonitorDao {

	@Autowired
	JdbcOperations jdbcTemplate;
		
	// 获得同步时间与当前时间的差值，大于阈值，则发送邮件进行告警
	public int syncConditionMonitor(String system) {
		String sql = "select ceil((SYSDATE - max(LAST_UPDTIME)) * 24 * 60 * 60)  from monitor_queue_" + system;
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
