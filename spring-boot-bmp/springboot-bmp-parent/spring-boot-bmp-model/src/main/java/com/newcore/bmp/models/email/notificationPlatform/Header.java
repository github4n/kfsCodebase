package com.newcore.bmp.models.email.notificationPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Header {
	@Value("${token:''}")
	String token;
	
	@Value("${userChannel:''}")
	String userChannel;
	
	@Value("${msgType:''}")
	String msgType;
	
	@Autowired
	User user;
}
