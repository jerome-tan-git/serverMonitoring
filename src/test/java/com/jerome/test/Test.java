package com.jerome.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jerome.config.Config;
import com.jerome.config.ProcessObj;
import com.jerome.config.ServerObj;
import com.jerome.validate.ReadConfig;

public class Test {
	public static void main(String[] args) throws IOException, JSchException
	{
//		ProcessObj proc = new ProcessObj();
//		proc.setProcessName("TestProcessName");
//		proc.setProcessNameRegex("Test process regex");
//		
//		ServerObj server = new ServerObj();
//		server.setPassword("server password");
//		server.setServerAddress("Server address");
//		server.setUserName("server userName");
//		
//		
//		Config configObj = new Config();
//		configObj.setPassword("password");
//		configObj.setUserName("userName");
//		List<ProcessObj> processes = new ArrayList<ProcessObj>();
//		processes.add(proc);
//		List<ServerObj> servers = new ArrayList<ServerObj>();
//		servers.add(server);
//		configObj.setProcessList(processes);
//		configObj.setServerList(servers);
//		System.out.println(JSON.toJSONString(configObj, true));
		Config conf = ReadConfig.readConfig("./config.json");
		System.out.println(conf.getServerList().get(0).getServerAddress());
		JSch jsch = new JSch();  
		
        Session session = jsch.getSession(conf.getUserName(), conf.getServerList().get(0).getServerAddress(), 22);  
        session.setPassword(conf.getPassword());  
        java.util.Properties config = new java.util.Properties();  
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config);  
        session.connect();  
//        String command = "cat /proc/loadavg";  
//        String command = "ps aux | grep java";
        String command = "cat /proc/stat";
//        String command = "cat /proc/cpuinfo";
//        String command = "cat /proc/meminfo";  
        ///proc/stat
        String charset = "UTF-8";  
        Channel channel = session.openChannel("exec");  
        ((ChannelExec) channel).setCommand(command);  
        channel.setInputStream(null);  
        ((ChannelExec) channel).setErrStream(System.err);  
          
        channel.connect();  	
        InputStream in = channel.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));  
        String buf = null;  
        while ((buf = reader.readLine()) != null)  
        {  
            System.out.println(buf);  
        }  
        reader.close();  
        channel.disconnect();  
        session.disconnect();  
	}
}
