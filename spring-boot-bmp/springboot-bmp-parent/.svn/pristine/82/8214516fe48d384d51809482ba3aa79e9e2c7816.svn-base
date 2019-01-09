package com.newcore.bmp.dao.impl.errorMonitor;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.dao.api.errorMonitor.ErrorMonitorDao;
import com.newcore.bmp.models.webvo.RunningBatchVo;

import lombok.extern.slf4j.Slf4j;

import com.newcore.bmp.models.errorJudge.ErrorDefine;
import com.newcore.bmp.models.errorJudge.ErrorTrail;
import com.newcore.bmp.models.errorJudge.SelectField;

@Repository("errorMonitorDao")
@Slf4j
public class ErrorMonitorDaoImpl implements ErrorMonitorDao {

	@Autowired
	JdbcOperations jdbcTemplate;

	// Select “异常规则定义表”的所有记录到内存中
	public List<ErrorDefine> GetErrorDefine(String system) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		String sql = "select " + "ERR_REASON_ID as errReasonId, " + "ERR_REASON_DETAIL as errReasonDetail, "
				+ "ERR_SCALE as errScale, " + "ERR_JUDGE_CONDITION as errJudgeCondition, "
				+ "ERR_ELIMINATE_CONDITION  as errEliminateCondition, " + "SYSTEM as system " + "from ERROR_DEFINE ";

		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(system));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return (List<ErrorDefine>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(ErrorDefine.class), parameter);
	}

	// Select
	// MONITOR_QUEUE_V8左连接BATCH_DEF，where条件=“异常规则”的“异常判定条件”，到内存中(即该异常规则对应的异常记录的集合)
	public List<SelectField> SelectField(String system, String errorReasonDetail, String whereCondition) {

		// 构造sql
		String sql;

		if (errorReasonDetail.equals(new String("批作业当日未重新启动")) || 
				errorReasonDetail.equals(new String("日间批作业未运行"))
		   ) {

			sql = "select " + "NULL as taskId, " + "NULL as startExecTime, " + "batch_def.batch_id as batchTxNo, "
					+ "NULL as whereClause, " + "NULL as endExecTime, " + "NULL as execStat, " + "NULL as endProcRec, "
					+ "NULL as errProcRec, " + "NULL as execProcRec, " + "NULL as priClass, " + "NULL as bizParamDesc, "
					+ "NULL as oclkBranchNo, " + "NULL as oclkClerkNo, "
					+ "bmp_province.PROV_BRANCH_CODE  as provBranchCode, " + "NULL as lastUpdTime " +

			"from batch_def, bmp_province ";
			
			sql += "where " + whereCondition;

			//batch_def中RULE_START_EXEC_TIME，RULE_END_EXEC_TIME为空的不监控
			sql += " and BATCH_DEF.RULE_START_EXEC_TIME is not null and BATCH_DEF.RULE_END_EXEC_TIME is not null ";

			return (List<SelectField>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(SelectField.class));

		} 
		else {
			sql = "select " + "to_char(MONITOR_QUEUE_" + system + ".TASK_ID) as taskId, " + "to_char(MONITOR_QUEUE_"
					+ system + ".START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as startExecTime, " + "to_char(MONITOR_QUEUE_"
					+ system + ".BATCH_TX_NO) as batchTxNo, " + "MONITOR_QUEUE_" + system
					+ ".WHERE_CLAUSE as whereClause, " + "to_char(MONITOR_QUEUE_" + system
					+ ".END_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as endExecTime, " + "to_char(MONITOR_QUEUE_" + system
					+ ".EXEC_STAT) as execStat, " + "MONITOR_QUEUE_" + system + ".END_PROC_REC  as endProcRec, "
					+ "MONITOR_QUEUE_" + system + ".ERR_PROC_REC as errProcRec, " + "MONITOR_QUEUE_" + system
					+ ".EXEC_PROC_REC as execProcRec, " + "MONITOR_QUEUE_" + system + ".PRI_CLASS as priClass, "
					+ "MONITOR_QUEUE_" + system + ".BIZ_PARAM_DESC as bizParamDesc, " + "MONITOR_QUEUE_" + system
					+ ".OCLK_BRANCH_NO as oclkBranchNo, " + "MONITOR_QUEUE_" + system
					+ ".OCLK_CLERK_NO as oclkClerkNo, " + "MONITOR_QUEUE_" + system
					+ ".PROV_BRANCH_CODE as provBranchCode, " + "to_char(MONITOR_QUEUE_" + system
					+ ".LAST_UPDTIME,'yyyy-mm-dd hh24:mi:ss') as lastUpdTime " +

			"from MONITOR_QUEUE_" + system + " " + "join BATCH_DEF " + "on to_char(MONITOR_QUEUE_" + system
					+ ".batch_tx_no) = BATCH_DEF.BATCH_ID ";
			
			//加上对system的限定
			sql += "and BATCH_DEF.SYSTEM = ? ";

			//batch_def中RULE_START_EXEC_TIME，RULE_END_EXEC_TIME为空的不监控
			sql += " and BATCH_DEF.RULE_START_EXEC_TIME is not null and BATCH_DEF.RULE_END_EXEC_TIME is not null ";
			
			sql += "where " + whereCondition;
			return (List<SelectField>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(SelectField.class),system);
		}
	}

	// 根据(机构号，批作业ID，执行条件，异常原因ID）到异常轨迹表中查找对应记录
	public List<ErrorTrail> FindInErrorTrail(String system, String provinceCode, String batchTxNo, String whereClause,
			String errReasonId, String startExecTime) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "et.ID as id, " + "et.TASK_ID as taskId, " + "et.BATCH_TX_NO as batchTxNo, "
				+ "et.PROV_BRANCH_CODE as provBranchCode, " + "et.WHERE_CLAUSE as whereClause, "
				+ "et.ERR_REASON_ID as errReasonId, " + "et.ERR_JUDGE_COUNT as errJudgeCount, "
				+ "et.ERR_ELIMITATE_FLAG as errElimitateFlag, " + "et.ERR_FIRST_JUDGE_TIME as errFirstJudgeTime, "
				+ "et.ERR_RECENT_JUDGE_TIME as errRecentJudgeTime, " + "et.ERR_ELIMITATE_TIME as errElimitateTime, "
				+ "to_char(et.START_EXEC_TIME, 'yyyy-mm-dd hh24:mi:ss') as startExecTime, " + "et.SYSTEM as system " +

		"from ERROR_TRAIL et ";

		// sql加上对PROV_BRANCH_CODE的限定条件
		sql += "where et.PROV_BRANCH_CODE = ? ";
		parameter_arraylist.add(new String(provinceCode));

		// sql加上对BATCH_TX_NO的限定条件
		sql += "and et.BATCH_TX_NO = ? ";
		parameter_arraylist.add(new String(batchTxNo));

		// sql加上对where_clause的限定条件
		if (whereClause == null) {
			sql += "and et.WHERE_CLAUSE is null ";
		} else {
			sql += "and et.WHERE_CLAUSE = ? ";
			parameter_arraylist.add(new String(whereClause));
		}

		// sql加上对ERR_REASON_ID的限定条件

		sql += "and et.ERR_REASON_ID = ? ";
		parameter_arraylist.add(new String(errReasonId));

		// sql加上对startExecTime的限定条件
		if (startExecTime == null) {
			// 未运行的情况
			String today_zero_time = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
			// System.out.println(today_zero_time);

			sql += "and to_char(et.START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";
			parameter_arraylist.add(new String(today_zero_time));

		} else {
			sql += "and to_char(et.START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";
			parameter_arraylist.add(new String(startExecTime));
		}

		// sql加上对SYSTEM的限定条件

		sql += "and et.SYSTEM = ? ";
		parameter_arraylist.add(new String(system));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return (List<ErrorTrail>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(ErrorTrail.class), parameter);
	}

	// insert一条“异常记录”到异常轨迹表，记录“机构号”，“批作业ID”，“执行条件”，“异常原因ID”；设置”异常累计判定次数”,“异常首次判定时间”，“异常最新判定时间”
	public void InsertErrorTrail(String system, SelectField sf, ErrorDefine ed) {
		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 批量执行要更新的纪录
		String sql = "insert into ERROR_TRAIL " + "values(ERROR_TRAIL_SEQ.NEXTVAL, ";

		// sql加上对TaskId的限定条件
		if (sf.getTaskId() == null) {
			sql += "null, ";
		} else {
			sql += "?, ";
			parameter_arraylist.add(Integer.parseInt(sf.getTaskId()));
		}

		// sql加上对BATCH_TX_NO的限定条件
		sql += "?, ";
		parameter_arraylist.add(sf.getBatchTxNo());

		// sql加上对PROV_BRANCH_CODE的限定条件
		sql += "?, ";
		parameter_arraylist.add(sf.getProvBranchCode());

		// sql加上对where_clause的限定条件
		if (sf.getWhereClause() == null) {
			sql += "null, ";
		} else {
			sql += "?, ";
			parameter_arraylist.add(sf.getWhereClause());
		}

		// sql加上对ERR_REASON_ID的限定条件
		sql += "?, ";
		parameter_arraylist.add(ed.getErrReasonId());

		// 加上其余的参数
		sql += "1, 0, SYSDATE, SYSDATE, null,";

		// sql加上对StartExecTime的限定条件
		if (sf.getStartExecTime() == null) {
			// 未运行批作业的情形
			// sql += "null, ";
			sql += "trunc(SYSDATE), ";

		} else {
			sql += "to_date(?,'yyyy-mm-dd hh24:mi:ss'),  ";
			parameter_arraylist.add(sf.getStartExecTime());
		}

		// sql加上对system的限定条件
		sql += "? ";
		parameter_arraylist.add(system);

		// sql结束
		sql += ")";

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		jdbcTemplate.update(sql, parameter);

	}


	
	// 根据（机构号，批作业ID，执行条件，异常原因ID），update异常轨迹表，更新“异常最新判定时间”,”异常累计判定次数”
	public void updateErrorTrail(final List<ErrorTrail> resET_has_where_clause,
			final List<ErrorTrail> resET_has_not_where_clause) {

		// 批量执行有where_clause的update语句
		updateBatch_has_where_clause_in_judge_stage(resET_has_where_clause);

		// 批量执行没有where_clause的update语句
		updateBatch_no_where_clause_in_judge_stage(resET_has_not_where_clause);
	}
	


	// 批量更新有where_clause的update语句
	public void updateBatch_has_where_clause_in_judge_stage(final List<ErrorTrail> resETs) {
		
		// 批量执行要更新的纪录
		String sql = "update ERROR_TRAIL set " + "ERR_RECENT_JUDGE_TIME= SYSDATE, ";

		sql += "ERR_ELIMITATE_FLAG = 0, ERR_ELIMITATE_TIME = null, ";
		
		// sql加上对ERR_JUDGE_COUNT的限定条件
		sql += "ERR_JUDGE_COUNT= ? ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "where BATCH_TX_NO = ? ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "and PROV_BRANCH_CODE = ? ";

		// sql加上对ERR_REASON_ID的限定条件
		sql += "and ERR_REASON_ID = ? ";

			sql += "and to_char(START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";

		// sql加上对SYSTEM的限定条件
		sql += "and SYSTEM = ? ";

		// sql加上对where_clause的限定条件
		sql += "and WHERE_CLAUSE = ? ";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ErrorTrail et = resETs.get(i);
				ps.setInt(1, Integer.parseInt(et.getErrJudgeCount()) + 1);
				ps.setString(2, et.getBatchTxNo());
				ps.setString(3, et.getProvBranchCode());
				ps.setString(4, et.getErrReasonId());
				ps.setString(5, et.getStartExecTime());
				ps.setString(6, et.getSystem());
				ps.setString(7, et.getWhereClause());

			}

			@Override
			public int getBatchSize() {
				return resETs.size();
			}
		});
	}
	
	// 批量更新没有where_clause的update语句(judge阶段)
	public void updateBatch_no_where_clause_in_judge_stage(final List<ErrorTrail> resETs) {
		
		// 批量执行要更新的纪录
		String sql = "update ERROR_TRAIL set " + "ERR_RECENT_JUDGE_TIME= SYSDATE, ";

		// sql加上对ERR_JUDGE_COUNT的限定条件
		sql += "ERR_JUDGE_COUNT= ? ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "where BATCH_TX_NO = ? ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "and PROV_BRANCH_CODE = ? ";

		// sql加上对ERR_REASON_ID的限定条件
		sql += "and ERR_REASON_ID = ? ";

			sql += "and to_char(START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";

		// sql加上对SYSTEM的限定条件
		sql += "and SYSTEM = ? ";

		// sql加上对where_clause的限定条件
		sql += "and WHERE_CLAUSE is null ";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ErrorTrail et = resETs.get(i);
				ps.setInt(1, Integer.parseInt(et.getErrJudgeCount()) + 1);
				ps.setString(2, et.getBatchTxNo());
				ps.setString(3, et.getProvBranchCode());
				ps.setString(4, et.getErrReasonId());
				ps.setString(5, et.getStartExecTime());
				ps.setString(6, et.getSystem());
			}

			@Override
			public int getBatchSize() {
				return resETs.size();
			}
		});
	}

	// -------------------------------------------------------------------------------

	// 20180329
	// 异常消除----------------------------------------------------------------------

	// 取出没有异常消除的结果集
	public List<ErrorTrail> GetErrorRecord(String system) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		String sql = "select " + "et.ID as id, " + "et.TASK_ID as taskId, " + "et.BATCH_TX_NO as batchTxNo, "
		
				+ "et.PROV_BRANCH_CODE as provBranchCode, " + "et.WHERE_CLAUSE as whereClause, "
				+ "et.ERR_REASON_ID as errReasonId, " + "et.ERR_JUDGE_COUNT as errJudgeCount, "
				+ "et.ERR_ELIMITATE_FLAG as errElimitateFlag, " + "et.ERR_FIRST_JUDGE_TIME as errFirstJudgeTime, "
				+ "et.ERR_RECENT_JUDGE_TIME as errRecentJudgeTime, " + "et.ERR_ELIMITATE_TIME as errElimitateTime, "
				+ "ed.ERR_ELIMINATE_CONDITION as errEliminateCondition, "
				+ "to_char(et.START_EXEC_TIME, 'yyyy-mm-dd hh24:mi:ss') as startExecTime, " + "et.SYSTEM as system, " 
				+ "ed.ERR_REASON_DETAIL as errReasonDetail " +

		"from ERROR_TRAIL et " + "join ERROR_DEFINE ed " + "on et.ERR_REASON_ID = ed.ERR_REASON_ID " +

		"where et.ERR_ELIMITATE_FLAG = 0 ";

		// sql加上对SYSTEM的限定条件
		sql += "and et.SYSTEM = ? ";
		parameter_arraylist.add(system);

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return (List<ErrorTrail>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(ErrorTrail.class), parameter);
	}

	
	// 根据(机构号，批作业ID，执行条件，异常原因ID）到MonitorQueue中查找对应记录
	public int FindInMonitorQueue(String system, String provinceCode, String batchTxNo, String whereClause,
			String errEliminateCondition, String errReasonId, String startExecTime, String errReasonDetail) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		String sql;

		
		//批作业当日未重新启动,或日间批作业未运行
		if ( errReasonDetail.equals(new String("批作业当日未重新启动")) || 
				errReasonDetail.equals(new String("日间批作业未运行"))	
			) {

			String today_zero_time = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
			System.out.println(today_zero_time);
			if (today_zero_time.equals(startExecTime)) {// 当天未运行的情况
				
				sql = "select count(*) " +

				"from MONITOR_QUEUE_" + system + " ";

				// sql加上对PROV_BRANCH_CODE的限定条件
				sql += "where MONITOR_QUEUE_" + system + ".PROV_BRANCH_CODE = ? ";
				parameter_arraylist.add(new String(provinceCode));

				// sql加上对BATCH_TX_NO的限定条件
				sql += "and to_char(MONITOR_QUEUE_" + system + ".BATCH_TX_NO) = ? ";
				parameter_arraylist.add(new String(batchTxNo));

				// 未运行批作业的情况
				sql += "and to_char(TRUNC(MONITOR_QUEUE_" + system + ".START_EXEC_TIME),'yyyy-mm-dd hh24:mi:ss') = ? ";
				parameter_arraylist.add(new String(startExecTime));
				
				if (errReasonDetail.equals(new String("日间批作业未运行"))) {
					sql += "and MONITOR_QUEUE_" + system + ".EXEC_STAT = '1' ";

				}
				
			} 
			else {
				// 不是当天未运行的情况，消除之
				return 1;
			}
			
		} 
		else {
			// 构造sql
			sql = "select count(*) " +

			"from MONITOR_QUEUE_" + system + " ";

			// sql加上对PROV_BRANCH_CODE的限定条件
			sql += "where MONITOR_QUEUE_" + system + ".PROV_BRANCH_CODE = ? ";
			parameter_arraylist.add(new String(provinceCode));

			// sql加上对BATCH_TX_NO的限定条件
			sql += "and to_char(MONITOR_QUEUE_" + system + ".BATCH_TX_NO) = ? ";
			parameter_arraylist.add(new String(batchTxNo));

			// sql加上对where_clause的限定条件
			if (whereClause == null) {
				sql += "and MONITOR_QUEUE_" + system + ".WHERE_CLAUSE is null ";
			} else {
				sql += "and MONITOR_QUEUE_" + system + ".WHERE_CLAUSE = ? ";
				parameter_arraylist.add(new String(whereClause));
			}

			if ( errReasonDetail.equals(new String("日间批作业在计划运行时间内异常终止"))) {
				//日间批作业在计划运行时间内异常终止，暂时直接消除
				return 1;
				
			}
			else {
				// sql加上对startExecTime的限定条件
				if (startExecTime == null) {
					sql += "and MONITOR_QUEUE_" + system + ".START_EXEC_TIME is null ";
				} else {
					sql += "and to_char(MONITOR_QUEUE_" + system + ".START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";
					parameter_arraylist.add(new String(startExecTime));
				}
			}
			
			// sql加上异常消除条件
			sql += "and " + errEliminateCondition;

		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, Integer.class);
	}
	
	
	/*
	// 根据(机构号，批作业ID，执行条件，异常原因ID）到MonitorQueue中查找对应记录
	public int FindInMonitorQueue(String system, String provinceCode, String batchTxNo, String whereClause,
			String errEliminateCondition, String errReasonId, String startExecTime, String errReasonDetail) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		String sql;

		
		
		//if ( ! errReasonDetail.equals(new String("批作业计划内时间未启动"))) {
		if ( ! errReasonDetail.equals(new String("批作业当日未重新启动"))) {

			// 构造sql
			sql = "select count(*) " +

			"from MONITOR_QUEUE_" + system + " ";

			// sql加上对PROV_BRANCH_CODE的限定条件
			sql += "where MONITOR_QUEUE_" + system + ".PROV_BRANCH_CODE = ? ";
			parameter_arraylist.add(new String(provinceCode));

			// sql加上对BATCH_TX_NO的限定条件
			sql += "and to_char(MONITOR_QUEUE_" + system + ".BATCH_TX_NO) = ? ";
			parameter_arraylist.add(new String(batchTxNo));

			// sql加上对where_clause的限定条件
			if (whereClause == null) {
				sql += "and MONITOR_QUEUE_" + system + ".WHERE_CLAUSE is null ";
			} else {
				sql += "and MONITOR_QUEUE_" + system + ".WHERE_CLAUSE = ? ";
				parameter_arraylist.add(new String(whereClause));
			}

			if ( ! errReasonDetail.equals(new String("日间批作业在计划运行时间内异常终止"))) {

				// sql加上对startExecTime的限定条件
				if (startExecTime == null) {
					sql += "and MONITOR_QUEUE_" + system + ".START_EXEC_TIME is null ";
				} else {
					sql += "and to_char(MONITOR_QUEUE_" + system + ".START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";
					parameter_arraylist.add(new String(startExecTime));
				}
			}
			else {
				//日间批作业在计划运行时间内异常终止，暂时直接消除
				return 1;
			}
			
			// sql加上异常消除条件
			sql += "and " + errEliminateCondition;

		} else {
			// 未运行批作业的情况
			String today_zero_time = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
			System.out.println(today_zero_time);
			if (today_zero_time.equals(startExecTime)) {
				// 当天未运行的情况
				sql = "select count(*) " +

				"from MONITOR_QUEUE_" + system + " ";

				// sql加上对PROV_BRANCH_CODE的限定条件
				sql += "where MONITOR_QUEUE_" + system + ".PROV_BRANCH_CODE = ? ";
				parameter_arraylist.add(new String(provinceCode));

				// sql加上对BATCH_TX_NO的限定条件
				sql += "and to_char(MONITOR_QUEUE_" + system + ".BATCH_TX_NO) = ? ";
				parameter_arraylist.add(new String(batchTxNo));

				// 未运行批作业的情况
				sql += "and to_char(TRUNC(MONITOR_QUEUE_" + system + ".START_EXEC_TIME),'yyyy-mm-dd hh24:mi:ss') = ? ";
				parameter_arraylist.add(new String(startExecTime));
			} else {
				// 不是当天未运行的情况，消除之
				return 1;
			}

		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, parameter, Integer.class);
	}
	*/

	// 根据（机构号，批作业ID，执行条件，异常原因ID），update异常轨迹表，更新“异常最新判定时间”,”异常累计判定次数”
	public void eliminateErrorTrail(final List<ErrorTrail> resET_has_where_clause,
			final List<ErrorTrail> resET_has_not_where_clause) {

		// 批量执行有where_clause的update语句
		updateBatch_has_where_clause(resET_has_where_clause);

		// 批量执行没有where_clause的update语句
		updateBatch_no_where_clause(resET_has_not_where_clause);
	}

	// 批量更新有where_clause的update语句
	public void updateBatch_has_where_clause(final List<ErrorTrail> resETs) {

		// 批量执行要更新的纪录
		String sql = "update ERROR_TRAIL set " + "ERR_ELIMITATE_TIME= SYSDATE, " + "ERR_ELIMITATE_FLAG = 1 ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "where BATCH_TX_NO = ? ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "and PROV_BRANCH_CODE = ? ";

		// sql加上对ERR_REASON_ID的限定条件
		sql += "and ERR_REASON_ID = ? ";

		// sql加上对startExecTime的限定条件
		sql += "and to_char(START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";

		// sql加上对SYSTEM的限定条件
		sql += "and SYSTEM = ? ";

		// sql加上对where_clause的限定条件
		sql += "and WHERE_CLAUSE = ? ";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ErrorTrail et = resETs.get(i);
				ps.setString(1, et.getBatchTxNo());
				ps.setString(2, et.getProvBranchCode());
				ps.setString(3, et.getErrReasonId());
				ps.setString(4, et.getStartExecTime());
				ps.setString(5, et.getSystem());
				ps.setString(6, et.getWhereClause());
			}

			@Override
			public int getBatchSize() {
				return resETs.size();
			}
		});
	}

	// 批量更新没有where_clause的update语句
	public void updateBatch_no_where_clause(final List<ErrorTrail> resETs) {

		// 批量执行要更新的纪录
		String sql = "update ERROR_TRAIL set " + "ERR_ELIMITATE_TIME= SYSDATE, " + "ERR_ELIMITATE_FLAG = 1 ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "where BATCH_TX_NO = ? ";

		// sql加上对BATCH_TX_NO的限定条件
		sql += "and PROV_BRANCH_CODE = ? ";

		// sql加上对ERR_REASON_ID的限定条件
		sql += "and ERR_REASON_ID = ? ";

		// sql加上对startExecTime的限定条件
		sql += "and to_char(START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') = ? ";

		// sql加上对SYSTEM的限定条件
		sql += "and SYSTEM = ? ";

		// sql加上对where_clause的限定条件
		sql += "and WHERE_CLAUSE is null ";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ErrorTrail et = resETs.get(i);
				ps.setString(1, et.getBatchTxNo());
				ps.setString(2, et.getProvBranchCode());
				ps.setString(3, et.getErrReasonId());
				ps.setString(4, et.getStartExecTime());
				ps.setString(5, et.getSystem());
			}

			@Override
			public int getBatchSize() {
				return resETs.size();
			}
		});
	}

	@Override
	public List<ErrorDefine> selectChudanErrorReasonId(String system) {
				
		String sql ="select "
				+ "ERR_REASON_ID as errReasonId, "
				+ "ERR_REASON_DETAIL as errReasonDetail, "
				+ "ERR_SCALE as errScale, "
				+ "ERR_JUDGE_CONDITION as errJudgeCondition, "
				+ "ERR_ELIMINATE_CONDITION as errEliminateCondition, "
				+ "SYSTEM as system, "
				+ "MONITOR_INTERVAL_COUNT as monitorIntervalCount,"
				+ "ONLY_MAIL_ONCE_FLAG as onlyMailOnceFlag "
				+ "from ERROR_DEFINE "
				+ "where SYSTEM = ? "
				+ "and ERR_SCALE = '严重' ";
		
		return (List<ErrorDefine>)  jdbcTemplate.query(sql, DaoUtils.createRowMapper(ErrorDefine.class), system);
	}

	@Override
	public List<ErrorTrail> selectChudanErrorRecord(String system, String errReasonId, String monitorIntervalCount) {
		String sql = "select " 
				+ "et.ID as id, " 
				+ "et.TASK_ID as taskId, " 
				+ "et.BATCH_TX_NO as batchTxNo, "
				+ "et.PROV_BRANCH_CODE as provBranchCode, " 
				+ "et.WHERE_CLAUSE as whereClause, "
				+ "et.ERR_REASON_ID as errReasonId, " 
				+ "et.ERR_JUDGE_COUNT as errJudgeCount, "
				+ "et.ERR_ELIMITATE_FLAG as errElimitateFlag, " 
				+ "et.ERR_FIRST_JUDGE_TIME as errFirstJudgeTime, "
				+ "et.ERR_RECENT_JUDGE_TIME as errRecentJudgeTime, " 
				+ "et.ERR_ELIMITATE_TIME as errElimitateTime, "
				+ "to_char(et.START_EXEC_TIME, 'yyyy-mm-dd hh24:mi:ss') as startExecTime, " + "et.SYSTEM as system,"
				+ "bd.BATCH_NAME as batchName,"
				+ "bp.PROV_BRANCH_NAME as provBranchName, "
				+ "ed.ERR_REASON_DETAIL as errReasonDetail,"
				+ "ed.ONLY_MAIL_ONCE_FLAG as onlyMailOnceFlag " +

				"from ERROR_TRAIL et "
				+ "join BATCH_DEF bd "
				+ "on et.BATCH_TX_NO = bd.BATCH_ID "
				+ "and et.SYSTEM = bd.SYSTEM "
				+ "join BMP_PROVINCE bp "
				+ "on et.PROV_BRANCH_CODE = bp.PROV_BRANCH_CODE "
				+ "join ERROR_DEFINE ed "
				+ "on et.ERR_REASON_ID = ed.ERR_REASON_ID "
				+ "where bd.CHUDAN_FLAG = 1 "
				+ "and bd.SYSTEM = ? "
				+ "and et.ERR_REASON_ID = ? "
				+ "and mod(ERR_JUDGE_COUNT, ?) = 0 "
				+ "and et.ERR_ELIMITATE_FLAG = 0 ";

				return (List<ErrorTrail>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(ErrorTrail.class), system, errReasonId, monitorIntervalCount);
	}
	

	
}
