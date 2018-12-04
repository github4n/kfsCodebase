package com.newcore.bmp.models.email.notificationPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Body {
	String title;
	
	String text;
	
	@Autowired
	SMS sms;
	
	@Autowired
	Mail mail;
	
	@Autowired
	Cloud cloud;
	
	String opDate;
}