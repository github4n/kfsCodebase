package com.newcore.bmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@ImportResource("classpath:applicationContext-service.xml")      //导入xml配置
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}

