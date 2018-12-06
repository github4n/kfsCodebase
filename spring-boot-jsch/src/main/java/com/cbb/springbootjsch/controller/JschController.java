package com.cbb.springbootjsch.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
import com.cbb.springbootjsch.utils.GSONTools;

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
	public String download(@RequestBody DownloadInfo info, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		//map.put("result", "download ok");
		
		//log.info(info.getLocalStoredFile());
		log.info(info.getDownloadFile());

		return jschServiceImpl.downloadFileToExplore( info.getDownloadFile(), response);
		
		//jschServiceImpl.downloadToLocalFile(info.getLocalStoredFile(), info.getDownloadFile());

		//return GSONTools.toJson(map);

	}	
}
