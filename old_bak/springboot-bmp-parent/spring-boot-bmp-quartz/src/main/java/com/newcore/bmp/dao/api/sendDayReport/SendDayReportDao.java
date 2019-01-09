package com.newcore.bmp.dao.api.sendDayReport;


import java.util.List;

import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.models.errorJudge.ErrorTrail;


public interface SendDayReportDao {
	
	public int calcBatchNums(String system, String batchTypeDetail);

	public int calcprovinceNums();

	public int calcUnRunBatchNums(String system, String batchTypeDetail, String errReasonDetail);

	public int calcRelativeProvinceNums(String system, String batchTypeDetail, String errReasonDetail);

	public List<ErrorTrail> SelectErrorTrail(String system, String errReasonDetail);

	public List<EmailSubscription> SelectEmailSubscription(String system);
	
	public String SelectSystemEnglishName(String system);
	
}
