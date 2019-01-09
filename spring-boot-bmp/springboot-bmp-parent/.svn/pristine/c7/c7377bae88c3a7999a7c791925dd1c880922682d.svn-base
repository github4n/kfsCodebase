package com.newcore.bmp.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.codec.net.QCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.dao.api.TaskRunMonitorDao;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.authority.models.ClerkResource;
import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.BatchCommandDef;
import com.newcore.bmp.models.webvo.BatchVO;
import com.newcore.bmp.models.webvo.DatabaseConnection;
import com.newcore.bmp.models.webvo.FailBatchVo;
import com.newcore.bmp.models.webvo.MapBatch;
import com.newcore.bmp.models.webvo.QueryBatch;
import com.newcore.bmp.models.webvo.QueryServer;
import com.newcore.bmp.models.webvo.RunningBatchVo;
import com.newcore.bmp.models.webvo.ServerInfo;
import com.newcore.bmp.models.webvo.SystemCommandDef;
import com.newcore.bmp.models.webvo.ZongKongBatch;

@Repository("taskRunMonitorDao")
public class TaskRunMonitorDaoImpl implements TaskRunMonitorDao {

	@Autowired
	JdbcOperations jdbcTemplate;

	// 作业运行监控
	// 1.1.2.1 case1 根据所有过滤条件，查询结果

