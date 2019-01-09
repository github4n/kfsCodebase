package com.newcore.bmp.dao.api;

import java.util.List;
import java.util.Map;

import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.BatchVO;
import com.newcore.bmp.models.webvo.DatabaseConnection;
import com.newcore.bmp.models.webvo.FailBatchVo;
import com.newcore.bmp.models.webvo.MapBatch;
import com.newcore.bmp.models.webvo.QueryBatch;
import com.newcore.bmp.models.webvo.QueryServer;
import com.newcore.bmp.models.webvo.RunningBatchVo;
import com.newcore.bmp.models.webvo.SystemCommandDef;
import com.newcore.bmp.models.webvo.ZongKongBatch;

public interface TaskRunMonitorDao {
	Map<String, Object> GetFailBatchList(String topic, String provCode, String batchId, String batchTypeDetail, String errScale,
			String errReasonId, String order, String length, String start);

	// 作业运行监控
	// 1.1.2.1 根据所有过滤条件，查询结果
	Map<String, Object> GetRunningBatchList(String topic, String provCode, String batchId, String batchTypeDetail, String order,
			String length, String start);

	// 1.1.2.2 根据 “机构号”条件，得到“机构名称”条件
	List<RunningBatchVo> GetProNameByProCode(String provCode);

	// 1.1.2.3 case3 根据 “机构名称”条件，得到“机构号”条件
	List<RunningBatchVo> GetProCodeByProName(String provName);

	// 1.1.2.4 case4 根据 “作业id”条件，得到“作业名称”条件
	List<RunningBatchVo> GetBatchNameByBatchId(String topic, String batchId);

	// 1.1.2.5 case5 根据 “作业名称”条件，得到“作业id”条件
	List<RunningBatchVo> GetBatchIdByBatchName(String topic, String batchName);

	// 1.1.2.6 case6 根据“业务系统分类”，得到“作业id”,”作业名称”列表
//	List<RunningBatchVo> GetBatchIdAndBatchNameByTopicAndBatchType(String topic, int batchType);
	List<RunningBatchVo> GetBatchIdAndBatchNameByTopicAndBatchType(String topic, String batchTypeDetail);


	List<FailBatchVo> GetErrReasonByTopicAndErrScale(String topic, String errScale);

	List<BatchVO> GetProvinceMapping(String provCode);

	Map<String, Object> GetHistoryBatchList(String topic, String provCode, String batchId, String batchTypeDetail, String order,
			String length, String start, int execStat, String startExecTime, String endExecTime);

//	boolean insertBatch(QueryBatch batch);

//    public boolean deleteBatch(String id);
    
//	boolean updateBatch(QueryBatch batch);

	public Map<String, Object> selectBatch(String topic, String batchTypeDetail, String batchId, String order, String length,
			String start);

	List<ZongKongBatch> getBatches(String topic, String batchTypeDetail, String order);

	int getRunningCount(String topic, String provCode, String batchId);

	int getErrorCount(String topic, String provCode, String batchId);

	int getTodayStoppedCount(String topic, String provCode, String batchId);


	public Map<String, Object> GetServerInfo(String topic, String provCode);

	public List<QueryBatch> GetBatchCommand(String topic, String batchId);

	public Map<String, Object> selectServer(String system, String provCode, String order, String length, String start);

	public boolean updateServer(final QueryServer qc);

	public boolean insertServer(final QueryServer qc);

	public boolean deleteServer(String id);

	
	public QueryBatch getBatchServerInfo(String topic, String provCode, String batchId);
	
	public QueryBatch getBatchTaskIds(String topic, String provCode, String batchId);
	
	public String getPortByServerInfo(String topic, String provCode, String ip);
	public String getUserNameByServerInfo(String topic, String provCode, String ip);

	boolean updateBatchDef(QueryBatch batch);

	boolean updateBatchCommandDef(QueryBatch batch);

	int getBatchCommandCount(QueryBatch batch);

	boolean insertBatchDef(QueryBatch batch);

	boolean insertBatchCommandDef(QueryBatch batch);

	String selectPerticularBatchId(QueryBatch batch);

	boolean deleteBatchCommandDef(String id);

	boolean deleteBatchDef(String id);
	
	public String getServerIpByQueryInfo(String topic, String provCode);
	
	public DatabaseConnection getDatabaseInfo(String topic, String provCode);
	
	public SystemCommandDef getSystemCommandDef(String topic, String provCode);
	
	public List<QueryBatch> getTaskIdList(String topic, String provCode, String batchId);
	
	public String getPerticularCommandLine(String batchId);


	List<MapBatch> getProvinces();

	int getErrorCountOfOneProvince(String topic, String provCode);


	int getBatchCountBaseOnSystemAndType(String topic, String batchTypeDetail);


	int getRunningCountBaseOnSystemAndType(String topic, String provCode, String batchTypeDetail);

	int getRunnedCountBaseOnSystemAndType(String topic, String provCode, String batchTypeDetail);

	int getChuDanWarningCount(String topic, String batchId);

	String getChuDanWarningInfo(String topic, String batchId);

	int getChuDanErrorCount(String topic, String batchId);

	String getChuDanErrorInfo(String topic, String batchId);

	List<MapBatch> getBatchesBaseOnTopicAndBatchTypeDetail(String topic, String batchTypeDetail);

	int getRunningProvinceCount(String topic, String batchId);

	int getRunnedProvinceCount(String topic, String batchId);

	String getBatchTypeDetailOfBatchId(String topic, String batchId);
	
	public int getRunningCountOfOneRiJianBatch(String topic, String proCode,  String batchId);
	
	public int getRunnedCountOfOneRiZhongBatch(String topic, String proCode,  String batchId);

}
