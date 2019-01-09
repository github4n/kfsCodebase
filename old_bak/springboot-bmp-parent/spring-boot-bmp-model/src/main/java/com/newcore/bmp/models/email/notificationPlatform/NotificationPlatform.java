package com.newcore.bmp.models.email.notificationPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data 
public class NotificationPlatform {
	
	@Autowired
	Header header;	
	
	@Autowired
	Body body;

}
