package com.newcore.bmp.service.impl.ssh;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 注释
 *
 * @Author： coding99 @Date： 16-9-2 @Time： 下午7:33
 */
public class JschUtil {

	private final Logger logger = LoggerFactory.getLogger(JschUtil.class);

	private String charset = "UTF-8"; // 设置编码格式,可以根据服务器的编码设置相应的编码格式
	private JSch jsch;
	private Session session;
	Channel channel = null;
	ChannelSftp chSftp = null;

	// 初始化配置参数
	private String jschHost;
	private int jschPort;
	private String jschUserName;
	private String privateKeyPath = "~/.ssh/id_rsa";
	private int jschTimeOut = 10000;

	// /**
	// * 静态内部类实现单例模式
	// */
	// private static class LazyHolder {
	// private static final JschUtil INSTANCE = new JschUtil();
	// }

	public JschUtil() {

	}

	// /**
	// * 获取实例
	// *
	// * @return
	// */
	// public static final JschUtil getInstance() {
	// return LazyHolder.INSTANCE;
	// }

	/**
	 * 连接到指定的服务器
	 * 
	 * @return
	 * @throws JSchException
	 */
	public boolean connect() throws JSchException {

		jsch = new JSch();// 创建JSch对象

		boolean result = false;

		try {

			long begin = System.currentTimeMillis();// 连接前时间
			logger.info("Try to connect to jschHost = " + jschHost + ",as jschUserName = " + jschUserName
					+ ",as jschPort =  " + jschPort);

			session = jsch.getSession(jschUserName, jschHost, jschPort);// 根据用户名，主机ip，端口获取一个Session对象

			// use private key
			jsch.addIdentity(this.getPrivateKeyPath());

			// not use password
			// session.setPassword(jschPassWord); // 设置密码

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);// 为Session对象设置properties
			session.setTimeout(jschTimeOut);// 设置连接超时时间
			session.connect();

			logger.info("Connected successfully to jschHost = " + jschHost + ",as jschUserName = " + jschUserName
					+ ",as jschPort =  " + jschPort);

			long end = System.currentTimeMillis();// 连接后时间

			logger.info("Connected To SA in {} ms", (end - begin));

			result = session.isConnected();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (result) {
				logger.info("connect success");
			} else {
				logger.info("connect failure");
			}
		}

		if (!session.isConnected()) {
			logger.error("获取连接失败");
		}

		return session.isConnected();

	}

	/**
	 * 关闭连接
	 */
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

	/**
	 * 脚本是同步执行的方式 执行脚本命令
	 * 
	 * @param command
	 * @return
	 */
	public Map<String, Object> execCmmmand(String command) throws Exception {

		Map<String, Object> mapResult = new HashMap<String, Object>();

		logger.info(command);

		StringBuffer result = new StringBuffer();// stdout返回结果
		BufferedReader reader = null;

		StringBuffer err_result = new StringBuffer();// stderr脚本返回结果
		BufferedReader err_reader = null;

		int returnCode = -2;// 脚本执行退出状态码

		try {

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);

			String err_fos_location = "/tmp/ssh/ssh_stderr_" + this.getJschHost() + "_"
					+ new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss_SSS").format(new Date());
			FileOutputStream fos = new FileOutputStream(err_fos_location);
			((ChannelExec) channel).setErrStream(fos);

			// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			// ((ChannelExec) channel).setErrStream(System.err);

			channel.connect();// 执行命令 等待执行结束

			// 获得标准输出stdout
			InputStream in = channel.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
			String res = "";
			while ((res = reader.readLine()) != null) {
				result.append(res + "\n");
				logger.info(res);
			}

			// channel.getExtInputStream();

			// 获得错误输出stderr
			InputStream err_in = new FileInputStream(err_fos_location);
			err_reader = new BufferedReader(new InputStreamReader(err_in, Charset.forName(charset)));
			String err_res = "";
			while ((err_res = err_reader.readLine()) != null) {
				err_result.append(err_res + "\n");
				logger.info(err_res);
			}

			returnCode = channel.getExitStatus();
			mapResult.put("returnCode", returnCode);
			mapResult.put("result", result.toString());
			mapResult.put("err_result", err_result.toString());

		} catch (IOException e) {

			logger.error(e.getMessage(), e);

		} catch (JSchException e) {

			logger.error(e.getMessage(), e);

		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return mapResult;

	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getJschHost() {
		return jschHost;
	}

	public void setJschHost(String jschHost) {
		this.jschHost = jschHost;
	}

	public int getJschPort() {
		return jschPort;
	}

	public void setJschPort(int jschPort) {
		this.jschPort = jschPort;
	}

	public String getJschUserName() {
		return jschUserName;
	}

	public void setJschUserName(String jschUserName) {
		this.jschUserName = jschUserName;
	}

	// public String getJschPassWord() {
	// return jschPassWord;
	// }
	//
	// public void setJschPassWord(String jschPassWord) {
	// this.jschPassWord = jschPassWord;
	// }

	public int getJschTimeOut() {
		return jschTimeOut;
	}

	public void setJschTimeOut(int jschTimeOut) {
		this.jschTimeOut = jschTimeOut;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}

}
