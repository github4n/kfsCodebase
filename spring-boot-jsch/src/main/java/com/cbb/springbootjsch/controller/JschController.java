package com.cbb.springbootjsch.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbb.springbootjsch.entry.DownloadInfo;
import com.cbb.springbootjsch.service.JschServiceImpl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/jsch")
@Data
@Slf4j
public class JschController {

	@Autowired
	private JschServiceImpl jschServiceImpl;
	
	@Value("${host.ip}")
	private String host;
	
	//下载文件
	@ResponseBody
	@PostMapping(value = "/download", produces = "application/json; charset=UTF-8")
	public void download(@RequestBody DownloadInfo info, HttpServletResponse response) {
		
		log.info("要下载的文件：" + info.getDownloadFile());

		jschServiceImpl.downloadFileToExplorer( info.getDownloadFile(), response);
		

		//Map<String, Object> map = new HashMap<>();
		//map.put("result", "download ok");
		//return GSONTools.toJson(map);

	}	
}
