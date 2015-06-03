package com.jerome.validate;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.jerome.config.Config;
import com.jerome.config.ProcessObj;
import com.jerome.config.ServerObj;

public class Validate {
	public static BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
	private Config config;
	public static CountDownLatch end;
	public Validate(Config _config)
	{
		this.config = _config;
		for(ProcessObj po : config.getProcessList())
		{
			Validate.queue.add(po);
		}
		
		for(ServerObj so: config.getServerList())
		{
			Validate.queue.add(so);
		}
		
		end = new CountDownLatch(Validate.queue.size());
		
		ExecutorService service = Executors.newFixedThreadPool(this.config.getThreadCount());
		for(int i=0;i<this.config.getThreadCount(); i++)
		{
			ValidateThread vt = new ValidateThread();
			service.submit(vt);
		}
		try {
			end.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.shutdown();
		
	}
	public static void main(String[] args) throws IOException
	{
		  new Validate(ReadConfig.readConfig("./config.json"));
	}
}
