/**
 * 
 */
package com.ricex.aft.android.gcm;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ricex.aft.android.PushFile;
import com.ricex.aft.android.requester.DeviceRequester;

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
	
	/** Property for the registration id */
	private static final String PROPERTY_REG_ID = "registration_id";
	
	/** Property for application version */
	private static final String PROPERTY_APP_VERSION = "appVersion";
	
	/** Sender ID for GCM, project number */
	private static final String SENDER_ID = "439278995325";
	
	/** The tag to use when creating log entries */
	private static final String LOG_TAG = "GCMRegister";
	
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
	
	private static String getRegistrationId(Context context) {
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
	
	private static SharedPreferences getGCMPreferences(Context context) {
		return context.getSharedPreferences(PushFile.class.getSimpleName(), Context.MODE_PRIVATE);
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
		new AsyncTask<Object, Object, String>() {
	
			protected String doInBackground(Object... params) {
				String msg = "";
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
				
				try {
					String registrationId = gcm.register(SENDER_ID);
					msg = registrationId;
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
				if (!msg.isEmpty()) {
					sendRegistrationToServer(context, msg);
				}
				else {
					Log.i(LOG_TAG, "Registration Failed");
				}
			}
			
		}.execute(null,null,null);
	}
	
	/** Sends the specified registration id to the server
	 * 
	 * @param registrationId The registration id of the device
	 */
	
	private static void sendRegistrationToServer(final Context context, final String registrationId) {	
		new AsyncTask<Object, Object, Boolean>() {

			@Override
			protected Boolean doInBackground(Object... params) {				
				boolean res = new DeviceRequester(context).registerDevice(registrationId);
				Log.i(LOG_TAG, "Device Registration returned: " + res);
				return res;				
			}
			
		}.execute(null,null,null);

	}	
	
	/** Stores the given registration ID to the applications {@code SharedPreferences}
	 * 
	 * @param context The application context
	 * @param registrationId registration id to save
	 */
	
	private static void storeRegistrationId(Context context, String registrationId) {
		SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(LOG_TAG, "Saving reistration id on app version" + appVersion);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putString(PROPERTY_REG_ID, registrationId);
		prefsEditor.putInt(PROPERTY_APP_VERSION, appVersion);
		prefsEditor.commit();		
	}
}
