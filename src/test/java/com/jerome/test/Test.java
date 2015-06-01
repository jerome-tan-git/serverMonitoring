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
		ServerObj so = conf.getServerList().get(0);
		System.out.println(so);
        Session session = jsch.getSession(so.getUserName(), so.getServerAddress(), 22);  
        session.setPassword(so.getPassword());  
        java.util.Properties config = new java.util.Properties();  
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config);  
        session.connect();  
//        String command = "cat /proc/loadavg";  
//        String command = "ps aux | grep java";
//        String command = "top";
        String command = "cat /proc/stat";
        
//        String command = "netstat -anp";
//        String command = "nload";
        //
//        String command = "cat /proc/cpuinfo";
//        String command = "cat /proc/meminfo";  
        ///proc/stat
        String charset = "UTF-8";  
        Channel channel = session.openChannel("exec");  
        ((ChannelExec) channel).setCommand(command);  
        channel.setInputStream(null);  
        ((ChannelExec) channel).setErrStream(System.err);  
          
        channel.connect();  
        long startTime = System.currentTimeMillis();  
        InputStream in = channel.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));  
        String line = null;  
        long idleCpuTime1 = 0, totalCpuTime1 = 0;
        while ((line = reader.readLine()) != null)  
        {  
        	 if(line.startsWith("cpu")){  
                 line = line.trim();    
                 String[] temp = line.split("\\s+");   
                 idleCpuTime1 = Long.parseLong(temp[4]);  
                 for(String s : temp){  
                     if(!s.equals("cpu")){  
                         totalCpuTime1 += Long.parseLong(s);  
                     }  
                 }     
                 System.out.println("IdleCpuTime: " + idleCpuTime1 + ", " + "TotalCpuTime: " + totalCpuTime1);  
                 break;  
             }   
        }  
        
        reader.close(); 
        channel.disconnect();  

        
        
        
        charset = "UTF-8";  
        channel = session.openChannel("exec");  
        ((ChannelExec) channel).setCommand(command);  
        channel.setInputStream(null);  
        ((ChannelExec) channel).setErrStream(System.err);  
        channel.connect();  
        long startTime1 = System.currentTimeMillis();  
        InputStream in1 = channel.getInputStream();  
        BufferedReader in2 = new BufferedReader(new InputStreamReader(in1, Charset.forName(charset)));  
        long idleCpuTime2 = 0, totalCpuTime2 = 0;   //分别为系统启动后空闲的CPU时间和总的CPU时间  
        while((line=in2.readLine()) != null){     
            if(line.startsWith("cpu")){  
                line = line.trim();   
                String[] temp = line.split("\\s+");   
                idleCpuTime2 = Long.parseLong(temp[4]);  
                for(String s : temp){  
                    if(!s.equals("cpu")){  
                        totalCpuTime2 += Long.parseLong(s);  
                    }  
                }  
                System.out.println("IdleCpuTime: " + idleCpuTime2 + ", " + "TotalCpuTime: " + totalCpuTime2);  
                break;    
            }                                 
        }   
        reader.close(); 
        channel.disconnect();  
        session.disconnect();  
        if(idleCpuTime1 != 0 && totalCpuTime1 !=0 && idleCpuTime2 != 0 && totalCpuTime2 !=0){  
            float cpuUsage = 1 - (float)(idleCpuTime2 - idleCpuTime1)/(float)(totalCpuTime2 - totalCpuTime1);  
            System.out.println("本节点CPU使用率为: " + cpuUsage);  
        } 
	}
}
