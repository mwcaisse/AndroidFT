package com.ricex.aft.android;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/** Main Activity for PushFile.
 *
 * Will allow the user to customize any settings, and start / register the initial services
 *
 * @author Mitchell Caisse
 *
 */

public class PushFile extends Activity {

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	
	/** Sender ID for GCM, project number */
	private static final String SENDER_ID = "";
	
	private static final String LOG_TAG = "PushFile";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_transfer);
		
		//check if google play services is installed, if not quit.
		if (!checkGooglePlayServices()) {
			finish();
		}
		//lets see if we are registered
		if (!checkRegistration(getApplicationContext())) {
			// not registered, register in the background
			registerInBackground(getApplicationContext());
		}
	}
	
	protected void onResume() {
		super.onResume();
		//check if google play services is installed, if not quit.
		if (!checkGooglePlayServices()) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_transfer, menu);
		return true;
	}
	
	/** Check if the device currently has google play services installed
	 * 
	 * @return True if the device has google play services, false otherwise
	 */
	
	public boolean checkGooglePlayServices() {
		int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (res != ConnectionResult.SUCCESS) {
			//user does not have GooglePlayServices installed
			if (GooglePlayServicesUtil.isUserRecoverableError(res)) {
				GooglePlayServicesUtil.getErrorDialog(res, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else {
				Log.i(LOG_TAG, "This device is not supported");

			}
			return false;
		}
		
		return true;
	}
	
	/** Checks to see if this app is registered for Google Cloud Messaging
	 *  
	 *  @context The context of the application
	 *  @return True if the device is registered, false if it is not registered
	 */
	
	public boolean checkRegistration(Context context) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String registrationId = getRegistrationId(context);
		if (registrationId.isEmpty()) {
			return false;
		}
		return true;
		
	}
	
	/** Gets the current registration ID for this application in Google Cloud Messaging
	 * 
	 * @param context The application context
	 * @return The registration ID, or empty string if the device is not registered.
	 */
	
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID,  "");
		if (registrationId.isEmpty()) {
			Log.i(LOG_TAG, "Registration not found");
			return "";
		}
		
		//check if the app has been updated
		//if it has been updated, it might not work with the old registration id
		
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		
		if (registeredVersion < currentVersion) {
			//the app was updated
			Log.i(LOG_TAG, "App version changed");
			return ""; // return empty to re-register
		}
		return registrationId;
		
	}
	
	/** Gets the {@code SharedPreferences} for this application
	 * 
	 * @param context The application context
	 * @return The {@code SharedPreferences} for this application
	 */
	
	private SharedPreferences getGCMPreferences(Context context) {
		return getSharedPreferences(PushFile.class.getSimpleName(), Context.MODE_PRIVATE);
	}
	
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		}
		catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	
	private void registerInBackground(final Context context) {
		new AsyncTask<Object, Object, String>() {
	
			protected String doInBackground(Object... params) {
				String msg = "";
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
				
				try {
					String registrationId = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + registrationId;
					//send the registration ID to my server
					
					//persist the registrationid
					storeRegistrationId(context, registrationId);
				}
				catch (IOException e) {
					msg = "Error: " + e.getMessage();
				}				
				return msg;
			}
			
			@Override
			protected void onPostExecute(String msg) {
				Log.i(LOG_TAG, "Registration results: " + msg);
			}
			
		}.execute(null,null,null);
	}
	
	/** Stores the given registration ID to the applications {@code SharedPreferences}
	 * 
	 * @param context The application context
	 * @param registrationId registration id to save
	 */
	
	private void storeRegistrationId(Context context, String registrationId) {
		SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(LOG_TAG, "Saving reistration id on app version" + appVersion);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putString(PROPERTY_REG_ID, registrationId);
		prefsEditor.putInt(PROPERTY_APP_VERSION, appVersion);
		prefsEditor.commit();
		
	}

}
