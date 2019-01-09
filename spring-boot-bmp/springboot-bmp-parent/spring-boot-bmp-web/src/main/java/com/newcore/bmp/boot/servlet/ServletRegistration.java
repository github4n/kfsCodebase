package com.newcore.bmp.boot.servlet;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import com.newcore.supports.web.CheckHealthDispather;

@Configuration
public class ServletRegistration {
	
	@Bean
    public ServletRegistrationBean healthCheckerServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CheckHealthDispather(),"/healthchecker");
        return registration;
    }
}

