package com.cbb.springbootjsch.service;

import com.jcraft.jsch.SftpProgressMonitor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyProgressMonitor implements SftpProgressMonitor {

	private long transfered;

	@Override
	public void init(int op, String src, String dest, long max) {
		// TODO Auto-generated method stub
		log.debug("Transferring begins.");

	}

	@Override
	public boolean count(long count) {
		transfered = transfered + count;
		log.debug("Currently transferred total size: " + transfered + " bytes");
		return true;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		log.debug("Transferring finishes.");

	}

}
