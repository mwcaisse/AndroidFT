/**
 * 
 */
package com.ricex.aft.android.processor;

import java.util.List;

import android.content.Context;

import com.ricex.aft.android.requester.RequestRequester;
import com.ricex.aft.common.entity.Request;

/**
 *  Handles the reciept of a message from the GCM service
 * 
 * @author Mitchell Caisse
 * 
 * TODO: Possibly create an internal class for the Runnable?
 */
public class MessageProcessor implements Runnable {

	/** The thread that this processor is running in */
	private Thread thread;
	
	/** The context that this processor was launched from */
	private final Context context;
	
	/** Creates a new message processor, and a thread for it to run in, then starts the thread
	 * 	@param context The context that this processor was launched from
	 */
	
	public MessageProcessor(Context context) {
		this.context = context;
		thread = new Thread(this);
		thread.start();
	}
	
	/** Runs the thread, creates and fetches the requests from the Server
	 * 
	 */
	
	public void run() {
		//fetch the requets from the server
		List<Request> newRequests = new RequestRequester(context).getNewRequestsForDevice();
		
		//process each of the requests in series
		for (Request request: newRequests) {
			boolean res = new RequestProcessor(context, request).processRequest();
			//TODO: Implement the notification
			//Should create / update the notification here..
		}
	}
}
