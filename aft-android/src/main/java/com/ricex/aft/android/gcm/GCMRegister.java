/**
 * 
 */
package com.ricex.aft.android.gcm;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ricex.aft.android.AFTPreferences;
import com.ricex.aft.android.register.PushFileRegister;

/** Utility class for Google Cloud Messaging registration
 * 
 *  Provides methods for checking if a device has google play services installed, checking if the device is registered,
 *  and performing a background registration if the device is not registered
 * 
 * @author Mitchell Caisse
 *
 */
public class GCMRegister {
	
	/** Resolution request for determing if a device has google play services */
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	/** Sender ID for GCM, project number */
	private static final String SENDER_ID = "439278995325";
	
	/** The tag to use when creating log entries */
	private static final String LOG_TAG = "PushFileGCMR";
	
	/** Check if the device currently has google play services installed
	 * 
	 * @return True if the device has google play services, false otherwise
	 */
	
	public static boolean checkGooglePlayServices(Activity activity) {
		int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		if (res != ConnectionResult.SUCCESS) {
			//user does not have GooglePlayServices installed
			if (GooglePlayServicesUtil.isUserRecoverableError(res)) {
				GooglePlayServicesUtil.getErrorDialog(res, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
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
	
	public static boolean checkRegistration(Context context) {
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
	
	public static String getRegistrationId(Context context) {
		String registrationId = AFTPreferences.getValue(AFTPreferences.PROPERTY_REG_ID);
		if (registrationId.isEmpty()) {
			Log.i(LOG_TAG, "Registration not found");
			return "";
		}
		
		//check if the app has been updated
		//if it has been updated, it might not work with the old registration id
		
		int registeredVersion = AFTPreferences.getIntValue(AFTPreferences.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		
		if (registeredVersion < currentVersion) {
			//the app was updated
			Log.i(LOG_TAG, "App version changed");
			return ""; // return empty to re-register
		}
		return registrationId;
		
	}
	
	/** Gets the application version of the currently running application
	 * 
	 * @param context The application context
	 * @return The application version
	 */
	
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		}
		catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	
	/** Launchers a AsyncTask to launch the background process to register the device with Google Cloud Messaging
	 * 
	 * @param context The context the app is running in
	 */
	
	public static void registerInBackground(final Context context) {
		
		Log.i(LOG_TAG, "Registering in background");
		new AsyncTask<Object, Object, String>() {
	
			protected String doInBackground(Object... params) {
				String msg = "";
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
				
				try {
					String registrationId = gcm.register(SENDER_ID);
					msg = registrationId;		
					
					Log.d(LOG_TAG, "GCM Just registered! Registration ID: " + registrationId);
					
					//send the registration ID to my server
					
					//persist the registrationid
					storeRegistrationId(context, registrationId);
				}
				catch (IOException e) {
					msg = "";
				}	
				return msg;
			}
			
			@Override
			protected void onPostExecute(String msg) {
				Log.i(LOG_TAG, "About to send registration to server: " + msg);
				if (!msg.isEmpty()) {
					sendRegistrationToServer(context);
				}
				else {
					Log.i(LOG_TAG, "Registration Failed");
				}
			}
			
		}.execute(null,null,null);
	}
	
	/** Sends the specified registration id to the server
	 */
	
	private static void sendRegistrationToServer(final Context context) {	
		new PushFileRegister(context).registerIfNeeded();
	}	
	
	/** Stores the given registration ID to the applications {@code SharedPreferences}
	 * 
	 * @param context The application context
	 * @param registrationId registration id to save
	 */
	
	private static void storeRegistrationId(Context context, String registrationId) {
		int appVersion = getAppVersion(context);
		Log.i(LOG_TAG, "Saving reistration id on app version" + appVersion);
		
		//update the preferences
		AFTPreferences.setValue(AFTPreferences.PROPERTY_REG_ID, registrationId);
		AFTPreferences.setIntValue(AFTPreferences.PROPERTY_APP_VERSION, appVersion);
	}
}
