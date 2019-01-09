package com.newcore.bmp.boot.jaxrsClient;


import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.halo.core.fastjson.support.FastJsonProvider;
import com.newcore.bmp.service.api.TaskRunMonitor;
import com.newcore.bmp.service.api.authority.ClerkService;
import com.newcore.supports.web.CheckHealthDispather;

@Configuration
public class JaxrsClient {
    
	@Bean
	public ClerkService clerkServiceRegistration() {

	  String baseAddress = "http://127.0.0.1:9002/bmp/serviceapp/services/rest/clerkService";
	  
      List<Object> providerList = new ArrayList<Object>();
      providerList.add(new FastJsonProvider());

      ClerkService clerkService = JAXRSClientFactory.create(baseAddress, ClerkService.class, providerList);
      
      return clerkService;
	}
	
	@Bean
	public TaskRunMonitor taskRunMonitorRegistration() {
		System.out.println("taskRunMonitorRegistration");
	  String baseAddress = "http://localhost:9002/bmp/serviceapp/services/rest/TaskRunMonitor";
	  
      List<Object> providerList = new ArrayList<Object>();
      providerList.add(new FastJsonProvider());

      TaskRunMonitor taskRunMonitor = JAXRSClientFactory.create(baseAddress, TaskRunMonitor.class, providerList);
      
      return taskRunMonitor;
	}
	

}


