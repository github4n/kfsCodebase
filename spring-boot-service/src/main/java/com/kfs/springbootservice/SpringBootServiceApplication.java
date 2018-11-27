package com.kfs.springbootservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringBootServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServiceApplication.class, args);
	}
}
