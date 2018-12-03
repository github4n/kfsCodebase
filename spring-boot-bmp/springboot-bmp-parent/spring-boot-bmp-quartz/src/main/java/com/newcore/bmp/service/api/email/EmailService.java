package com.newcore.bmp.service.api.email;

import java.util.List;

import com.newcore.bmp.models.email.EmailSubscription;

public interface EmailService {

	void sendEmail(List<EmailSubscription> emailSubscription, String subject, String content, String attachFile)
			throws Exception;
	
	public List<EmailSubscription> SelectEmailSubscriptionOfSystem(String system);
	
	public List<EmailSubscription> SelectEmailSubscriptionOfAdministrator(String system);
}
