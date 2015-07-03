package com.ricex.aft.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ricex.aft.android.gcm.GCMRegister;
import com.ricex.aft.android.notifier.Notifier;
import com.ricex.aft.android.processor.MessageProcessor;
import com.ricex.aft.android.register.PushFileRegister;
import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.RequestCallback;
import com.ricex.aft.android.request.SessionContext;
import com.ricex.aft.android.request.user.LoginTokenRequest;
import com.ricex.aft.common.response.BooleanResponse;

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
		
		if (needSessionToken()) {
			
		}
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
		Notifier.getInstance().updateContext(this);
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
	
	/** Checks if we need a session token
	 * 
	 * @return True if we need a session token, false otherwise
	 */	
	private boolean needSessionToken() {
		SessionContext sessionContext = SessionContext.INSTANCE;
		return sessionContext.needSessionToken();
	}
	

	//TODO: Implement
	private void fetchSessionToken(final FetchSessionTokenListener listener) {
		new LoginTokenRequest("").executeAsync(new RequestCallback<BooleanResponse>() {

			@Override
			public void onSuccess(BooleanResponse results) {
				listener.onSuccess();				
			}

			@Override
			public void onFailure(AFTResponse<BooleanResponse> response) {
				listener.onError(response.getError());				
			}

			@Override
			public void onError(Exception e) {		
				listener.onError(e.getMessage());
			}
			
		});
	}
	
	
	private interface FetchSessionTokenListener {
		
		/** Called when the request to fetch the session token completed successfully
		 * 
		 */
		public void onSuccess();
		
		/** Called when there was an error fetching the session token
		 * 
		 * @param error String describing the error that occurred
		 */
		public void onError(String error);
		
		
	}
	
}
