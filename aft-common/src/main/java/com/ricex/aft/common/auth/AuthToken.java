package com.ricex.aft.common.auth;

import java.io.Serializable;

public class AuthToken implements Serializable {
	
	/** The authentication token */
	private String authenticationToken;
	
	/** The uid of the device the authentication request is from */
	private String deviceUid;
	
	/** Creates a new instance of AuthToken
	 * 
	 */
	public AuthToken() {
		
	}

	/**
	 * @return the authenticationToken
	 */
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	/**
	 * @param authenticationToken the authenticationToken to set
	 */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	/**
	 * @return the deviceUid
	 */
	public String getDeviceUid() {
		return deviceUid;
	}

	/**
	 * @param deviceUid the deviceUid to set
	 */
	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}

	
	
}
