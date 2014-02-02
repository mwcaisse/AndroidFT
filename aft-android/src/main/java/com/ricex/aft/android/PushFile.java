package com.ricex.aft.android;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.ricex.aft.android.gcm.GCMRegister;
import com.ricex.aft.android.requester.DeviceRequester;

/** Main Activity for PushFile.
 *
 * Will allow the user to customize any settings, and start / register the initial services
 *
 * @author Mitchell Caisse
 *
 */

public class PushFile extends Activity {

	
	private static final String LOG_TAG = "PushFile";
	
	/**
	 *  {@inheritDoc}
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_file);		
		//set the context of the device requester
		DeviceRequester.INSTANCE.setContext(getApplicationContext());
		
		//check if google play services is installed, if not quit.
		if (!GCMRegister.checkGooglePlayServices(this)) {
			finish();
		}
		//lets see if we are registered
		if (!GCMRegister.checkRegistration(getApplicationContext())) {
			// not registered, register in the background
			GCMRegister.registerInBackground(getApplicationContext());
		}
		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	
	protected void onResume() {
		super.onResume();
		//check if google play services is installed, if not quit.
		if (!GCMRegister.checkGooglePlayServices(this)) {
			finish();
		}
	}
}
