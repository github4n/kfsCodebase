package com.kfs.springcloudeurecaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurecaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudEurecaServerApplication.class, args);
	}
}
