package com.newcore.bmp.service.api.quartz;

public interface ExampleQuartzService {

	//异常判断
	public void errorJudging(String system);
	
	//异常消除逻辑
	public void errorEliminate(String system);

	//发送email
	public void sendEmail() throws Exception;
	
}
