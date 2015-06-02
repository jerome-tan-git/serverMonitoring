package com.jerome.validate;

import akka.actor.UntypedActor;

public class Worker extends UntypedActor{

	@Override
	public void onReceive(Object _msg) throws Exception {
		if (_msg instanceof String) {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " sleep end");
            System.out.println("接收的消息：" + _msg);
            // 返回一个消息
            this.getSender().tell("hello world", this.getSelf());
            System.out.println("sender path=" + this.getSender().path());
            getContext().stop(this.getSelf());
            System.out.println("|||{} has stop : " + this.getSelf().path());
        }
		
	}

}
