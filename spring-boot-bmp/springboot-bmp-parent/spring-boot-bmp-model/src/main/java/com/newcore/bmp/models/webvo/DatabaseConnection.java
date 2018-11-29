package com.newcore.bmp.models.webvo;



public class DatabaseConnection {
	String id;
	String system;
	String provBranchCode;
	String databaseIp;
	String databasePort;
	String databaseName;
	String databasePassword;
	String instanceName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getProvBranchCode() {
		return provBranchCode;
	}
	public void setProvBranchCode(String provBranchCode) {
		this.provBranchCode = provBranchCode;
	}
	public String getDatabaseIp() {
		return databaseIp;
	}
	public void setDatabaseIp(String databaseIp) {
		this.databaseIp = databaseIp;
	}
	public String getDatabasePort() {
		return databasePort;
	}
	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getDatabasePassword() {
		return databasePassword;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	@Override
	public String toString() {
		return "DatabaseConnection [id=" + id + ", system=" + system + ", provBranchCode=" + provBranchCode
				+ ", databaseIp=" + databaseIp + ", databasePort=" + databasePort + ", databaseName=" + databaseName
				+ ", databasePassword=" + databasePassword + ", instanceName=" + instanceName + "]";
	}


	
	
	

}
