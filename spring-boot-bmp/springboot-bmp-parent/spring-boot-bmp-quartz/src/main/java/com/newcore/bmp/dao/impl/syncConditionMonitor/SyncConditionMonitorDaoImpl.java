package com.newcore.bmp.dao.impl.syncConditionMonitor;

import java.util.ArrayList;
import java.util.List;

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

	// 查出对应的SelectSystemEnglishName
	public String SelectSystemEnglishName(String system) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "ENGLISH_NAME as englishName  " + "from SYSTEM_DEFINE  ";

		// sql加上对SYSTEM的限定条件
		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(system));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, String.class);
	}


}
