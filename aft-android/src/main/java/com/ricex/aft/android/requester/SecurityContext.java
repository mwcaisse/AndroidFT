package com.ricex.aft.android.requester;

import android.util.Base64;



public enum SecurityContext {
	
	/** The singleton instance of this security context */
	INSTANCE;
	
	/** The current Authentication Token */
	private String aftToken;	
	
	/** The credentials to use for authorization */
	private String credentials;
	
	
	/** Creates a new Security Context
	 * 
	 */
	private SecurityContext() {
		aftToken = null;
		updateCredentials("testuser", "password");
	}
	
	/** Determines if we need an Authentication token or not
	 * 
	 * @return True if we need a token, false otherwise
	 */
	public boolean needAuthenticationToken() {
		return (aftToken == null || aftToken.isEmpty());
	}
	
	/** Invalidates the AFT Token
	 * 
	 */
	public void invalidateAFTToken() {		
		aftToken = null;
	}
	
	/** Updates the credentials stored
	 * 
	 * @param username The username
	 * @param password The password
	 */
	public void updateCredentials(String username, String password) {
		String tmp = username + "|" + password;
		credentials = Base64.encodeToString(tmp.getBytes(), Base64.NO_WRAP);
	}

	/**
	 * @return the aftToken
	 */
	public String getAftToken() {
		return aftToken;
	}

	/**
	 * @param aftToken the aftToken to set
	 */
	public void setAftToken(String aftToken) {
		this.aftToken = aftToken;
	}

	/**
	 * @return the credentials
	 */
	public String getCredentials() {
		return credentials;
	}
	
	
}
