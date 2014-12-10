package com.ricex.aft.android.requester;

public enum SecurityContext {
	
	/** The singleton instance of this security context */
	INSTANCE;
	
	/** The current Authentication Token */
	private String aftToken;		
	
	
	/** Creates a new Security Context
	 * 
	 */
	private SecurityContext() {
		aftToken = null;
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
}
