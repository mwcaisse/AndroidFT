package com.ricex.aft.android.register;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ricex.aft.android.gcm.GCMRegister;
import com.ricex.aft.android.request.device.CheckRegistrationRequest;
import com.ricex.aft.android.request.device.RegisterDeviceRequest;
import com.ricex.aft.android.request.exception.RequestException;

/** Registers this device for the PushFile service if it is not already registered
 * 
 * @author Mitchell Caisse
 *
 */

public class PushFileRegister {

	/** The log tag to use when logging */
	private static final String LOG_TAG = "PushFileRegister";
	
	/** The context of this register */
	private final Context context;
	
	/*** Creates a new Push File reigster
	 * 
	 * @param context The context
	 */
	public PushFileRegister(Context context) {
		this.context = context;
	}
	
	/** Checks if this device is already registered with the PushFile server. If it is not registered it
	 * 		registers the device, otherwise it does nothing
	 */
	
	public void registerIfNeeded() {
		new AsyncTask<Object, Object, Boolean>() {

			@Override
			protected Boolean doInBackground(Object... params) {
				//call the web service, and check if we are registered
				try {	
					boolean registered = new CheckRegistrationRequest().execute().getValue();
					if (!registered) {
						Log.i(LOG_TAG, "Device is not registered with PushFile server, registering now.");
						String registrationId = GCMRegister.getRegistrationId(context);
						//boolean res = deviceRequester.registerDevice(registrationId);
						long res = new RegisterDeviceRequest(registrationId).execute().getValue();
						if (res < 0) {
							Log.e(LOG_TAG, "Failed to register this device with the PushFile server");
						}
					}
					return true;
				}
				catch (RequestException e) {
					Log.e(LOG_TAG, "Failed to register this device with the PushFile server", e);
					return false;
				}
			}
			
		}.execute();
	}	
}
