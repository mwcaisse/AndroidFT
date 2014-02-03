/**
 * 
 */
package com.ricex.aft.servlet.gcm;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** Executor for sending messages to the GCM servers
 * 
 * @author Mitchell Caisse
 *
 */
public enum MessageExecutor {
	
	/** The singleton instance */
	INSTANCE;
	
	/** The executor that will be used to execute sync messages */
	private ScheduledExecutorService executor;
	
	private MessageExecutor() {
		//default executor
		executor = new ScheduledThreadPoolExecutor(4);
	}
	
	
	/** Executes the given command now
	 * 
	 * @param command The command to execute
	 */
	
	public void executeNow(Runnable command) {
		executor.execute(command);
	}
	
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		return executor.schedule(command,delay,unit);
	}	

	/**
	 * @return the executor
	 */
	
	public ScheduledExecutorService getExecutor() {
		return executor;
	}

	/**
	 * @param executor the executor to set
	 */
	
	public void setExecutor(ScheduledExecutorService executor) {
		this.executor = executor;
	}
	
	
}
