/**
 * 
 */
package com.ricex.aft.android.processor;

import java.util.List;

import android.R;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ricex.aft.android.requester.RequestRequester;
import com.ricex.aft.common.entity.Request;

/**
 *  Handles the reciept of a message from the GCM service
 * 
 * @author Mitchell Caisse
 * 
 */
public class MessageProcessor implements MediaScannerConnectionClient {

	/** The log tag for this class */
	private final String LOG_TAG = "PushFileMP";
	
	/** The thread that this processor is running in */
	private Thread thread;
	
	/** The context that this processor was launched from */
	private final Context context;
	
	/** The notification ID this message processor uses */
	private final int notificationId;
	
	/** The media scanner connection used to update external storage */
	private final MediaScannerConnection mediaScannerConnection;
	
	/** Creates a new message processor, and a thread for it to run in, then starts the thread
	 * 	@param context The context that this processor was launched from
	 */
	
	public MessageProcessor(Context context) {
		this.context = context;
		notificationId = (int)(Math.random() * 1000.0);
		mediaScannerConnection = new MediaScannerConnection(context, this);
	}
	
	/** Fetches the requests from the server and processes them. Does so in the background in an AsyncTask
	 * 
	 */
	
	public void process() {
		//connect to the media scanner, and use its callback to start the task
		if (mediaScannerConnection.isConnected()) {
			//media scanner is connected already. start the ASyncTask
			startTask();
		}
		else {
			//we are not connected, don't start the task
			mediaScannerConnection.connect();
		}
	}
	
	/** Creates and executes the AsyncTask to fetch the requests
	 * 
	 */
	
	private void startTask() {
		Log.i(LOG_TAG, "Process(): About to create the AsyncTask");
		new AsyncTask<Object, Object, Boolean>() {
			@Override
			protected Boolean doInBackground(Object... params) {	
				Log.i(LOG_TAG, "Process(): About to fetch the requests from the server");
				//fetch the requets from the server
				List<Request> newRequests = new RequestRequester(context).getNewRequestsForDevice();
				Log.i(LOG_TAG, "Process(): Fetched the requests");
				int failed = 0;
				int success = 0;
				//process each of the requests in series
				for (Request request: newRequests) {
					Log.i(LOG_TAG, "Process(): Processing a request");
					boolean res = new RequestProcessor(context, mediaScannerConnection, request).processRequest();
					if (res) {
						success ++;
					}
					else {
						failed ++;
					}
					Log.i(LOG_TAG, "Process(): Showing the notification");
					showNotification(success, failed);
				}
				
				//disconnect the media scanner
				mediaScannerConnection.disconnect();
				
				Log.i(LOG_TAG, "Process(): We done, returning true");
				return true;
			}
			
		}.execute(null,null,null);	
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

	/** Called when the Media Scanner is connected. Starts the ASyncTask to fetch the requests / files
	 * 
	 */
	
	public void onMediaScannerConnected() {
		startTask();		
	}


	public void onScanCompleted(String path, Uri uri) {
		
	}

}
