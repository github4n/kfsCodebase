package com.kfs.springbootservice.service.portService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortService {
	@Value("${server.port}")
	String port;
	
    @RequestMapping("/port")
	public String hello() {
        return "Hello World, I'm from port : " + port;
    }
}
