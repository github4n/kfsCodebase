package com.newcore.bmp.boot.servlet;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.newcore.supports.web.CheckHealthDispather;

@Configuration
public class ServletRegistration {
	@Bean
    public ServletRegistrationBean cxfServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CXFServlet());
        registration.addUrlMappings("/services/*");
        registration.setLoadOnStartup(1);
        return registration;
    }
	
	//暂时没使用到
	@Bean
    public ServletRegistrationBean druidStatViewServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        return registration;
    }
	
	
	@Bean
    public ServletRegistrationBean healthCheckerServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CheckHealthDispather(),"/healthchecker");
        return registration;
    }
}

