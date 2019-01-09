package com.newcore.bmp.web.clerk;


import com.halo.core.header.HeaderInfoHolder;
import com.halo.core.web.annotation.ResponseMessage;
import com.newcore.bmp.authority.util.DesUtils;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.service.api.authority.ClerkService;
import com.newcore.supports.models.cxf.CxfHeader;
import com.newcore.supports.util.HeaderInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/exp/clerk")
public class RegisterController {

	@Autowired
	ClerkService clerkService;
	
	@RequestMapping(value = "/createclerk" )
	public @ResponseMessage boolean CreateClerk(@RequestBody Clerk clerk)
	{
		CxfHeader header = HeaderInfoUtils.buildDefaultHeaderInfo();
    	HeaderInfoHolder.setOutboundHeader(header);
    	
    	//kfs20180713
    	clerk.setPassword(DesUtils.strDec(clerk.getPassword(), null));
    	//kfs20180713
    	
    	return  clerkService.createClerk(clerk);
	}

}
