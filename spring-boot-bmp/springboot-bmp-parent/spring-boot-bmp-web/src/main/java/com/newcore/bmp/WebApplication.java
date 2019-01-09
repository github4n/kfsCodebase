package com.newcore.bmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@ImportResource("classpath:applicationContext-web.xml")      //导入xml配置
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}

