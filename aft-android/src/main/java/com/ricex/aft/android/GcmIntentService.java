/**
 * 
 */
package com.ricex.aft.android;

import android.app.IntentService;
import android.content.Intent;

/** 
 * @author Mitchell Caisse
 *
 */
public class GcmIntentService extends IntentService {

	/**
	 * @param name
	 */
	public GcmIntentService(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {

	}

}
