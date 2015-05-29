package com.ricex.aft.android.requester;

import com.ricex.aft.common.response.BooleanResponse;

import android.content.Context;

public class UserRequester extends AbstractRequester {

	/** Creates a new UserRequester with the specified application context
	 * 
	 * @param context The application context
	 */
	public UserRequester(Context context) {
		super(context);
	}

	
	/*
	 * 	public boolean isRegistered() {
		String deviceUid = getDeviceUID();		
		BooleanResponse res = getForObject(serverAddress + "device/isRegistered/{deviceUid}", BooleanResponse.class, deviceUid);		
		return res.getValue();
	}
	
	 */
	
	public boolean areValidCredentials(String username, String password) {
		return false;
	}
}
