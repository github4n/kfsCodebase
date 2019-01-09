package com.newcore.bmp.models.webvo;



public class SystemCommandDef {
	String system;
	String itFramework;
	String startCommand;
	String haltCommand;
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getItFramework() {
		return itFramework;
	}
	public void setItFramework(String itFramework) {
		this.itFramework = itFramework;
	}
	public String getStartCommand() {
		return startCommand;
	}
	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}
	public String getHaltCommand() {
		return haltCommand;
	}
	public void setHaltCommand(String haltCommand) {
		this.haltCommand = haltCommand;
	}
	@Override
	public String toString() {
		return "SystemCommandDef [system=" + system + ", itFramework=" + itFramework + ", startCommand=" + startCommand
				+ ", haltCommand=" + haltCommand + "]";
	}

	
	
	

}
