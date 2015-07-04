package com.ricex.aft.android;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ricex.aft.android.auth.AccountActivity;
import com.ricex.aft.android.gcm.GCMRegister;
import com.ricex.aft.android.notifier.Notifier;
import com.ricex.aft.android.processor.MessageProcessor;
import com.ricex.aft.android.register.PushFileRegister;
import com.ricex.aft.android.request.AbstractRequestCallback;
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

	/** Result code used when requesting a login */
	private static final int REQUEST_LOGIN_CODE = 343562;
	
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
			fetchSessionToken();
		}
		else {
			onFetchSessionToken();
		}
	}
	
	/** Called when we have successfully fetched a session token
	 * 
	 */
	private void onFetchSessionToken() {
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
	

	/**
	 *  Fetch a session token from the server.
	 *  
	 *  If we do not have a valid auth token stored. Prompt the user to login.
	 *  
	 *  Calls onFetchSessionToken() when we have fetched the token
	 */
	private void fetchSessionToken() {
		String authToken = AFTPreferences.getValue(AFTPreferences.PROPERTY_AUTH_TOKEN);
		if (StringUtils.isEmpty(authToken)) {
			//we don't have a username or authtoken. need to get both before we can log in
			Intent intent = new Intent(this, AccountActivity.class);
			startActivityForResult(intent, REQUEST_LOGIN_CODE);
		}
		else {
			//we have an auth token. lets login
			
			new LoginTokenRequest(authToken).executeAsync(new AbstractRequestCallback<BooleanResponse>() {
				public void onSuccess(BooleanResponse results) {
					onFetchSessionToken();
				}
			});
			
		}
	
	}	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_LOGIN_CODE:
			
				if (resultCode == RESULT_OK) {
					String username = data.getStringExtra(AccountActivity.RES_ACCOUNT_NAME);
					String authToken = data.getStringExtra(AccountActivity.RES_AUTH_TOKEN);
					
					AFTPreferences.setValue(AFTPreferences.PROPERTY_USERNAME, username);
					AFTPreferences.setValue(AFTPreferences.PROPERTY_AUTH_TOKEN, authToken);
					
					//since we logged in, we will have a session token already in the context.
					onFetchSessionToken();
				}
				else {
					Log.w(LOG_TAG, "Could not receive auth token from user login!");
				}
				
			break;
		}
	}
	
}
