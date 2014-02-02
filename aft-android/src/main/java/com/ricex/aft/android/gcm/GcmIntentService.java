/**
 * 
 */
package com.ricex.aft.android.gcm;

import android.R;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/** 
 * @author Mitchell Caisse
 *
 */
public class GcmIntentService extends IntentService {

	
	/** The tag for log entriies */
	private static final String LOG_TAG = "FilePushIntent";
	
	/**
	 * @param name
	 */
	public GcmIntentService() {
		super("PushFileGcmIntentService");
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		
		String messageType = gcm.getMessageType(intent);
		
		//if extras are not empty
		if (!extras.isEmpty()) {
			if (messageType.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)) {
				Log.i(LOG_TAG, "There was an issue sending a message: " + extras.toString());
			}
			else if (messageType.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)) {
				Log.i(LOG_TAG, "Deleted messages on server: " + extras.toString());
			}
			else if (messageType.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
				Log.i(LOG_TAG, "We received a message, might want to do something..." + extras.toString());
				showNotification();
			}
			else {
				Log.i(LOG_TAG, "Unreconized message type...");
			}
		}
		
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	/** Used to show a notification when we receive a GCM message
	 * 
	 */
	
	private void showNotification() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.stat_sys_warning);
		builder.setContentTitle("PushFile");
		builder.setContentText("Received a google cloud messaging message");
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1, builder.build()); // note that 1 is always unique
	}

}
