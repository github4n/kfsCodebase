package com.newcore.bmp.models.email.notificationPlatform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class User {
	@Value("${userid}")
	String userid;
}
