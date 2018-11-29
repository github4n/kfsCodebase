package com.newcore.bmp.service.impl.syncConditionMonitor;



import java.text.SimpleDateFormat;
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
import com.newcore.bmp.dao.api.syncConditionMonitor.SyncConditionMonitorDao;
import com.newcore.bmp.models.email.Email;
import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.service.api.syncConditionMonitor.SyncConditionMonitorService;

import lombok.extern.slf4j.Slf4j;

@Service("syncConditionMonitorService")
@Slf4j
public class SyncConditionMonitorServiceImpl implements SyncConditionMonitorService {
	@Autowired
	SyncConditionMonitorDao syncConditionMonitorDao;

	@Autowired
	EmailDao emailDao;
	
	@Value("${kafka_delay_time:300}")
	int kafka_delay_time;

	// 检测实时同步情况
		public void syncConditionMonitor(String system) throws Exception {
			 
			int syncSeconds = syncConditionMonitorDao.syncConditionMonitor(system);
			log.info("消费程序最新同步时间与当前时间相差 =  {} 秒, 延迟阈值 =  {} 秒",syncSeconds, kafka_delay_time);

			if (syncSeconds > kafka_delay_time) { //发邮件告警
				String mailMessage = "消费程序最新同步时间与当前时间相差"+syncSeconds +"s, 请及时关注";
				log.error(mailMessage);
				
				this.sendEmail(system, mailMessage);
			}
		}
	
		@Value("${sendmailFlag:true}")
		boolean sendmailFlag;
		
		//最大相差时间10分钟，发邮件告警
		public void sendEmail(String system, String mailMessage) throws Exception {

			if(!sendmailFlag) {
				return;
			}
			
			
			String systemEnglishName = emailDao.SelectSystemEnglishName(system);
//			System.out.println("systemEnglishName = " +systemEnglishName);
			
			//准备工作
			// 获得当前年月日
			String today = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss").format(new Date());
			
			
			//获取收件人订阅表
			List<EmailSubscription> emailSubscription = emailDao.SelectEmailSubscriptionOfAdministrator(system);		
//			 System.out.println("sizeemailSubscription = "+emailSubscription.size());

			// 邮件主题
			String subject = systemEnglishName + "与KAFKA消费程序实时同步情况的告警_" + today;

			// 邮件正文
			String content = today + ",<br>" + "<blockquote> " + mailMessage + "<br>" + "</blockquote>";
			
			//附件名称　 生产环境
			String attachFile = null;

			
			//发送流程开始
			// 1. 创建参数配置, 用于连接邮件服务器的参数配置
			Properties props = new Properties(); 
			props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
			props.setProperty("mail.smtp.host", Email.getMyemailsmtphost()); // 发件人的邮箱的 SMTP服务器地址
			props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

			// 2. 根据配置创建会话对象, 用于和邮件服务器交互
			Session session = Session.getInstance(props);
			session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

			// 3. 创建一封邮件	
			MimeMessage message = createMimeMessage(session, Email.getSendemailaccount(),  Email.getSendemailname(), emailSubscription,subject, content, attachFile);

			// 4. 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();

			// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			transport.connect(Email.getSendemailaccount(), Email.getSendemailpassword());

			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
			// 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());

			// 7. 关闭连接
			transport.close();
		}
		
		// 创建一封只包含文本的简单邮件
		public static MimeMessage createMimeMessage(Session session, 
				String sendEmailAccount, String sendEmailName, 
				List<EmailSubscription> emailSubscriptionList, 
				String subject, String content, String attachFile)
						throws Exception {
			// 1. 创建一封邮件
			MimeMessage message = new MimeMessage(session);

			// 2. From: 发件人
			message.setFrom(new InternetAddress(sendEmailAccount, sendEmailName, "UTF-8"));

			// 3. To: 收件人（可以增加多个收件人、抄送、密送）
			int sizeES = emailSubscriptionList.size();
			for (int i = 0; i < sizeES; i++) {
				EmailSubscription es = emailSubscriptionList.get(i);
				
				switch(es.getType()) {
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
			if (attachFile != null) {
				bodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(attachFile); // 得到数据源
				bodyPart.setDataHandler(new DataHandler(fds)); // 得到附件本身并放入BodyPart
				bodyPart.setFileName(fds.getName()); // 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
				multiPart.addBodyPart(bodyPart);
			}


			// 设置邮件消息的主要内容
			message.setContent(multiPart);

			// 6. 设置发件时间
			message.setSentDate(new Date());

			// 7. 保存设置
			message.saveChanges();

			return message;
		}
}
