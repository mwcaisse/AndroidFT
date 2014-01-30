/**
 * 
 */
package com.ricex.aft.android.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/** Receives broadcasts from Google Cloud Messaging
 * @author Mitchell Caisse
 *
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
		startWakefulService(context, intent.setComponent(comp));
		setResultCode(Activity.RESULT_OK);		
	}

}
