/**
 * 
 */
package com.ricex.aft.android;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
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
			}
			else {
				Log.i(LOG_TAG, "Unreconized message type...");
			}
		}
		
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

}
