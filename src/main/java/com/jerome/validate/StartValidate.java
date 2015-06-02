package com.jerome.validate;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import com.jerome.config.Config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;

public class StartValidate {
	private ActorRef child;
	private int processCount;
	private Config conf;
	private Timeout timeout;
	private ActorSystem system;
	public StartValidate(Config _conf)
	{
		 system = ActorSystem.create("test");
		 this.conf = _conf;
		 child = system.actorOf(new RoundRobinPool(this.conf.getThreadCount()).props(Props.create(Worker.class)), "works");
		 timeout = new Timeout(Duration.create(5, "seconds"));
	}
	
	public void startValidate()
	{
		List<Future<String>> frs = new ArrayList<Future<String>>();
		Future f1 = akka.pattern.Patterns.ask(child, "1,50000", timeout);
		Future f2 = akka.pattern.Patterns.ask(child, "~~~", timeout);
		frs.add(f1);
        frs.add(f2);
        Future<Iterable<String>> future = Futures.sequence(frs, this.system.dispatcher());
        Future<Double> fr = future.map(arg0, arg1)


    }
//		 Future<Object> future = Patterns.ask(child, "are you ready?", timeout);
//		 String result= null;
//		try {
//			result = (String) Await.result(future, timeout.duration());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	     System.out.println(result);
	     this.shutdown();
	}
	
	
	public void shutdown()
	{
		this.system.shutdown();
	}
	public static void main(String[] args) throws IOException
	{
		StartValidate validator = new StartValidate(ReadConfig.readConfig("./config.json"));
		validator.startValidate();
	}
}
