package com.newcore.bmp.web;

import java.util.List;

import com.halo.core.common.SpringContextHolder;
import com.halo.core.common.util.PropertiesUtils;
import com.newcore.bmp.models.authority.constants.Constants;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.authority.models.SessionModel;
import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.RunningBatchVo;
import com.newcore.bmp.service.api.authority.ClerkService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.halo.core.header.HeaderInfoHolder;
import com.halo.core.web.annotation.ResponseMessage;
import com.newcore.bmp.models.webvo.BatchVO;
import com.newcore.bmp.models.webvo.FailBatchVo;
import com.newcore.bmp.models.webvo.QueryBatch;
import com.newcore.bmp.models.webvo.QueryCond;
import com.newcore.bmp.models.webvo.QueryServer;
import com.newcore.bmp.service.api.TaskRunMonitor;
import com.newcore.supports.models.cxf.CxfHeader;
import com.newcore.supports.util.HeaderInfoUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import java.util.Map;

import com.newcore.bmp.models.authority.models.ClerkResource;

@Controller
@RequestMapping("/taskrunm")
public class TaskRunMonitorController {
	@Autowired
	TaskRunMonitor taskRunMonitor;
	
	@RequestMapping(value = "/getfailbatchlist" )
	public @ResponseMessage Map<String, Object> GetFailBatchList(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
    	HeaderInfoHolder.setOutboundHeader(header);
    	return  taskRunMonitor.GetFailBatchList(qc);
	}

	@RequestMapping(value = "/getrunningbatchlist" )
	public @ResponseMessage Map<String, Object> GetRunningBatchList(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetRunningBatchList(qc);
	}
 
	@RequestMapping(value = "/getpronamebyprocode" )
	public @ResponseMessage List<RunningBatchVo> GetProNameByProCode(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetProNameByProCode(qc);
	}
	
	@RequestMapping(value = "/getprocodebyproname" )
	public @ResponseMessage List<RunningBatchVo> GetProCodeByProName(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetProCodeByProName(qc);
	}

	
	@RequestMapping(value = "/getbatchnamebybatchid" )
	public @ResponseMessage List<RunningBatchVo> GetBatchNameByBatchId(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetBatchNameByBatchId(qc);
	}
	
	
	@RequestMapping(value = "/getbatchidbybatchname" )
	public @ResponseMessage List<RunningBatchVo> GetBatchIdByBatchName(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetBatchIdByBatchName(qc);
	}
	
	@RequestMapping(value = "/getbatchidandbatchnamebytopicandbatchtype" )
	public @ResponseMessage List<RunningBatchVo> GetBatchIdAndBatchNameByTopicAndBatchType(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetBatchIdAndBatchNameByTopicAndBatchType(qc);
	}
	
	@RequestMapping(value = "/geterrreasonbytopicanderrscale" )
	public @ResponseMessage List<FailBatchVo> GetErrReasonByTopicAndErrScale(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetErrReasonByTopicAndErrScale(qc);
	}
	
	@RequestMapping(value = "/getprovincemapping" )
	public @ResponseMessage List<BatchVO>  GetProvinceMapping(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetProvinceMapping(qc);
	}
	


	@RequestMapping(value = "/getusername", method = RequestMethod.GET)
	public @ResponseMessage ClerkResource getUserName() {
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);

		Subject currentSubject = SecurityUtils.getSubject();
		Session session = currentSubject.getSession(false);

		String clerkNo = (String)session.getAttribute(PropertiesUtils.getProperty(Constants.SESSION_CLERKNO_KEY));

		ClerkService clerkService = SpringContextHolder.getBean(ClerkService.class);
		Clerk clerk = clerkService.findOne(clerkNo);

		ClerkResource resource = new ClerkResource();
		resource.setClerkNo(clerk.getUserName());
		resource.setBranchNo(clerk.getBranchNo());
		resource.setName(clerk.getName());
		resource.setAcceptTaskFlag(clerk.getAcceptTaskFlag());

