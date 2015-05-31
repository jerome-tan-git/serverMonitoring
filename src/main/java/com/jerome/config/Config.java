package com.jerome.config;

import java.util.ArrayList;
import java.util.List;

public class Config {
	private String userName;
	private String password;
	private List<ServerObj> serverList = new ArrayList<ServerObj>();
	private List<ProcessObj> processList = new ArrayList<ProcessObj>();
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
	public List<ServerObj> getServerList() {
		return serverList;
	}
	public void setServerList(List<ServerObj> serverList) {
		this.serverList = serverList;
	}
	public List<ProcessObj> getProcessList() {
		return processList;
	}
	public void setProcessList(List<ProcessObj> processList) {
		this.processList = processList;
	}
}