package com.newcore.bmp.models.webvo;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JsonFirstLayer {
	String success; 
	String status; 
	String msg; 
	List<JsonSecondLayer> obj; 	
	String error;
	String total;
	

	
	public String getSuccess() {
		return success;
	}



	public void setSuccess(String success) {
		this.success = success;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public List<JsonSecondLayer> getObj() {
		return obj;
	}



	public void setObj(List<JsonSecondLayer> obj) {
		this.obj = obj;
	}



	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}



	public String getTotal() {
		return total;
	}



	public void setTotal(String total) {
		this.total = total;
	}

/*
	@Override
	public String toString() 
	{
		return "JsonFirstLayer ["+
				"success=" + success + ", "+
				"status="+ status + ", "+
				"obj="+ obj +
				"]";
	}
*/

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}