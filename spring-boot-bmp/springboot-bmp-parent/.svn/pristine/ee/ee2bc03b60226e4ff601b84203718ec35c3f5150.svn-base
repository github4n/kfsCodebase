package com.newcore.bmp.boot.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.WebStatFilter;



@Configuration
public class FilterRegistration {
	
	//Druid的URL监听器
	@Bean
    public FilterRegistrationBean  druidWebStatFilterRegistration() {
		
		WebStatFilter filter = new WebStatFilter();
		filter.setSessionStatEnable(false);
		filter.setProfileEnable(true);
		
		FilterRegistrationBean  registration = new FilterRegistrationBean(filter);	
		registration.addUrlPatterns("/bmp/serviceapp/services/*");
		
        return registration;
    }
}

