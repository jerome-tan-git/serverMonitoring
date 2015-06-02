package com.jerome.validate;


import java.io.IOException;

import com.jerome.config.Config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

public class StartValidate {
	private ActorRef child;
	private int processCount;
	private Config conf;
	public StartValidate(Config _conf)
	{
		 ActorSystem system = ActorSystem.create("test");
		 this.conf = _conf;
		 child = system.actorOf(new RoundRobinPool(this.conf.getThreadCount()).props(Props.create(Worker.class)), "works");
	}
	
	public void startValidate()
	{
		
	}
	public static void main(String[] args) throws IOException
	{
		StartValidate validator = new StartValidate(ReadConfig.readConfig("./config.json"));
		validator.startValidate();
	}
}