	public Map<String, Object> GetRunningBatchList(String topic, String provCode, String batchId,
			String batchTypeDetail, String order, String length, String start) {

		Map<String, Object> amap = new HashMap<String, Object>();

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "rownum as rn," + "bd.SYSTEM as topic, " + "bp.PROV_BRANCH_CODE as proCode, "
				+ "bp.PROV_BRANCH_NAME as proName, " + "bd.BATCH_ID as batchId, " + "bd.BATCH_NAME as batchName, "
				+ "bd.BATCH_TYPE_DETAIL as batchTypeDetail, " + "nvl(mq.WHERE_CLAUSE,'无') as whereClause, "
				+ "to_char(mq.START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as startExecTime, "
				+ "mq.END_PROC_REC  as endProcRecord, " + "mq.ERR_PROC_REC as errProcRecord, "
				+ "mq.EXEC_PROC_REC as execProcRecord, " + "mq.OCLK_BRANCH_NO as oclkBranchNo, "
				+ "mq.OCLK_CLERK_NO as oclkClerkNo " + "from MONITOR_QUEUE_";

		// 指定业务系统
		sql += topic;

		sql = sql + " mq " + "join BATCH_DEF bd " + "on to_char(mq.BATCH_TX_NO) = bd.BATCH_ID " + "and bd.SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		sql = sql + "join BMP_PROVINCE bp " + "on mq.PROV_BRANCH_CODE = bp.PROV_BRANCH_CODE ";

		// 加上运行状态位限定
		sql += "where mq.EXEC_STAT = '1' ";

		// 查询sql加上对机构号（provCode）的限定条件
		if (!(provCode.equals(new String("0")))) {
			sql += "and mq.PROV_BRANCH_CODE= ? ";
			parameter_arraylist.add(new String(provCode));
		}

		// 查询sql加上对批作业id（batchId）的限定条件
		if (!(batchId.equals(new String("0")))) {
			sql += "and to_char(mq.BATCH_TX_NO) = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 查询sql加上对作业类型（batchType）的限定条件
		if (!batchTypeDetail.equals("0")) {
			sql += "and bd.BATCH_TYPE_DETAIL = ? ";
			parameter_arraylist.add(batchTypeDetail);
		}

		// 查询sql加上对排序（order）的后缀
		if (!(order.equals(new String("0")))) {
			sql += "order by " + order;
		}

		String sql_count = "select count(*) from (" + sql + ")";

		String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(length));
		sql = "select * from (" + sql + ") where rn > " + start + "and rn <= " + end;

		amap.put("start", start);
		amap.put("length", length);

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		// 计算总记录条数
		// String sql_count = "select count(*) from (" + sql + ")";
		Integer totalCount = jdbcTemplate.queryForObject(sql_count, Integer.class, parameter);
		amap.put("recordtotal", totalCount);

		List<RunningBatchVo> list = jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class), parameter);
		amap.put("data", list);
		return amap;
	}

	// 1.1.2.2 case2 根据 “机构号”条件，得到“机构名称”条件
	public List<RunningBatchVo> GetProNameByProCode(String provCode) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select PROV_BRANCH_NAME as proName from BMP_PROVINCE where PROV_BRANCH_CODE = ?";
		parameter_arraylist.add(new String(provCode));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<RunningBatchVo>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class),
				parameter);
	}

	// case3 根据 “机构名称”条件，得到“机构号”条件
	public List<RunningBatchVo> GetProCodeByProName(String provName) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select PROV_BRANCH_CODE as proCode from BMP_PROVINCE where PROV_BRANCH_NAME = ?";
		parameter_arraylist.add(new String(provName));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<RunningBatchVo>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class),
				parameter);
	}

	// 1.1.2.4 case4 根据 “作业id”条件，得到“作业名称”条件
	public List<RunningBatchVo> GetBatchNameByBatchId(String topic, String batchId) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select BATCH_NAME as batchName from BATCH_DEF ";

		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		sql += "and BATCH_ID = ? ";
		parameter_arraylist.add(new String(batchId));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<RunningBatchVo>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class),
				parameter);
	}

	// 1.1.2.5 case5 根据 “作业名称”条件，得到“作业id”条件
	public List<RunningBatchVo> GetBatchIdByBatchName(String topic, String batchName) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select BATCH_ID as batchId from BATCH_DEF ";

		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		sql += "and BATCH_NAME = ? ";
		parameter_arraylist.add(new String(batchName));

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<RunningBatchVo>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class),
				parameter);
	}

	// 1.1.2.6 case6 根据“业务系统分类”，得到“作业id”,”作业名称”列表（未实现）
	public List<RunningBatchVo> GetBatchIdAndBatchNameByTopicAndBatchType(String topic, String batchTypeDetail) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select BATCH_ID as batchId, BATCH_NAME as batchName from BATCH_DEF ";

		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		// 查询sql加上对作业类型（batchType）的限定条件
		if (!batchTypeDetail.equals("0")) {
			sql += "and BATCH_TYPE_DETAIL = ? ";
			parameter_arraylist.add(new String(batchTypeDetail));
		}

		sql += "order by batchId ";

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<RunningBatchVo>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class),
				parameter);
	}

	// 作业异常管理
	// 1.2.2.1 case1 根据所有过滤条件，查询结果
	public Map<String, Object> GetFailBatchList(String topic, String provCode, String batchId, String batchTypeDetail,
			String errScale, String errReasonId, String order, String length, String start) {

		Map<String, Object> amap = new HashMap<String, Object>();

		// 计算总记录数
		Integer totalCount = calcFailBatchListCount(topic, provCode, batchId, batchTypeDetail, errScale, errReasonId);
		amap.put("recordtotal", totalCount);
		// kfs

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "rownum as rn, " + "bd.SYSTEM as topic, " + "et.PROV_BRANCH_CODE as proCode, "
				+ "bp.PROV_BRANCH_NAME as proName, " + "bd.BATCH_ID as batchId, " + "bd.BATCH_NAME as batchName, "
				+ "bd.BATCH_TYPE_DETAIL as batchTypeDetail, " + "et.WHERE_CLAUSE as whereClause, "
				+ "to_char(et.START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as startExecTime, "
				+ "to_char(mq.END_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as endExecTime, "
				+ "mq.END_PROC_REC  as endProcRecord, " + "mq.ERR_PROC_REC as errProcRecord, "
				+ "mq.EXEC_PROC_REC as execProcRecord, " + "mq.OCLK_BRANCH_NO as oclkBranchNo, "
				+ "mq.OCLK_CLERK_NO as oclkClerkNo, " + "ed.ERR_REASON_DETAIL as errReason, "
				+ "ed.ERR_SCALE as errScale, " + "et.ERR_JUDGE_COUNT as errJudgeCount, "
				+ "et.ERR_ELIMITATE_FLAG as errElimitateFlag, "
				+ "to_char(et.ERR_FIRST_JUDGE_TIME,'yyyy-mm-dd hh24:mi:ss') as errFirstJudgeTime, "
				+ "to_char(et.ERR_RECENT_JUDGE_TIME,'yyyy-mm-dd hh24:mi:ss') as errRecentJudgeTime "
				+ "from ERROR_TRAIL et " + "left join " + "MONITOR_QUEUE_";

		// 指定业务系统
		sql += topic;

		sql += " mq " + "on " + "et.TASK_ID = to_char(mq.TASK_ID) " + "and et.BATCH_TX_NO = to_char(mq.BATCH_TX_NO) " +

		"join BATCH_DEF bd " + "on et.BATCH_TX_NO = bd.BATCH_ID " + "and et.SYSTEM = bd.SYSTEM " +

		"join BMP_PROVINCE bp " + "on et.PROV_BRANCH_CODE = bp.PROV_BRANCH_CODE " +

		"join ERROR_DEFINE ed " + "on et.ERR_REASON_ID = ed.ERR_REASON_ID ";

		// 查询sql加上对异常消除位（ERR_ELIMITATE_FLAG）的限定条件
		sql += " where et.ERR_ELIMITATE_FLAG = 0 ";

		// 查询sql加上对系统（topic）的限定条件
		sql += "and et.SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		// 查询sql加上对机构号（provCode）的限定条件
		if (!(provCode.equals(new String("0")))) {
			sql += "and et.PROV_BRANCH_CODE= ? ";
			parameter_arraylist.add(new String(provCode));
		}

		// 查询sql加上对批作业id（batchId）的限定条件
		if (!(batchId.equals(new String("0")))) {
			sql += "and et.BATCH_TX_NO = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 查询sql加上对作业类型（batchType）的限定条件
		if (!batchTypeDetail.equals("0")) {
			sql += "and bd.BATCH_TYPE_DETAIL = ? ";
			parameter_arraylist.add(batchTypeDetail);
		}

		// 查询sql加上对批作业id（errScale）的限定条件
		if (!(errScale.equals(new String("0")))) {
			sql += "and ed.ERR_SCALE = ? ";
			parameter_arraylist.add(new String(errScale));
		}

		// 查询sql加上对异常原因代码（errReasonId）的限定条件
		if (!(errReasonId.equals(new String("0")))) {
			sql += "and et.ERR_REASON_ID = ? ";
			parameter_arraylist.add(new String(errReasonId));
		}

		// 查询sql加上对排序（order）的后缀
		if (!(order.equals(new String("0")))) {
			sql += "order by " + order;
		}

		// String sql_count = "select count(*) from (" + sql + ")";

		String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(length));
		sql = "select * from (" + sql + ") where rn > " + start + "and rn <= " + end;

		amap.put("start", start);
		amap.put("length", length);

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		// Integer totalCount = jdbcTemplate.queryForObject(sql_count,
		// Integer.class, parameter);
		// amap.put("recordtotal", totalCount);

		List<FailBatchVo> list = jdbcTemplate.query(sql, DaoUtils.createRowMapper(FailBatchVo.class), parameter);
		amap.put("data", list);

		return amap;
	}

	// 计算失败记录的数量
	private Integer calcFailBatchListCount(String topic, String provCode, String batchId, String batchTypeDetail,
			String errScale, String errReasonId) {

		// 获取count
		List<Object> parameter_arraylist = new ArrayList<Object>();
		String sql_count = "select count(*) " + "from ERROR_TRAIL et " +

		"join BATCH_DEF bd " + "on et.BATCH_TX_NO = bd.BATCH_ID " + "and et.SYSTEM = bd.SYSTEM " +

		"join ERROR_DEFINE ed " + "on et.ERR_REASON_ID = ed.ERR_REASON_ID ";

		// 查询sql加上对异常消除位（ERR_ELIMITATE_FLAG）的限定条件
		sql_count += " where et.ERR_ELIMITATE_FLAG = 0 ";

		// 查询sql加上对系统（topic）的限定条件
		sql_count += "and et.SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		// 查询sql加上对机构号（provCode）的限定条件
		if (!(provCode.equals(new String("0")))) {
			sql_count += "and et.PROV_BRANCH_CODE= ? ";
			parameter_arraylist.add(new String(provCode));
		}

		// 查询sql加上对批作业id（batchId）的限定条件
		if (!(batchId.equals(new String("0")))) {
			sql_count += "and et.BATCH_TX_NO = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 查询sql加上对作业类型（batchType）的限定条件
		if (!batchTypeDetail.equals("0")) {
			sql_count += "and bd.BATCH_TYPE_DETAIL = ? ";
			parameter_arraylist.add(batchTypeDetail);
		}

		// 查询sql加上对批作业id（errScale）的限定条件
		if (!(errScale.equals(new String("0")))) {
			sql_count += "and ed.ERR_SCALE = ? ";
			parameter_arraylist.add(new String(errScale));
		}

		// 查询sql加上对异常原因代码（errReasonId）的限定条件
		if (!(errReasonId.equals(new String("0")))) {
			sql_count += "and et.ERR_REASON_ID = ? ";
			parameter_arraylist.add(new String(errReasonId));
		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		Integer totalCount = jdbcTemplate.queryForObject(sql_count, Integer.class, parameter);
		return totalCount;
	}

	@Override
	public List<FailBatchVo> GetErrReasonByTopicAndErrScale(String topic, String errScale) {
		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select ERR_REASON_ID as errReasonId, " + "ERR_REASON_DETAIL as errReason " + "from ERROR_DEFINE ";

		sql += "where SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		System.out.println("errScale = " + errScale);

		// 查询sql加上对异常等级（errScale）的限定条件
		if (!errScale.equals(new String("0"))) {
			sql += "and ERR_SCALE = ?";
			parameter_arraylist.add(errScale);
		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<FailBatchVo>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(FailBatchVo.class), parameter);
	}

	@Override
	public List<BatchVO> GetProvinceMapping(String provCode) {
		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select PROV_BRANCH_NAME as provName, " + "PROV_BRANCH_CODE as provCode " + "from BMP_PROVINCE ";

		// 查询sql加上对异常原因ID（errReasonId）的限定条件
		if (Integer.parseInt(provCode) != 0) {
			sql += "where PROV_BRANCH_CODE = ?";
			parameter_arraylist.add(new Integer(provCode));
		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);
		return (List<BatchVO>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(BatchVO.class), parameter);
	}

	public Map<String, Object> GetHistoryBatchList(String topic, String provCode, String batchId,
			String batchTypeDetail, String order, String length, String start, int execStat, String startExecTime,
			String endExecTime) {

		Map<String, Object> amap = new HashMap<String, Object>();

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "rownum as rn," + "bd.SYSTEM as topic, " + "bp.PROV_BRANCH_CODE as proCode, "
				+ "bp.PROV_BRANCH_NAME as proName, " + "bd.BATCH_ID as batchId, " + "bd.BATCH_NAME as batchName, "
				+ "bd.BATCH_TYPE_DETAIL as batchTypeDetail, " + "nvl(mq.WHERE_CLAUSE,'无') as whereClause, "
				+ "to_char(mq.START_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as startExecTime, "
				+ "mq.END_PROC_REC  as endProcRecord, " + "mq.ERR_PROC_REC as errProcRecord, "
				+ "mq.EXEC_PROC_REC as execProcRecord, " + "mq.OCLK_BRANCH_NO as oclkBranchNo, "
				+ "mq.OCLK_CLERK_NO as oclkClerkNo, " +

		"to_char(mq.END_EXEC_TIME,'yyyy-mm-dd hh24:mi:ss') as endExecTime, " + "mq.EXEC_STAT as execStat " +

		"from MONITOR_QUEUE_";

		// 指定业务系统
		sql += topic;

		sql = sql + " mq " + "join BATCH_DEF bd " + "on to_char(mq.BATCH_TX_NO) = bd.BATCH_ID " + "and bd.SYSTEM = ? ";
		parameter_arraylist.add(new String(topic));

		sql = sql + "join BMP_PROVINCE bp " + "on mq.PROV_BRANCH_CODE = bp.PROV_BRANCH_CODE ";

		// 查询sql加上对机构号（provCode）的限定条件
		if (!(provCode.equals(new String("0")))) {
			sql += "where mq.PROV_BRANCH_CODE= ? ";
			parameter_arraylist.add(new String(provCode));
		}

		// 查询sql加上对批作业id（batchId）的限定条件
		if (!(batchId.equals(new String("0")))) {
			sql += "and to_char(mq.BATCH_TX_NO) = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 查询sql加上对作业类型（batchType）的限定条件
		if (!batchTypeDetail.equals("0")) {
			sql += "and bd.BATCH_TYPE_DETAIL = ? ";
			parameter_arraylist.add(batchTypeDetail);
		}

		// 查询sql加上对（execStat）的限定条件
		if (execStat != -1) {
			sql += "and mq.EXEC_STAT = ? ";
			parameter_arraylist.add(new Integer(execStat));
		}

		// 查询sql加上对（startExecTime）的限定条件
		sql += "and mq.START_EXEC_TIME > to_date(?, 'yyyy-mm-dd hh24:mi:ss')";
		parameter_arraylist.add(new String(startExecTime));

		sql += "and mq.START_EXEC_TIME < to_date(?, 'yyyy-mm-dd hh24:mi:ss')";
		parameter_arraylist.add(new String(endExecTime));

		// 查询sql加上对排序（order）的后缀
		if (!(order.equals(new String("0")))) {
			sql += "order by " + order;
		}

		String sql_count = "select count(*) from (" + sql + ")";

		String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(length));
		sql = "select * from (" + sql + ") where rn > " + start + "and rn <= " + end;

		amap.put("start", start);
		amap.put("length", length);

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		// 计算总记录条数
		Integer totalCount = jdbcTemplate.queryForObject(sql_count, Integer.class, parameter);
		amap.put("recordtotal", totalCount);

		List<RunningBatchVo> list = jdbcTemplate.query(sql, DaoUtils.createRowMapper(RunningBatchVo.class), parameter);
		amap.put("data", list);
		return amap;
	}

	// 事务，插入批作业信息
	// @Override
	// public boolean insertBatch(final QueryBatch batch) {
	//
	// final String sql = "insert into BATCH_DEF(ID, BATCH_ID, BATCH_NAME,
	// BATCH_TYPE_DETAIL,SCHEDULE_FREQUENCY,IT_FRAMEWORK,LOOP_FLAG,OPERATION_STAFF,OPERATION_MODE,STATE,RULE_START_EXEC_TIME,RULE_END_EXEC_TIME,SYSTEM)
	// values(BATCH_DEF_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?)";
	//
	// jdbcTemplate.update(sql,
	// batch.getBatchId(),batch.getBatchName(),batch.getBatchTypeDetail(),batch.getScheduleFrequency(),batch.getItFramework(),
	// batch.getLoopFlag(),batch.getOperationStaff(),batch.getOperationMode(),batch.getState(),batch.getRuleStartExecTime(),batch.getRuleEndExecTime(),batch.getTopic());
	//
	// return true;
	// }

	@Override
	public boolean insertBatchDef(final QueryBatch batch) {

		final String sql = "insert into BATCH_DEF(ID, BATCH_ID, BATCH_NAME, BATCH_TYPE_DETAIL,SCHEDULE_FREQUENCY,IT_FRAMEWORK,LOOP_FLAG,OPERATION_STAFF,OPERATION_MODE,STATE,RULE_START_EXEC_TIME,RULE_END_EXEC_TIME,SYSTEM) values(BATCH_DEF_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?)";

		jdbcTemplate.update(sql, batch.getBatchId(), batch.getBatchName(), batch.getBatchTypeDetail(),
				batch.getScheduleFrequency(), batch.getItFramework(), batch.getLoopFlag(), batch.getOperationStaff(),
				batch.getOperationMode(), batch.getState(), batch.getRuleStartExecTime(), batch.getRuleEndExecTime(),
				batch.getTopic());

		return true;
	}

	public String selectPerticularBatchId(final QueryBatch batch) {
		String sql = "select ID as id from BATCH_DEF where BATCH_ID = ? and SYSTEM = ?";
		QueryBatch result = jdbcTemplate
				.query(sql, DaoUtils.createRowMapper(QueryBatch.class), batch.getBatchId(), batch.getTopic()).get(0);
		return result.getId();
	}

	@Override
	public boolean insertBatchCommandDef(final QueryBatch batch) {

		final String sql = "insert into BATCH_COMMAND_DEF(ID, START_COMMAND,START_PARAMETERS,HALT_COMMAND,HALT_PARAMETERS) values(?,?,?,?,?)";

		jdbcTemplate.update(sql, batch.getId(), batch.getStartCommand(), batch.getStartParameters(),
				batch.getHaltCommand(), batch.getHaltParameters());
		return true;
	}

	// 事务，更新批作业信息
	// @Override
	// public boolean updateBatch(final QueryBatch batch) {
	//
	//
	// final String sql = "update BATCH_DEF set BATCH_ID = ?, BATCH_NAME
	// =?,BATCH_TYPE_DETAIL=?,SCHEDULE_FREQUENCY=?,
	// IT_FRAMEWORK=?,LOOP_FLAG=?,OPERATION_STAFF=?,
	// OPERATION_MODE=?,STATE=?,RULE_START_EXEC_TIME=?,RULE_END_EXEC_TIME=?,
	// SYSTEM=?"
	// + "where ID = ?";
	//
	// jdbcTemplate.update(sql,batch.getBatchId(),batch.getBatchName(),batch.getBatchTypeDetail(),batch.getScheduleFrequency(),batch.getItFramework(),
	// batch.getLoopFlag(),batch.getOperationStaff(),batch.getOperationMode(),batch.getState(),batch.getRuleStartExecTime(),batch.getRuleEndExecTime(),batch.getTopic(),
	// batch.getId());
	//
	// return true;
	// }

	@Override
	public int getBatchCommandCount(final QueryBatch batch) {

		final String sql = "select count(*) from  BATCH_COMMAND_DEF where ID = ?";

		return jdbcTemplate.queryForObject(sql, Integer.class, batch.getId());
	}

	@Override
	public boolean updateBatchDef(final QueryBatch batch) {

		final String sql = "update BATCH_DEF set BATCH_ID = ?, BATCH_NAME =?,BATCH_TYPE_DETAIL=?,SCHEDULE_FREQUENCY=?, IT_FRAMEWORK=?,LOOP_FLAG=?,OPERATION_STAFF=?, OPERATION_MODE=?,STATE=?,RULE_START_EXEC_TIME=?,RULE_END_EXEC_TIME=?, SYSTEM=?"
				+ "where ID = ?";

		jdbcTemplate.update(sql, batch.getBatchId(), batch.getBatchName(), batch.getBatchTypeDetail(),
				batch.getScheduleFrequency(), batch.getItFramework(), batch.getLoopFlag(), batch.getOperationStaff(),
				batch.getOperationMode(), batch.getState(), batch.getRuleStartExecTime(), batch.getRuleEndExecTime(),
				batch.getTopic(), batch.getId());

		return true;
	}

	@Override
	public boolean updateBatchCommandDef(final QueryBatch batch) {
		final String sql = "update BATCH_COMMAND_DEF set START_COMMAND = ?, START_PARAMETERS =?,HALT_COMMAND=?,HALT_PARAMETERS=?"
				+ "where ID = ?";

		jdbcTemplate.update(sql, batch.getStartCommand(), batch.getStartParameters(), batch.getHaltCommand(),
				batch.getHaltParameters(), batch.getId());

		return true;
	}

	// 事务，同时修改2个表
	// @Override
	// public boolean deleteBatch(String id) {
	//
	// String sql = "delete from BATCH_DEF where id=?";
	//
	// jdbcTemplate.update(sql, id);
	//
	// return true;
	// }

	@Override
	public boolean deleteBatchDef(String id) {

		String sql = "delete from BATCH_DEF where id=?";

		jdbcTemplate.update(sql, id);

		return true;
	}

	@Override
	public boolean deleteBatchCommandDef(String id) {

		String sql = "delete from BATCH_COMMAND_DEF where id=?";

		jdbcTemplate.update(sql, id);

		return true;
	}

	public Map<String, Object> selectBatch(String topic, String batchTypeDetail, String batchId, String order,
			String length, String start) {

		Map<String, Object> amap = new HashMap<String, Object>();

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "rownum as rn, " + "bd.ID as id, " + "bd.BATCH_ID as batchId, "
				+ "bd.BATCH_NAME as batchName, " + "bd.BATCH_TYPE_DETAIL as batchTypeDetail, "
				+ "bd.SCHEDULE_FREQUENCY as scheduleFrequency , " + "bd.IT_FRAMEWORK as itFramework, "
				+ "bd.LOOP_FLAG as LoopFlag, " + "bd.OPERATION_STAFF as operationStaff, "
				+ "bd.OPERATION_MODE as operationMode, " + "bd.STATE as state, "
				+ "bd.RULE_START_EXEC_TIME as ruleStartExecTime, " + "bd.RULE_END_EXEC_TIME as ruleEndExecTime, "
				+ "bd.SYSTEM as topic, " + "bcd.START_COMMAND as startCommand, "
				+ "bcd.START_PARAMETERS as startParameters, " + "bcd.HALT_COMMAND as haltCommand, "
				+ "bcd.HALT_PARAMETERS as haltParameters " + "from BATCH_DEF bd " + "left join BATCH_COMMAND_DEF bcd "
				+ "on bd.id = bcd.id ";

		sql += "where bd.SYSTEM = ? ";
		parameter_arraylist.add(topic);

		if (!(batchTypeDetail.equals("0"))) {
			sql += "and bd.BATCH_TYPE_DETAIL= ? ";
			parameter_arraylist.add(new String(batchTypeDetail));
		}

		if (!(batchId.equals(new String("0")))) {
			sql += "and bd.BATCH_ID= ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 查询sql加上对排序（order）的后缀
		if (!(order.equals(new String("0")))) {
			sql += "order by " + order;
		}

		String sql_count = "select count(*) from (" + sql + ")";

		String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(length));
		sql = "select * from (" + sql + ") where rn > " + start + "and rn <= " + end;

		amap.put("start", start);
		amap.put("length", length);

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		// 计算总记录条数
		Integer totalCount = jdbcTemplate.queryForObject(sql_count, Integer.class, parameter);
		amap.put("recordtotal", totalCount);

		List<QueryBatch> list = jdbcTemplate.query(sql, DaoUtils.createRowMapper(QueryBatch.class), parameter);
		amap.put("data", list);
		return amap;
	}

	@Override
	public List<ZongKongBatch> getBatches(String topic, String batchTypeDetail, String order) {

		// 构造sql
		String sql = "select BATCH_ID as batchId,  BATCH_NAME as batchName from BATCH_DEF where SYSTEM = ? and BATCH_TYPE_DETAIL = ? and translate(BATCH_ID, '\\1234567890 ', '\\') is null order by to_number(BATCH_ID) asc";

		return (List<ZongKongBatch>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(ZongKongBatch.class), topic,
				batchTypeDetail);

	}

	@Override
	public int getRunningCount(String topic, String provCode, String batchId) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select count(*) from monitor_queue_" + topic + " where exec_stat = '1' ";

		if (!(provCode.equals(new String("0")))) {
			sql += "and prov_branch_code = ? ";
			parameter_arraylist.add(new String(provCode));
		}

		if (!(batchId.equals(new String("0")))) {
			sql += "and batch_tx_no = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, Integer.class, parameter);
	}

	@Override
	public int getTodayStoppedCount(String topic, String provCode, String batchId) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select count(*) from monitor_queue_" + topic
				+ " where exec_stat <> '1' and START_EXEC_TIME > trunc(SYSDATE) ";

		if (!(provCode.equals(new String("0")))) {
			sql += "and prov_branch_code = ? ";
			parameter_arraylist.add(new String(provCode));
		}

		if (!(batchId.equals(new String("0")))) {
			sql += "and batch_tx_no = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, Integer.class, parameter);
	}

	public int getErrorCount(String topic, String provCode, String batchId) {

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select count(*) from error_trail where ERR_ELIMITATE_FLAG = 0 ";

		if (!(topic.equals(new String("0")))) {
			sql += "and system = ? ";
			parameter_arraylist.add(new String(topic));
		}

		if (!(provCode.equals(new String("0")))) {
			sql += "and prov_branch_code = ? ";
			parameter_arraylist.add(new String(provCode));
		}

		if (!(batchId.equals(new String("0")))) {
			sql += "and batch_tx_no = ? ";
			parameter_arraylist.add(new String(batchId));
		}

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		return jdbcTemplate.queryForObject(sql, Integer.class, parameter);
	}

	public Map<String, Object> GetServerInfo(String topic, String provCode) {

		Map<String, Object> amap = new HashMap<String, Object>();

		// 构造sql
		String sql = "select " + "SYSTEM as system, " + "PROV_BRANCH_CODE as provBranchCode, "
				+ "SERVER_NAME as serverName, " + "IP as ip, " + "USER_NAME as userName " + "from SERVER_INFO "
				+ "where SYSTEM = ? " + "and PROV_BRANCH_CODE= ? ";

		List<ServerInfo> list = jdbcTemplate.query(sql, DaoUtils.createRowMapper(ServerInfo.class), topic, provCode);
		amap.put("data", list);
		return amap;
	}

	public List<QueryBatch> GetBatchCommand(String topic, String batchId) {

		// 构造sql
		String sql = "select " + "COMMAND as command " + "from BATCH_DEF " + "where SYSTEM = ? " + "and BATCH_ID= ? ";

		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(QueryBatch.class), topic, batchId);
	}

	public Map<String, Object> selectServer(String system, String provCode, String order, String length, String start) {

		Map<String, Object> amap = new HashMap<String, Object>();

		// 初始化传参Arraylist
		List<Object> parameter_arraylist = new ArrayList<Object>();

		// 构造sql
		String sql = "select " + "rownum as rn, " + "si.ID as id, " + "sd.ENGLISH_NAME as systemEnglishName, "
				+ "si.PROV_BRANCH_CODE as provCode, " + "si.SERVER_NAME as serverName, " + "si.IP as ip, "
				+ "si.PORT as port, " + "si.USER_NAME as userName, " + "si.CHARSET as charset, "
				+ "si.SYSTEM as system " + "from SERVER_INFO si " + "join SYSTEM_DEFINE sd "
				+ "on si.SYSTEM = sd.SYSTEM ";

		sql += "where si.SYSTEM = ? ";
		parameter_arraylist.add(system);

		if (!(provCode.equals(new String("0")))) {
			sql += "and si.PROV_BRANCH_CODE= ? ";
			parameter_arraylist.add(new String(provCode));
		}

		// 查询sql加上对排序（order）的后缀
		if (!(order.equals(new String("0")))) {
			sql += "order by " + order;
		}

		String sql_count = "select count(*) from (" + sql + ")";

		String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(length));
		sql = "select * from (" + sql + ") where rn > " + start + "and rn <= " + end;

		amap.put("start", start);
		amap.put("length", length);

		// 传入后台数据库的参数
		int size = parameter_arraylist.size();
		Object[] parameter = (Object[]) parameter_arraylist.toArray(new Object[size]);

		// 计算总记录条数
		Integer totalCount = jdbcTemplate.queryForObject(sql_count, Integer.class, parameter);
		amap.put("recordtotal", totalCount);

		List<QueryServer> list = jdbcTemplate.query(sql, DaoUtils.createRowMapper(QueryServer.class), parameter);
		amap.put("data", list);
		return amap;
	}

	@Override
	public boolean updateServer(final QueryServer qc) {

		final String sql = "update SERVER_INFO set SYSTEM =?,PROV_BRANCH_CODE =?,SERVER_NAME=?,IP=?,PORT=?,USER_NAME=?,CHARSET=? "
				+ "where id=? ";

		jdbcTemplate.update(sql, qc.getSystem(), qc.getProvCode(), qc.getServerName(), qc.getIp(), qc.getPort(),
				qc.getUserName(), qc.getCharset(), qc.getId());

		return true;
	}

	@Override
	public boolean insertServer(final QueryServer qc) {

		final String sql = "insert into SERVER_INFO(ID, SYSTEM, PROV_BRANCH_CODE, SERVER_NAME, IP, PORT, USER_NAME, CHARSET) values(SERVER_INFO_SEQ.NEXTVAL,?,?,?,?,?,?,?)";

		jdbcTemplate.update(sql, qc.getSystem(), qc.getProvCode(), qc.getServerName(), qc.getIp(), qc.getPort(),
				qc.getUserName(), qc.getCharset());

		return true;
	}

	@Override
	public boolean deleteServer(String id) {

		String sql = "delete from SERVER_INFO where id=?";

		jdbcTemplate.update(sql, id);

		return true;
	}

	public QueryBatch getBatchServerInfo(String topic, String provCode, String batchId) {

		// 构造sql
		String sql = "select bd.DESCRIPTION as description, bd.COMMAND as command, bd.PARAMETERS as parameters,wm_concat(si.IP) as ips, wm_concat(si.USER_NAME) as usernames "
				+ "from BATCH_DEF bd " + "join SERVER_INFO si " + "on bd.SYSTEM = si.SYSTEM "
				+ "where bd.SYSTEM = ? and bd.BATCH_ID = ? and si.PROV_BRANCH_CODE = ? "
				+ "group by bd.DESCRIPTION,bd.COMMAND,bd.PARAMETERS ";

		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(QueryBatch.class), topic, batchId, provCode).get(0);

	}

	public QueryBatch getBatchTaskIds(String topic, String provCode, String batchId) {

		// 构造sql
		String sql = "select wm_concat(TASK_ID) as taskIds, " + "wm_concat(nvl(WHERE_CLAUSE,'null')) as whereClause, "
				+ "wm_concat(to_char(START_EXEC_TIME, 'yyyy-mm-dd hh24:mi:ss')) as startExecTime "
				+ "from MONITOR_QUEUE_" + topic + " where to_char(BATCH_TX_NO) = ? " + "and PROV_BRANCH_CODE = ? "
				+ "and EXEC_STAT = '1' " + " group by BATCH_TX_NO ";

		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(QueryBatch.class), batchId, provCode).get(0);

	}

	public String getPortByServerInfo(String topic, String provCode, String ip) {

		// 构造sql
		String sql = "select PORT  as port from SERVER_INFO where SYSTEM = ? and PROV_BRANCH_CODE = ? and IP = ? ";
		return jdbcTemplate.queryForObject(sql, String.class, topic, provCode, ip);

	}

	public String getUserNameByServerInfo(String topic, String provCode, String ip) {

		// 构造sql
		String sql = "select USER_NAME  as userName from SERVER_INFO where SYSTEM = ? and PROV_BRANCH_CODE = ? and IP = ? ";
		return jdbcTemplate.queryForObject(sql, String.class, topic, provCode, ip);

	}

	public String getServerIpByQueryInfo(String topic, String provCode) {

		// 构造sql
		String sql = "select IP  as ip from SERVER_INFO where SYSTEM = ? and PROV_BRANCH_CODE = ? and PRIMARY_FLAG is null ";
		return jdbcTemplate.queryForObject(sql, String.class, topic, provCode);

	}

	public DatabaseConnection getDatabaseInfo(String topic, String provCode) {

		// 构造sql
		String sql = "select ID," + "SYSTEM," + "PROV_BRANCH_CODE," + "DATABASE_IP," + "DATABASE_PORT,"
				+ "DATABASE_NAME," + "DATABASE_PASSWORD," + "INSTANCE_NAME"
				+ " from DATABASE_CONNECTION_DEF where SYSTEM = ? and PROV_BRANCH_CODE = ? ";
		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(DatabaseConnection.class), topic, provCode).get(0);
	}

	public SystemCommandDef getSystemCommandDef(String topic, String provCode) {

		// 构造sql
		String sql = "select scd.SYSTEM as system, " + "scd.IT_FRAMEWORK as itFramework, "
				+ "scd.START_COMMAND as startCommand, " + "scd.HALT_COMMAND as haltCommand "
				+ "from SYSTEM_COMMAND_DEF scd " + "left join batch_def bd " + "on scd.SYSTEM = bd.SYSTEM "
				+ "and scd.IT_FRAMEWORK = bd.IT_FRAMEWORK " + "and scd.SYSTEM = ? " + "and bd.batch_id = ? ";
		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(SystemCommandDef.class), topic, provCode).get(0);
	}

	public List<QueryBatch> getTaskIdList(String topic, String provCode, String batchId) {

		// 构造sql
		String sql = "select to_char(TASK_ID) as taskId " + " from MONITOR_QUEUE_" + topic
				+ " where to_char(BATCH_TX_NO) = ? " + " and PROV_BRANCH_CODE = ? " + " and EXEC_STAT = '1' ";
		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(QueryBatch.class), batchId, provCode);
	}

	public String getPerticularCommandLine(String batchId) {

		// 构造sql
		String sql = "select START_COMMAND as startCommand " + " from BATCH_COMMAND_DEF bcd "
				+ " left join BATCH_DEF bd " + " on bcd.ID = bd.ID " + " and bd.BATCH_ID = ? "
				+ " and bcd.ALL_START_FLAG = '1' ";
		return jdbcTemplate.query(sql, DaoUtils.createRowMapper(BatchCommandDef.class), batchId).get(0)
				.getStartCommand();
	}

	@Override
	public List<MapBatch> getProvinces() {

		// 构造sql
		String sql = "select PROV_BRANCH_NAME as proName, " + "PROV_BRANCH_CODE as provCode " + "from BMP_PROVINCE ";

		return (List<MapBatch>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(MapBatch.class));
	}

	@Override
	public int getErrorCountOfOneProvince(String topic, String provCode) {

		String sql = "select count(unique batch_tx_no) " + "from ERROR_TRAIL " + "where SYSTEM=? "
				+ "and PROV_BRANCH_CODE = ?" + "and ERR_ELIMITATE_FLAG = 0";

		return jdbcTemplate.queryForObject(sql, Integer.class, topic, provCode);
	}

	@Override
	public int getBatchCountBaseOnSystemAndType(String topic, String batchTypeDetail) {
		String sql = "select count(*) from BATCH_DEF where SYSTEM = ? and BATCH_TYPE_DETAIL = ?";

		return jdbcTemplate.queryForObject(sql, Integer.class, topic, batchTypeDetail);
	}


	
	@Override
	public int getRunningCountBaseOnSystemAndType(String topic, String provCode, String batchTypeDetail) {
		String sql = "select count(unique mq.BATCH_TX_NO) "
				+ "from MONITOR_QUEUE_" + topic + " mq "
				+ "join BATCH_DEF bd "
				+ "on to_char(mq.BATCH_TX_NO) = bd.BATCH_ID "
				+ "and bd.SYSTEM = ? "
				+ "where mq.PROV_BRANCH_CODE = ? "
				+ "and bd.BATCH_TYPE_DETAIL = ?　"
				+ "and mq.EXEC_STAT = '1'"; 

		return jdbcTemplate.queryForObject(sql, Integer.class, topic, provCode, batchTypeDetail);
	}

	@Override
	public int getRunnedCountBaseOnSystemAndType(String topic, String provCode, String batchTypeDetail) {
		String sql = "select count(unique mq.BATCH_TX_NO) "
				+ "from MONITOR_QUEUE_" + topic + " mq "
				+ "join BATCH_DEF bd "
				+ "on to_char(mq.BATCH_TX_NO) = bd.BATCH_ID "
				+ "and bd.SYSTEM = ? "
				+ "where mq.PROV_BRANCH_CODE = ? "
				+ "and bd.BATCH_TYPE_DETAIL = ?　"
				+ "and mq.EXEC_STAT <> '1'"; 

		return jdbcTemplate.queryForObject(sql, Integer.class, topic, provCode, batchTypeDetail);
	}

	@Override
	public int getChuDanWarningCount(String topic, String batchId) {
		String sql = "select count(*) from  ERROR_TRAIL et " + 
				"join ERROR_DEFINE ed on et.ERR_REASON_ID = ed.ERR_REASON_ID " + 
				"where et.SYSTEM = ? and et.BATCH_TX_NO = ? " + 
				"and et.ERR_ELIMITATE_FLAG = 0 " +
				"and (ed.ERR_REASON_DETAIL = '批作业当日未重新启动' or ed.ERR_REASON_DETAIL = '批作业启动后没有处理成功的记录' or ed.ERR_REASON_DETAIL = '批作业跨日运行未终止' or ed.ERR_REASON_DETAIL = '批作业启动后没有处理任何数据' or ed.ERR_REASON_DETAIL = '批作业当日未重新启动' )";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, topic, batchId);

	}

	@Override
	public String getChuDanWarningInfo(String topic, String batchId) {
		String sql = "select wm_concat(unique ed.ERR_REASON_DETAIL) from  ERROR_TRAIL et " + 
				"join ERROR_DEFINE ed on et.ERR_REASON_ID = ed.ERR_REASON_ID " + 
				"where et.SYSTEM = ? and et.BATCH_TX_NO = ? " + 
				"and et.ERR_ELIMITATE_FLAG = 0 " +
				"and (ed.ERR_REASON_DETAIL = '批作业当日未重新启动' or ed.ERR_REASON_DETAIL = '批作业启动后没有处理成功的记录' or ed.ERR_REASON_DETAIL = '批作业跨日运行未终止' or ed.ERR_REASON_DETAIL = '批作业启动后没有处理任何数据' or ed.ERR_REASON_DETAIL = '批作业当日未重新启动' ) "; 
				
		return jdbcTemplate.queryForObject(sql, String.class, topic, batchId);
	}
	
	@Override
	public int getChuDanErrorCount(String topic, String batchId) {
		String sql = "select count(*) from  ERROR_TRAIL et " + 
				"join ERROR_DEFINE ed on et.ERR_REASON_ID = ed.ERR_REASON_ID " + 
				"where et.SYSTEM = ? and et.BATCH_TX_NO = ? " + 
				"and et.ERR_ELIMITATE_FLAG = 0 " +
				"and (ed.ERR_REASON_DETAIL = '日间批作业未运行')";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, topic, batchId);

	}
	
	@Override
	public String getChuDanErrorInfo(String topic, String batchId) {
		String sql = "select wm_concat(unique ed.ERR_REASON_DETAIL) from  ERROR_TRAIL et " + 
				"join ERROR_DEFINE ed on et.ERR_REASON_ID = ed.ERR_REASON_ID " + 
				"where et.SYSTEM = ? and et.BATCH_TX_NO = ? " + 
				"and et.ERR_ELIMITATE_FLAG = 0 " +
				"and (ed.ERR_REASON_DETAIL = '日间批作业未运行') "; 
				
		return jdbcTemplate.queryForObject(sql, String.class, topic, batchId);
	}

	@Override
	public List<MapBatch> getBatchesBaseOnTopicAndBatchTypeDetail(String topic, String batchTypeDetail) {
		
		//构造sql
		String sql = "select system as topic, BATCH_TYPE_DETAIL as BATCH_TYPE_DETAIL, BATCH_ID as batchId, BATCH_NAME as batchName  "
				+ "from BATCH_DEF "
				+ " where SYSTEM = ? and BATCH_TYPE_DETAIL = ?";
		return (List<MapBatch>) jdbcTemplate.query(sql, DaoUtils.createRowMapper(MapBatch.class), topic,batchTypeDetail);
	}

	@Override
	public int getRunningProvinceCount(String topic, String batchId) {
		
		String sql = "select count(unique PROV_BRANCH_CODE) "
				+ "from MONITOR_QUEUE_" + topic + " where to_char(BATCH_TX_NO) = ? and EXEC_STAT = '1'";
		
		return jdbcTemplate.queryForObject(sql, Integer.class,  batchId);

	}

	@Override
	public int getRunnedProvinceCount(String topic, String batchId) {
		String sql = "select count(unique PROV_BRANCH_CODE) "
				+ "from MONITOR_QUEUE_" + topic + " where to_char(BATCH_TX_NO) = ? and START_EXEC_TIME >trunc(SYSDATE) and EXEC_STAT <>1";
		
		return jdbcTemplate.queryForObject(sql, Integer.class,  batchId);
	}
	
	public String getBatchTypeDetailOfBatchId(String topic, String batchId) {
		String sql = "select BATCH_TYPE_DETAIL as batchTypeDetail from BATCH_DEF where SYSTEM = ? and BATCH_ID = ? " ;
				
		return jdbcTemplate.queryForObject(sql, String.class, topic, batchId);
	}
	
	public int getRunningCountOfOneRiJianBatch(String topic, String proCode,  String batchId) {
		
		String sql = "select count(*) "
				+ "from MONITOR_QUEUE_" + topic + " where PROV_BRANCH_CODE = ? and  BATCH_TX_NO = ? and EXEC_STAT = '1'";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, proCode, batchId);

	}

	
	
	public int getRunnedCountOfOneRiZhongBatch(String topic, String proCode,  String batchId) {
		
		String sql = "select count(*) "
				+ "from MONITOR_QUEUE_" + topic + " where PROV_BRANCH_CODE = ? and  BATCH_TX_NO = ? and START_EXEC_TIME > trunc(SYSDATE) and EXEC_STAT <> '1'";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, proCode, batchId);

	}
}
