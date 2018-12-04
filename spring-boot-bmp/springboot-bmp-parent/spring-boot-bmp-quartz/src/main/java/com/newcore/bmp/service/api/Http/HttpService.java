package com.newcore.bmp.service.api.Http;

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
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HttpService {

	// post请求
	public static String doPost(String url, String params)  {

		log.info("请求url:" + url);
		log.info("请求参数:" + params);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Accept", "application/json");
		
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		String jsonResponse = "";
		
		try {

			response = httpclient.execute(httpPost);

			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			
			log.info("请求返回码" + state);

			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				jsonResponse = EntityUtils.toString(responseEntity);
				
				log.info("请求返回参数: = " + jsonResponse);

			} else {
				log.error("请求返回的错误码:" + state);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		return jsonResponse;

	}

}