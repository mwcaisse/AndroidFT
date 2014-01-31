/**
 * 
 */
package com.ricex.aft.android.request.listener;

import android.util.Log;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * @author Mitchell Caisse
 *
 */
public class RegistrationRequestListener implements RequestListener<Long> {

	private static final String LOG_TAG = "RRL";
	
	/**
	 * {@inheritDoc}
	 */

	public void onRequestFailure(SpiceException e) {
		Log.i(LOG_TAG, "Request Failed: " + e.getMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void onRequestSuccess(Long res) {
		Log.i(LOG_TAG, "Request Success: " + res);		
	}
	

}
