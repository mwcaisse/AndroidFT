/**
 * 
 */
package com.ricex.aft.android.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ricex.aft.android.notifier.Notifier;
import com.ricex.aft.android.notifier.RequestProcessedNotification;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.android.request.request.GetNewRequestsForDeviceRequest;
import com.ricex.aft.common.entity.Request;

/**
 *  Handles the reciept of a message from the GCM service
 * 
 * @author Mitchell Caisse
 * 
 */
public class MessageProcessor {

	/** The log tag for this class */
	private final String LOG_TAG = "PushFileMP";
	
	/** The thread that this processor is running in */
	private Thread thread;
	
	/** The context that this processor was launched from */
	private final Context context;
	
	/** The notification ID this message processor uses */
	private final int notificationId;
	
	/** Creates a new message processor, and a thread for it to run in, then starts the thread
	 * 	@param context The context that this processor was launched from
	 */
	
	public MessageProcessor(Context context) {
		this.context = context;
		notificationId = (int)(Math.random() * 1000.0);

	}
	
	/** Fetches the requests from the server and processes them. Does so in the background in an AsyncTask
	 * 
	 */
	
	public void process() {
		Log.i(LOG_TAG, "Process(): About to create the AsyncTask");
		new AsyncTask<Object, Object, Boolean>() {
			@Override
			protected Boolean doInBackground(Object... params) {	
				Log.i(LOG_TAG, "Process(): About to fetch the requests from the server");
				//fetch the requets from the server
				
				List<Request> newRequests = new ArrayList<Request>();
				
				try {		
					newRequests = Arrays.asList(new GetNewRequestsForDeviceRequest().execute());
				}
				catch (RequestException e) {
					Log.e(LOG_TAG, "Process(): error requesting requests from server.", e);						
				}
				
				Log.i(LOG_TAG, "Process(): Fetched the requests");
				int failed = 0;
				int success = 0;
				//process each of the requests in series
				for (Request request: newRequests) {
					Log.i(LOG_TAG, "Process(): Processing a request");
					boolean res = new RequestProcessor(context, request).processRequest();
					if (res) {
						success ++;
					}
					else {
						failed ++;
					}
					Log.i(LOG_TAG, "Process(): Showing the notification");
					showNotification(success, failed);
				}				
				Log.i(LOG_TAG, "Process(): We done, returning true");
				return true;
			}
			
		}.execute(null,null,null);	
	}
	
	
	
	/**  Show the notification of how many requests were sucessful and how many failed
	 * 
	 */
	
	private void showNotification(int successful, int error) {
		Notifier notifier = Notifier.getInstance();
		notifier.displayNotification(new RequestProcessedNotification(successful, error));
	}

}
