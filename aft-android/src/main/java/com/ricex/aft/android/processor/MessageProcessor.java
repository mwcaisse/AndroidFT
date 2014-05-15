/**
 * 
 */
package com.ricex.aft.android.processor;

import java.util.List;

import android.R;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

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
	
	/** The notification ID this message processor uses */
	private final int notificationId;
	
	/** Creates a new message processor, and a thread for it to run in, then starts the thread
	 * 	@param context The context that this processor was launched from
	 */
	
	public MessageProcessor(Context context) {
		this.context = context;
		notificationId = (int)(Math.random() * 1000.0);
		thread = new Thread(this);
		thread.start();
	}
	
	/** Runs the thread, creates and fetches the requests from the Server
	 * 
	 */
	
	public void run() {
		//fetch the requets from the server
		List<Request> newRequests = new RequestRequester(context).getNewRequestsForDevice();
		int failed = 0;
		int success = 0;
		//process each of the requests in series
		for (Request request: newRequests) {
			boolean res = new RequestProcessor(context, request).processRequest();
			if (res) {
				success ++;
			}
			else {
				failed ++;
			}
			
			showNotification(success, failed);
		}
	}
	
	
	
	/** Used to show a notification when we receive a GCM message
	 * 
	 */
	
	private void showNotification(int successful, int error) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(R.drawable.stat_notify_sync);
		builder.setContentTitle("PushFile Request");
		
		String notificationMessage = "";
		if (successful > 0) {
			notificationMessage += "Sucessfully received " + successful + " requests \n";
		}
		if (error > 0) {
			//only add the error message if there were errors
			notificationMessage += "Errors receiving " + error + " requests";
		}		
		builder.setContentText(notificationMessage);
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(notificationId, builder.build()); // note that 1 is always unique
	}

}
