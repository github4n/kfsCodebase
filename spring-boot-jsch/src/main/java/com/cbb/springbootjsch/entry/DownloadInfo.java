package com.cbb.springbootjsch.entry;


import lombok.Data;

@Data
public class DownloadInfo {
	

	/** 下载到的本地文件地址 */
	private String localStoredFile;

	/** 要下载的远端文件地址 */
	private String downloadFile;
	
}
