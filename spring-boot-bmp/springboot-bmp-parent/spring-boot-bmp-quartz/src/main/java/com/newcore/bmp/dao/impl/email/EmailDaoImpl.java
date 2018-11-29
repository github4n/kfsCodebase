package com.newcore.bmp.dao.impl.email;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.dao.api.email.EmailDao;
import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.models.errorJudge.ErrorTrail;


@Repository("emailDao")
public class EmailDaoImpl implements EmailDao {

	@Autowired
	JdbcOperations jdbcTemplate;
	
	// 邮件发送涉及的数据库查询

	// 计算当天应运行日终批作业总数
	public int calcBatchNums(String system, String batchTypeDetail) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select count(*) " + "from BATCH_DEF ";

		// sql加上对SYSTEM的限定条件
		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(system));

		// sql加上对BATCH_TYPE_DETAIL的限定条件
		sql += "and BATCH_TYPE_DETAIL = ?";
		parameter_arraylist.add(new String(batchTypeDetail));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, Integer.class);

	}

	// 计算共有多少个省份
	@Override
	public int calcprovinceNums() {
		// 构造sql
		String sql = "select count(*) from BMP_PROVINCE";

		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	// 计算当天实际运行的日终批作业个数
	@Override
	public int calcUnRunBatchNums(String system, String batchTypeDetail, String errReasonDetail) {
		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select count(*) " + "from ERROR_TRAIL et " + "join BATCH_DEF bd "
				+ "on et.BATCH_TX_NO = bd.batch_id " + "and et.SYSTEM = bd.SYSTEM "
				+ "join ERROR_DEFINE ed "
				+ "on et.ERR_REASON_ID = ed.ERR_REASON_ID " + "and et.SYSTEM = ed.SYSTEM ";

		// sql加上对SYSTEM的限定条件
		sql += "where et.SYSTEM = ? ";
		parameter_arraylist.add(new String(system));

		sql += "and et.ERR_ELIMITATE_FLAG = '0'";

		// sql加上对ERR_REASON_DETAIL的限定条件
		sql += "and ed.ERR_REASON_DETAIL = ? ";
		parameter_arraylist.add(new String(errReasonDetail));

		// sql加上对BATCH_TYPE_DETAIL的限定条件
		sql += "and BATCH_TYPE_DETAIL = ?";
		parameter_arraylist.add(new String(batchTypeDetail));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, Integer.class);

	}

	// 计算当天未运行的日终批作业涉及的分公司个数
	@Override
	public int calcRelativeProvinceNums(String system, String batchTypeDetail, String errReasonDetail) {
		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select count(*) " + "from " + "( " +

		"select Unique PROV_BRANCH_CODE " + "from ERROR_TRAIL et " + "join " + "BATCH_DEF bd "
				+ "on et.BATCH_TX_NO = bd.batch_id " + " and et.SYSTEM = bd.SYSTEM "
				+ "join ERROR_DEFINE ed "
				+ "on et.ERR_REASON_ID = ed.ERR_REASON_ID " + "and et.SYSTEM = ed.SYSTEM ";

		// sql加上对SYSTEM的限定条件
		sql += "where et.SYSTEM = ? ";
		parameter_arraylist.add(new String(system));

		sql += "and et.ERR_ELIMITATE_FLAG = '0'";

		// sql加上对ERR_REASON_DETAIL的限定条件
		sql += "and ed.ERR_REASON_DETAIL = ? ";
		parameter_arraylist.add(new String(errReasonDetail));

		// sql加上对BATCH_TYPE_DETAIL的限定条件
		sql += "and bd.BATCH_TYPE_DETAIL = ?";
		parameter_arraylist.add(new String(batchTypeDetail));

		sql += ") ";

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, Integer.class);
	}

	// 查出对应的异常记录给EXCEL
	public List<ErrorTrail> SelectErrorTrail(String system, String errReasonDetail) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select "+ 
				"bd.BATCH_TYPE_DETAIL as batchTypeDetail, "+
				"bp.PROV_BRANCH_NAME as provBranchName, "+
				"et.PROV_BRANCH_CODE as provBranchCode, "+
				"bd.BATCH_NAME as batchName, "+
				"et.BATCH_TX_NO as batchTxNo, "+
				"ed.ERR_REASON_DETAIL as errReasonDetail, "+
				"et.SYSTEM as system, "+
				"et.ERR_JUDGE_COUNT as errJudgeCount, "+
				"to_char(et.ERR_FIRST_JUDGE_TIME, 'yyyy-mm-dd hh24:mi:ss') as errFirstJudgeTime, "+
				"to_char(et.ERR_RECENT_JUDGE_TIME,'yyyy-mm-dd hh24:mi:ss') as errRecentJudgeTime, "+
				"to_char(et.START_EXEC_TIME,'yyyy-mm-dd') as startExecTime "+
				"from error_trail et  "+
				"join ERROR_DEFINE ed "+
				"on et.ERR_REASON_ID = ed.ERR_REASON_ID "+ 
				"join BATCH_DEF bd "+
				"on et.BATCH_TX_NO = bd.BATCH_ID "+
				"and et.SYSTEM = bd.SYSTEM "+
				"join BMP_PROVINCE bp "+
				"on et.PROV_BRANCH_CODE = bp.PROV_BRANCH_CODE ";
				
		// sql加上对SYSTEM的限定条件
		sql += "where et.SYSTEM = ? ";
		parameter_arraylist.add(new String(system));
						
		// sql加上对ERR_REASON_ID的限定条件
		sql += "and ed.ERR_REASON_DETAIL = ? ";
		parameter_arraylist.add(new String(errReasonDetail));
		
		sql += "and et.ERR_ELIMITATE_FLAG = 0 ";
		
		
		
		//按批作业种类排序
		sql+= "order by bd.BATCH_TYPE_DETAIL";

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return (List<ErrorTrail>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(ErrorTrail.class), parameter);
	}

	
	// 查出对应的EMAIL_Subscription
	public List<EmailSubscription> SelectEmailSubscription(String system) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql =   "select "+   
				 "es.SYSTEM as system, "+   
				 "es.TYPE as type, "+   
				 "es.EMAIL_ADDRESS as emailAddress, "+   
				 "es.EMAIL_NAME as emailName "+
				 "from EMAIL_SUBSCRIPTION es ";
				
		// sql加上对SYSTEM的限定条件
		sql += "where es.SYSTEM = ? ";
		parameter_arraylist.add(new String(system));
						
		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return (List<EmailSubscription>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(EmailSubscription.class), parameter);
	}
	
	// 查出对应的SelectSystemEnglishName
	public String SelectSystemEnglishName(String system) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select "+    
				  "ENGLISH_NAME as englishName  "+     
				  "from SYSTEM_DEFINE  ";
				
		// sql加上对SYSTEM的限定条件
		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(system));
						
		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, String.class);		
	}
	
	
	// 查出对应的EMAIL_Subscription(Administrator)
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
