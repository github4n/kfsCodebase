package com.newcore.bmp.models.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data 
public class Email {
	
	@Value("${sendEmailAccount}")
	String sendEmailAccount;

	// 发件人密码
	@Value("${sendEmailPassword}")
	String sendEmailPassword;

	// 发件人名称
	@Value("${sendEmailName}")
	String sendEmailName;

	// 发件邮件服务器smtp的ip地址
	@Value("${myEmailSMTPHost}")
	String myEmailSMTPHost;
	
	/* 生产环境
	// 发件人邮箱
	//@Value("${sendEmailAccount}")
	final static String sendEmailAccount = "bmp@e-chinalife.com";

	// 发件人密码
	//@Value("${sendEmailPassword}")
	final static String sendEmailPassword = "Bmp!Q@W3e4r";

	// 发件人名称
	//@Value("${sendEmailName}")
	final static String sendEmailName = "作业管理平台";

	// 发件邮件服务器smtp的ip地址
	//@Value("${myEmailSMTPHost}")
	final static String myEmailSMTPHost = "10.21.14.72";
	*/

	/*
	 * 测试环境 // 发件人邮箱 final static String sendEmailAccount =
	 * "kongfanshuo0224@163.com";
	 * 
	 * // 发件人密码 final static String sendEmailPassword = "kenducky39";
	 * 
	 * // 发件人名称 final static String sendEmailName = "作业管理平台";
	 * 
	 * //发件邮件服务器smtp的ip地址 final static String myEmailSMTPHost = "smtp.163.com";
	 * 
	 */

/*
	public static String getSendemailaccount() {
		return sendEmailAccount;
	}

	public static String getSendemailpassword() {
		return sendEmailPassword;
	}

	public static String getSendemailname() {
		return sendEmailName;
	}

	public static String getMyemailsmtphost() {
		return myEmailSMTPHost;
	}
*/

}
