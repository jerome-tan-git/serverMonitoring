package com.jerome.validate;

import com.jerome.config.ProcessObj;
import com.jerome.config.ServerObj;

public class ValidateThread implements Runnable {

	public void run() {
		Object task = null;

		while ((task = Validate.queue.poll()) != null) {
			Validate.end.countDown();
			if (task instanceof ProcessObj) {
				System.out.println(((ProcessObj) task).getProcessName());
			} else if (task instanceof ServerObj) {
				System.out.println(((ServerObj) task).getServerAddress());
			}
			
		}

	}

}
