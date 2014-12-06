package com.ricex.aft.servlet.auth;

import org.springframework.security.core.Authentication;

public interface Token {

	/** Gets the id of this token
	 * 
	 *  @return The id of this token
	 */
	public String getTokenId();
	
	/** The authentication the token represents
	 * 
	 * @return
	 */
	public Authentication getAuthentication();
	
	/** Determines if the token is expired or not
	 * 
 	 * @return True if expired, false otherwise
	 */
	public boolean isExpired();
	
	
}
