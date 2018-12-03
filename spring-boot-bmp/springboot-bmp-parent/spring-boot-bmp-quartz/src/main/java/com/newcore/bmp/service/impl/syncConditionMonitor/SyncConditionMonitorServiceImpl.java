package com.newcore.bmp.service.impl.syncConditionMonitor;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newcore.bmp.dao.api.syncConditionMonitor.SyncConditionMonitorDao;
import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.service.api.email.EmailService;
import com.newcore.bmp.service.api.syncConditionMonitor.SyncConditionMonitorService;

import lombok.extern.slf4j.Slf4j;

@Service("syncConditionMonitorService")
@Slf4j
public class SyncConditionMonitorServiceImpl implements SyncConditionMonitorService {
	@Autowired
	SyncConditionMonitorDao syncConditionMonitorDao;
	
	@Autowired
	EmailService emailService;
	
	@Value("${kafka_delay_time:300}")
	int kafka_delay_time;

	// 检测实时同步情况
		public void syncConditionMonitor(String system) throws Exception {
			 
			int syncSeconds = syncConditionMonitorDao.syncConditionMonitor(system);
			log.info("消费程序最新同步时间与当前时间相差 =  {} 秒, 延迟阈值 =  {} 秒",syncSeconds, kafka_delay_time);

			if (syncSeconds > kafka_delay_time) { //发邮件告警
				String mailMessage = "消费程序最新同步时间与当前时间相差"+syncSeconds +"s, 请及时关注";
				log.error(mailMessage);
				
				this.sendWarningEmail(system, mailMessage);
			}
		}
		
		//发告警邮件
		public void sendWarningEmail(String system, String mailMessage) throws Exception {
			
			String systemEnglishName = syncConditionMonitorDao.SelectSystemEnglishName(system);
			log.info("systemEnglishName = " +systemEnglishName);
			
			//准备工作
			// 获得当前年月日
			String today = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss").format(new Date());
			
			
			//获取收件人订阅表
			List<EmailSubscription> emailSubscription = emailService.SelectEmailSubscriptionOfSystem(system);		
			log.info("sizeemailSubscription = "+emailSubscription.size());

			// 邮件主题
			String subject = systemEnglishName + "与KAFKA消费程序实时同步情况的告警_" + today;

			// 邮件正文
			String content = today + ",<br>" + "<blockquote> " + mailMessage + "<br>" + "</blockquote>";
			
			//附件名称　 生产环境
			String attachFile = null;

			//调用email service发送邮件
			emailService.sendEmail(emailSubscription, subject, content, attachFile);

		}
}
