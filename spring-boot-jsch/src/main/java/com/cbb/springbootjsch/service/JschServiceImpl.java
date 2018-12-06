package com.cbb.springbootjsch.service;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class JschServiceImpl {

	private String charset = "UTF-8";
	private JSch jsch;
	private Session session;
	Channel channel = null;
	ChannelSftp chSftp = null;

	// 要连接的服务器信息
	@Value("${host.ip}")
	private String jschHost;

	@Value("${host.port}")
	private int jschPort;

	@Value("${host.user}")
	private String jschUserName;

	@Value("${host.password}")
	private String jschPassWord;

	@Value("${host.connect.timeout}")
	private int jschTimeOut;
	
	//临时存放远程文件的地方
	private String localFilePath = "/tmp/";

	// 连接到指定的服务器
	public boolean connect() {

		jsch = new JSch();

		boolean result = false;

		try {

			long begin = System.currentTimeMillis();// 连接前时间
			log.debug("Try to connect to jschHost = " + jschHost + ",as jschUserName = " + jschUserName
					+ ",as jschPort =  " + jschPort);

			session = jsch.getSession(jschUserName, jschHost, jschPort);
			session.setPassword(jschPassWord);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setTimeout(jschTimeOut);
			session.connect();

			log.debug("Connected successfully to jschHost = " + jschHost + ",as jschUserName = " + jschUserName
					+ ",as jschPort =  " + jschPort);

			long end = System.currentTimeMillis();// 连接后时间

			log.debug("Connected To SA Successful in {} ms", (end - begin));

			result = session.isConnected();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (result) {
				log.debug("connect success");
			} else {
				log.debug("connect failure");
			}
		}

		if (!session.isConnected()) {
			log.error("获取连接失败");
		}
		return session.isConnected();
	}

	// 关闭连接
	public void close() {

		if (channel != null && channel.isConnected()) {
			channel.disconnect();
			channel = null;
		}

		if (session != null && session.isConnected()) {
			session.disconnect();
			session = null;
		}

	}

	/*
	 * // 下载文件 public void download() {
	 * 
	 * return; }
	 */

	/**
	 * 下载文件
	 * 
	 * @param localStoredFile 下载到的本地文件地址，有两种写法 １、如/opt，拿到则是默认文件名 ２、/opt/文件名，则是另起一个名字
	 * @param downloadFile    要下载的远端文件地址， 如/opt/xxx.txt
	 *
	 */
	public void download(String localStoredFile, String downloadFile) {
		try {

			log.debug("Opening Channel.");
			channel = session.openChannel("sftp");
			channel.connect();
			chSftp = (ChannelSftp) channel;
			SftpATTRS attr = chSftp.stat(downloadFile);
			long fileSize = attr.getSize();
			log.info("downloadFile size = " + fileSize + "bytes");

			OutputStream out = new FileOutputStream(localStoredFile);

			InputStream is = chSftp.get(downloadFile, new MyProgressMonitor());
			byte[] buff = new byte[1024 * 2];
			int read;
			if (is != null) {
				log.debug("Start to read input stream");
				do {
					read = is.read(buff, 0, buff.length);
					if (read > 0) {
						out.write(buff, 0, read);
					}
					out.flush();
				} while (read >= 0);

				out.close();
				log.debug("input stream read done.");
			}

			is.close();
			log.debug("成功下载文件至" + localStoredFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			chSftp.quit();
			if (channel != null) {
				channel.disconnect();
				log.debug("channel disconnect");
			}
		}
	}

	/**
	 * 下载远程的文件
	 * 
	 * @param localStoredFile 下载到的本地文件地址,如/tmp/xxx.txt
	 * @param downloadFile    要下载的远端文件地址,如/opt/xxx.txt
	 *
	 */
	public void downloadToLocalFile(String localStoredFile, String downloadFile) {

		boolean isConnected = false;
		isConnected = this.connect();

		log.info("下载文件前，连接状态=" + isConnected);

		if (isConnected == true) {

			this.download(localStoredFile, downloadFile);

			this.close();

		}
	}

	public String downloadFileToExplore( String downloadFile, HttpServletResponse response) {
		
		//下载远程的文件到本地
		this.downloadToLocalFile(localStoredFile, downloadFile);
		
		localFilePath
		
		String filePath = "D:\\car.txt";
		String fileName = "car2.txt";

		return transferLocalFileToExplorer(response, filePath, fileName);
	}

	private String transferLocalFileToExplorer(HttpServletResponse response, String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
				System.out.println("success");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

}
