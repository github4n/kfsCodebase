package com.newcore.bmp.service.impl.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
 

/**
 * HttpClient工具类
 */
public class HttpUtil {
	
	private static Logger logger = Logger.getLogger(HttpUtil.class);
 
	
	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, String params) throws Exception {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost   
    	httpPost.setHeader("Accept", "application/json"); 
    	httpPost.setHeader("Content-Type", "application/json");
    	httpPost.setHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCTVAifQ.2cBFqbsbLnop-cjm8Zt1dABAKwzfxtSnO8BmfLHbZiY");

    	String charSet = "UTF-8";
    	StringEntity entity = new StringEntity(params, charSet);
    	httpPost.setEntity(entity);        
        CloseableHttpResponse response = null;
        
        String jsonString = "";
        try {
        	
        	response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
			 logger.info("请求参数:"+params);
			 logger.info("请求返回:"+state+"("+url+")");

            if (state == HttpStatus.SC_OK) {
            	HttpEntity responseEntity = response.getEntity();
            	jsonString = EntityUtils.toString(responseEntity);
   			 	logger.info("jsonString = "+jsonString);

//   			 	return jsonString;
            }
            else{
				 logger.error("请求返回:"+state+"("+url+")");
			}
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
//        return null;
		 return jsonString;

	}
	
}
