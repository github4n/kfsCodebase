package com.newcore.bmp.web.clerk;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halo.core.header.HeaderInfoHolder;
import com.halo.core.web.annotation.ResponseMessage;
import com.newcore.bmp.authority.util.DesUtils;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.QueryClerk;
import com.newcore.bmp.models.webvo.QueryCond;
import com.newcore.bmp.service.api.authority.ClerkService;
import com.newcore.supports.models.cxf.CxfHeader;
import com.newcore.supports.util.HeaderInfoUtils;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

	@Autowired
	ClerkService clerkService;


	@RequestMapping(value = "/changepassword" )
	public @ResponseMessage boolean ChangePassword(@RequestBody QueryClerk qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
    	HeaderInfoHolder.setOutboundHeader(header);
    	
    	//kfs20180713
    	qc.setPassword(DesUtils.strDec(qc.getPassword(), null));
    	qc.setNewPassword(DesUtils.strDec(qc.getNewPassword(), null));
    	//kfs20180713

    	
    	return  clerkService.ChangePassword(qc);
	}

///*	
	@RequestMapping(value = "/getinfobyclerkno" )
	@ResponseBody
	public ClerkBo  GetInfoByClerkNo(@RequestBody Clerk qc)
	{
		//CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		//HeaderInfoHolder.setOutboundHeader(header);
		ClerkBo object = clerkService.GetInfoByClerkNo(qc);
		System.out.println(object);
		return  object;
	}
//*/
	
/*
	@RequestMapping(value = "/getinfobyclerkno" )	
	public @ResponseMessage ClerkBo  GetInfoByClerkNo(@RequestBody Clerk qc)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
		HeaderInfoHolder.setOutboundHeader(header);
		ClerkBo object = clerkService.GetInfoByClerkNo(qc);
		System.out.println(object);
		return  object;
	}
*/
	
}
