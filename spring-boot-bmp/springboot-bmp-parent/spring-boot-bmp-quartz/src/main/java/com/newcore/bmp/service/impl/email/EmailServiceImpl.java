package com.newcore.bmp.service.impl.email;


import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newcore.bmp.dao.api.email.EmailDao;
import com.newcore.bmp.models.email.Email;
import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.service.api.email.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	@Autowired
	EmailDao emailDao;

	@Autowired
	Email email;
	
	
	//是否实际发送邮件
	@Value("${sendmailFlag:true}")
	boolean sendmailFlag;
	
	//发送邮件
	@Override
	public void sendEmail(List<EmailSubscription> emailSubscription, String subject, String content, String attachFile) throws Exception {

		if(!sendmailFlag) {
			return;
		}
		
		// 发送流程开始
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", email.getMyEmailSMTPHost()); // 发件人的邮箱的 SMTP服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getInstance(props);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message = this.createMimeMessage(session, email.getSendEmailAccount(), email.getSendEmailName(),
				emailSubscription, subject, content, attachFile);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
		transport.connect(email.getSendEmailAccount(), email.getSendEmailPassword());

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	}
	
	// 创建一封只包含文本的简单邮件
		public  MimeMessage createMimeMessage(Session session, String sendEmailAccount, String sendEmailName,
				List<EmailSubscription> emailSubscriptionList, String subject, String content, String attachFile)
				throws Exception {
			// 1. 创建一封邮件
			MimeMessage message = new MimeMessage(session);

			// 2. From: 发件人
			message.setFrom(new InternetAddress(sendEmailAccount, sendEmailName, "UTF-8"));

			// 3. To: 收件人（可以增加多个收件人、抄送、密送）
			int sizeES = emailSubscriptionList.size();
			for (int i = 0; i < sizeES; i++) {
				EmailSubscription es = emailSubscriptionList.get(i);

				switch (es.getType()) {
				case "TO":
					message.addRecipient(MimeMessage.RecipientType.TO,
							new InternetAddress(es.getEmailAddress(), es.getEmailName(), "UTF-8"));
					break;

				case "CC":
					message.addRecipient(MimeMessage.RecipientType.CC,
							new InternetAddress(es.getEmailAddress(), es.getEmailName(), "UTF-8"));
					break;

				case "BCC":
					message.addRecipient(MimeMessage.RecipientType.BCC,
							new InternetAddress(es.getEmailAddress(), es.getEmailName(), "UTF-8"));
					break;

				default:
					break;

				}

			}

			// 4. 邮件主题
			message.setSubject(subject, "UTF-8");

			// 5. 邮件正文和附件
			Multipart multiPart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();

			// 设置html邮件消息内容
			bodyPart.setContent(content, "text/html; charset=utf-8");
			multiPart.addBodyPart(bodyPart);

			// 添加附件
			bodyPart = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(attachFile); // 得到数据源
			bodyPart.setDataHandler(new DataHandler(fds)); // 得到附件本身并放入BodyPart
			bodyPart.setFileName(fds.getName()); // 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
			multiPart.addBodyPart(bodyPart);

			// 设置邮件消息的主要内容
			message.setContent(multiPart);

			// 6. 设置发件时间
			message.setSentDate(new Date());

			// 7. 保存设置
			message.saveChanges();

			return message;
		}
		
		//获取系统对应的邮件人列表
		public List<EmailSubscription> SelectEmailSubscriptionOfSystem(String system) {
				return emailDao.SelectEmailSubscriptionOfSystem(system);
		}
		
		//只给管理员发
		public List<EmailSubscription> SelectEmailSubscriptionOfAdministrator(String system) {
				return emailDao.SelectEmailSubscriptionOfAdministrator(system);
		}
}