		return resource;
	}

	@RequestMapping(value = "/getname", method = RequestMethod.GET)
	public @ResponseMessage String getName() {
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);

		Subject currentSubject = SecurityUtils.getSubject();
		Session session = currentSubject.getSession(false);
		String clerkNo = (String)session.getAttribute(PropertiesUtils.getProperty(Constants.SESSION_CLERKNO_KEY));

		ClerkService clerkService = SpringContextHolder.getBean(ClerkService.class);
		Clerk clerk = clerkService.findOne(clerkNo);

		return clerk.getName();
	}
	
	@RequestMapping(value = "/gethistorybatchlist" )
	public @ResponseMessage Map<String, Object> GetHistoryBatchList(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.GetHistoryBatchList(qc);
	}
	
	@RequestMapping(value = "/getsessionstatus" )
	public @ResponseMessage boolean getSessionStatus(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return true;
	}
	
	@RequestMapping(value = "/insertbatch" )
	public @ResponseMessage boolean insertBatch(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.insertBatch(qc);
	}
	
	
	@RequestMapping(value = "/updatebatch" )
	public @ResponseMessage boolean updateBatch(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.updateBatch(qc);
	}
	
	@RequestMapping(value = "/deletebatch" )
	public @ResponseMessage boolean deleteBatch(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.deleteBatch(qc);
	}
	
	@RequestMapping(value = "/selectbatch" )
	public @ResponseMessage Map<String, Object> selectBatch(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.selectBatch(qc);
	}
	
	@RequestMapping(value = "/getzongkongbatches" )
	public @ResponseMessage Map<String, Object> getZongKongBatches(@RequestBody QueryCond qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getZongKongBatches(qc);
	}
	
	
	
	@RequestMapping(value = "/selectserver" )
	public @ResponseMessage Map<String, Object> selectServer(@RequestBody QueryServer qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.selectServer(qc);
	}
	

	
	
	@RequestMapping(value = "/updateserver" )
	public @ResponseMessage boolean updateServer(@RequestBody QueryServer qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.updateServer(qc);
	}
	
	@RequestMapping(value = "/insertserver" )
	public @ResponseMessage boolean insertServer(@RequestBody QueryServer qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.insertServer(qc);
	}
	
	@RequestMapping(value = "/deleteserver" )
	public @ResponseMessage boolean deleteServer(@RequestBody QueryServer qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.deleteServer(qc);
	}
	
	@RequestMapping(value = "/getbatchserverinfo" )
	public @ResponseMessage QueryBatch getBatchServerInfo(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getBatchServerInfo(qc);
	}
	
	@RequestMapping(value = "/getbatchtaskids" )
	public @ResponseMessage QueryBatch getBatchTaskIds(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getBatchTaskIds(qc);
	}
	
	@RequestMapping(value = "/runbatch" )
	public @ResponseMessage Map<String, Object> runBatch(@RequestBody QueryBatch qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.runBatch(qc);
	}
	
	@RequestMapping(value = "/runbatchthroughplatform" )
	public @ResponseMessage Map<String, Object> runBatchThroughPlatform(@RequestBody QueryBatch qc) throws Exception
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.runBatchThroughPlatform(qc);
	}
	
	@RequestMapping(value = "/startbatch" )
	public @ResponseMessage Map<String, Object> startBatch(@RequestBody QueryBatch qc) throws Exception
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.startBatch(qc);
	}
	
	@RequestMapping(value = "/stopbatch" )
	public @ResponseMessage Map<String, Object> stopBatch(@RequestBody QueryBatch qc) throws Exception
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.stopBatch(qc);
	}
	
	@RequestMapping(value = "/stopsomebatch" )
	public @ResponseMessage Map<String, Object> stopSomeBatch(@RequestBody QueryBatch qc) throws Exception
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.stopSomeBatch(qc);
	}
	
	@RequestMapping(value = "/stopbatchbaseontaskid" )
	public @ResponseMessage Map<String, Object> stopBatchBaseOnTaskId(@RequestBody QueryBatch qc) throws Exception
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.stopBatchBaseOnTaskId(qc, qc.getTaskId());
	}
	
	@RequestMapping(value = "/getmapbatches" )
	public @ResponseMessage Map<String, Object> getMapBatches(@RequestBody QueryCond qc) 	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getMapBatches(qc);
	}

	@RequestMapping(value = "/getprovincedimensioninfo" )
	public @ResponseMessage Map<String, Object> getProvinceBatches(@RequestBody QueryCond qc) 	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getProvinceDimensionInfo(qc);
	}
	
	@RequestMapping(value = "/getchudandimensioninfo" )
	public @ResponseMessage Map<String, Object> getChuDanDimensionInfo(@RequestBody QueryCond qc) 	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getChuDanDimensionInfo(qc);
	}
	
	
	
	@RequestMapping(value = "/getbatchdimensioninfo" )
	public @ResponseMessage Map<String, Object> getBatchDimensionInfo(@RequestBody QueryCond qc) 	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getBatchDimensionInfo(qc);
	}
	
	@RequestMapping(value = "/getonebatchstatusofoneprovince" )
	public @ResponseMessage Map<String, Object> getOneBatchStatusOfOneProvince(@RequestBody QueryCond qc) 	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		return  taskRunMonitor.getOneBatchStatusOfOneProvince(qc);
	}
}
