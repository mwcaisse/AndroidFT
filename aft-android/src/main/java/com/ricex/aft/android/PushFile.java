package com.ricex.aft.android;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ricex.aft.android.gcm.GCMRegister;
import com.ricex.aft.android.processor.MessageProcessor;
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
		
		Log.i(LOG_TAG, "On Create");
		
		//check if google play services is installed, if not quit.
		if (!GCMRegister.checkGooglePlayServices(this)) {
			finish();
		}
		
		//lets see if we are registered
		if (!GCMRegister.checkRegistration(getApplicationContext())) {
			// not registered, register in the background
			GCMRegister.registerInBackground(getApplicationContext());
		} 
		else {
			//we were registered with GCM, check if we are registered with the PushFile server
			checkPushFileRegistration();			
		}	
		
		//why don't we check for some updates right now?
		new MessageProcessor(this).process();
		
	}
	
	/** Will check if we are registered for PushFile, and register if we are not
	 * 
	 *  Performs the Registration + check in the background
	 */
	
	public void checkPushFileRegistration() {		
		final DeviceRequester deviceRequester = new DeviceRequester(this);
		final Context context = this;
		
		new AsyncTask<Object, Object, Boolean>() {

			@Override
			protected Boolean doInBackground(Object... params) {				
				boolean res = deviceRequester.isRegistered();
				if (!res) {
					Log.i(LOG_TAG, "Device is registered for GCM, but not PushFile, registering");
					String registrationId = GCMRegister.getRegistrationId(context);
					Log.d(LOG_TAG, "GCM Registration ID: " + registrationId);
					res = deviceRequester.registerDevice(registrationId);
					if (!res) {
						Log.w(LOG_TAG, "Failed to register device");
					}
				}
				Log.i(LOG_TAG, "Device Registration returned: " + res);
				return res;				
			}
			
		}.execute(null,null,null);
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
