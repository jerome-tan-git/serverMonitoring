package com.jerome.config;

public class ProcessObj {
	private String processNameRegex;
	private String processName;
	private String serverAddress;
	private String userName;
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProcessNameRegex() {
		return processNameRegex;
	}
	public void setProcessNameRegex(String processNameRegex) {
		this.processNameRegex = processNameRegex;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public String toString()
	{
		return "Regex: " + this.processNameRegex + " Name: " + this.processName + " Address: " + this.serverAddress + " username: " + this.userName + " password: " + this.password; 
	}
}
