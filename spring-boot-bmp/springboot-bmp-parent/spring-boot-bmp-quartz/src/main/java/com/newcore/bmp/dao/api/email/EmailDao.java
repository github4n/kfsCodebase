package com.newcore.bmp.dao.api.email;

import java.util.List;

import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.models.errorJudge.ErrorDefine;
import com.newcore.bmp.models.errorJudge.ErrorTrail;
import com.newcore.bmp.models.errorJudge.SelectField;
import com.newcore.bmp.models.webvo.RunningBatchVo;

public interface EmailDao {
	//获取系统对应的邮件人列表
	public List<EmailSubscription> SelectEmailSubscriptionOfSystem(String system);
	
	public List<EmailSubscription> SelectEmailSubscriptionOfAdministrator(String system);

	public List<EmailSubscription> SelectEmailSubscriptionOfChudan(String system);

}
