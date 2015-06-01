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
		for(ServerObj so: this.serverList)
		{
			if(so.getUserName() == null || so.getUserName().trim().equals(""))
			{
				System.out.println("Set username: " + this.userName);
				so.setUserName(this.userName);
				so.setPassword(this.password);
			}
		}
		return serverList;
	}
	public void setServerList(List<ServerObj> serverList) {

		this.serverList = serverList;
	}
	public List<ProcessObj> getProcessList() {
		for(ProcessObj po: this.processList)
		{
			if(po.getUserName() == null || po.getUserName().trim().equals(""))
			{
				System.out.println("Set username: " + this.userName);
				po.setUserName(this.userName);
				po.setPassword(this.password);
			}
		}
		return processList;
	}
	public void setProcessList(List<ProcessObj> processList) {
		
		this.processList = processList;
	}
}
