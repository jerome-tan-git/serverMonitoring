package com.jerome.validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import com.jerome.config.Config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.OnComplete;
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

	public StartValidate(Config _conf) {
		system = ActorSystem.create("test");
		this.conf = _conf;
		child = system.actorOf(new RoundRobinPool(this.conf.getThreadCount())
				.props(Props.create(Worker.class)), "works");
		timeout = new Timeout(Duration.create(20, "seconds"));
	}

	public void startValidate() throws Exception {
		ExecutionContext ec = system.dispatcher();
		List<Future<String>> frs = new ArrayList<Future<String>>();
		for (int i = 0; i < 10; i++) {
			Future f1 = akka.pattern.Patterns.ask(child, "msg: " + i, timeout);
			frs.add(f1);
		}
		Future<Iterable<String>> future = Futures.sequence(frs,
				this.system.dispatcher());

		Future<String> fr = future.map(new Mapper<Iterable<String>, String>() {
			@Override
			public String apply(Iterable<String> parameter) {
				String result = "";
				for (String s : parameter) {
					System.out.println("-->: " + s);
					result += s + " ";
				}
				return result;
			}
		}, ec);
		System.out.println(Await.result(fr, Duration.create(30, "s")));
		fr.onSuccess(new OnSuccess<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("f2·µ»Ø½á¹û:" + result);
            }
        }, ec);
		
		 try {
		 Thread.sleep(5000);
		 } catch (InterruptedException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		// ExecutionContext ec = system.dispatcher();
		// for(int i=0;i<1;i++)
		// {
		// Future f = Patterns.ask(child, "message: " + i, timeout);
		// frs.add(f);
		// }
		// System.out.println("x");
		// Future<Iterable<String>> future = Futures.sequence(frs,
		// this.system.dispatcher());
		// Future<String> fr = future.map(new Mapper<Iterable<String>, String>()
		// {
		// @Override
		// public String apply(Iterable<String> parameter) {
		// String result = "";
		// for (String s : parameter) {
		// System.out.println("-->: " + s);
		// result += s + " ";
		// }
		// return result;
		// }
		// }, ec);
		//
		//
		//
		// try {
		// System.out.println(Await.result(fr, Duration.create(10, "s")));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// try {
		// Await.result(frs, Duration.create(20, "s"));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Future f1 = akka.pattern.Patterns.ask(child, "1,50000", timeout);
		// Future f2 = akka.pattern.Patterns.ask(child, "~~~", timeout);
		// frs.add(f1);
		// frs.add(f2);
		// Future<Iterable<String>> future = Futures.sequence(frs,
		// this.system.dispatcher());
		// try {
		// Await.result(future, Duration.create(0, "s"));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Future<Double> fr = future.map(arg0, arg1)

		// Future<Object> future = Patterns.ask(child, "are you ready?",
		// timeout);
		// String result= null;
		// try {
		// result = (String) Await.result(future, timeout.duration());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(result);
		this.shutdown();
	}

	public void shutdown() {
		this.system.shutdown();
	}

	public static void main(String[] args) throws Exception {
		StartValidate validator = new StartValidate(
				ReadConfig.readConfig("./config.json"));
		validator.startValidate();
	}
}
