package com.newcore.bmp.dao.impl.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.dao.api.email.EmailDao;
import com.newcore.bmp.models.email.EmailSubscription;


@Repository("emailDao")
public class EmailDaoImpl implements EmailDao {

	@Autowired
	JdbcOperations jdbcTemplate;		
	
	//获取系统对应的邮件人列表
	public List<EmailSubscription> SelectEmailSubscriptionOfSystem(String system) {
		// 构造sql
		String sql =   "select "+   
				 "es.SYSTEM as system, "+   
				 "es.TYPE as type, "+   
				 "es.EMAIL_ADDRESS as emailAddress, "+   
				 "es.EMAIL_NAME as emailName "+
				 "from EMAIL_SUBSCRIPTION es ";
				
		// sql加上对SYSTEM的限定条件
		sql += "where es.SYSTEM = ? ";
						
		return (List<EmailSubscription>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(EmailSubscription.class), system);
	}
	
	//只给管理员发
	public List<EmailSubscription> SelectEmailSubscriptionOfAdministrator(String system) {
		String administrator = "作业管理平台系统管理员";
		// 构造sql
		String sql =   "select "+   
				 "es.SYSTEM as system, "+   
				 "es.TYPE as type, "+   
				 "es.EMAIL_ADDRESS as emailAddress, "+   
				 "es.EMAIL_NAME as emailName "+
				 "from EMAIL_SUBSCRIPTION es ";
				
		// sql加上对SYSTEM的限定条件
		sql += "where es.SYSTEM = ?  and es.EMAIL_NAME = ? ";
						
		return (List<EmailSubscription>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(EmailSubscription.class), system, administrator);
	}
}
