package com.newcore.bmp.service.api;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.BatchVO;
import com.newcore.bmp.models.webvo.FailBatchVo;
import com.newcore.bmp.models.webvo.QueryBatch;
import com.newcore.bmp.models.webvo.QueryCond;
import com.newcore.bmp.models.webvo.QueryServer;
import com.newcore.bmp.models.webvo.RunningBatchVo;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TaskRunMonitor {
	@POST
	@Path("/GetFailBatchList")
	Map<String, Object> GetFailBatchList(QueryCond qc);

	@POST
	@Path("/GetRunningBatchList")
	Map<String, Object> GetRunningBatchList(QueryCond qc);
	
	@POST
	@Path("/GetProNameByProCode")
	List<RunningBatchVo> GetProNameByProCode(QueryCond qc);

	@POST
	@Path("/GetProCodeByProName")
	List<RunningBatchVo> GetProCodeByProName(QueryCond qc);
	
	@POST
	@Path("/GetBatchNameByBatchId")
	List<RunningBatchVo> GetBatchNameByBatchId(QueryCond qc);
	

	
	@POST
	@Path("/GetBatchIdByBatchName")
	List<RunningBatchVo> GetBatchIdByBatchName(QueryCond qc);
	
	@POST
	@Path("/GetBatchIdAndBatchNameByTopicAndBatchType")
	List<RunningBatchVo> GetBatchIdAndBatchNameByTopicAndBatchType(QueryCond qc);
	
	@POST
	@Path("/GetErrReasonByTopicAndErrScale")
	List<FailBatchVo> GetErrReasonByTopicAndErrScale(QueryCond qc);
	
	@POST
	@Path("/GetProvinceMapping")
	List<BatchVO> GetProvinceMapping(QueryCond qc);

	@POST
	@Path("/GetHistoryBatchList")
	Map<String, Object> GetHistoryBatchList(QueryCond qc);
	
	@POST
	@Path("/insertBatch")
	boolean insertBatch(QueryBatch qc);

	@POST
	@Path("/updateBatch")
    boolean updateBatch(final QueryBatch batch);

	@POST
	@Path("/deleteBatch")
	boolean deleteBatch(QueryBatch qc);
	
	@POST
	@Path("/selectBatch")
	Map<String, Object> selectBatch(QueryBatch qc);
	
	
	@POST
	@Path("/getZongKongBatches")
	public Map<String, Object> getZongKongBatches(QueryCond qc);
	
	@POST
	@Path("/runBatch")
	public Map<String, Object> runBatch(QueryBatch qc) throws Exception;
	
	@POST
	@Path("/runBatchThroughPlatform")
	public Map<String, Object> runBatchThroughPlatform(QueryBatch qc) throws Exception;
	
	@POST
	@Path("/stopBatch")
	public Map<String, Object> stopBatch(QueryBatch qc) throws Exception;
	
	@POST
	@Path("/stopSomeBatch")
	public Map<String, Object> stopSomeBatch(QueryBatch qc) throws Exception;
	@POST
	@Path("/stopBatchBaseOnTaskId")
	public Map<String, Object> stopBatchBaseOnTaskId(QueryBatch qc, String taskId) throws Exception ;
	
	@POST
	@Path("/startBatch")
	public Map<String, Object> startBatch(QueryBatch qc) throws Exception;
	
	@POST
	@Path("/selectServer")
	public Map<String, Object> selectServer(QueryServer qc);
	
	@POST
	@Path("/updateServer")
	public boolean updateServer(QueryServer qc);

	@POST
	@Path("/insertServer")
	public boolean insertServer(QueryServer qc);
	
	@POST
	@Path("/deleteServer")
	public boolean deleteServer(QueryServer qc);
	
	@POST
	@Path("/getBatchServerInfo")
	public QueryBatch getBatchServerInfo(QueryBatch qc);
	
	@POST
	@Path("/getBatchTaskIds")
	public QueryBatch getBatchTaskIds(QueryBatch qc);
}
