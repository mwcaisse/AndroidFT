package com.ricex.aft.android;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ricex.aft.android.gcm.GCMRegister;
import com.ricex.aft.android.processor.MessageProcessor;
import com.ricex.aft.android.register.PushFileRegister;
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
		
		initializeProperties();
		checkRegistration();
		checkForRequests();		
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
	
	/** Initializes the properties to use this as a context
	 * 
	 */
	private void initializeProperties() {
		AFTPreferences.setContext(this);
	}	
	
	/** Checks if we are registered for GCM + PushFile
	 * 
	 */
	private void checkRegistration() {
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
	}
	
	/** Checks if there are any pending requests for this device
	 * 
	 */
	private void checkForRequests() {
		new MessageProcessor(this).process();
	}
	
	/** Will check if we are registered for PushFile, and register if we are not
	 * 
	 *  Performs the Registration + check in the background
	 */
	
	private void checkPushFileRegistration() {		
		new PushFileRegister(this).registerIfNeeded();
	}
	
}
