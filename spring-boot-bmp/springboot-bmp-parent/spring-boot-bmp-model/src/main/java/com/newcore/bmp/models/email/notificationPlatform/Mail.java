package com.newcore.bmp.models.email.notificationPlatform;


import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Mail {
	String email;
	
	String copyMail;
	
	String blindMail;
}