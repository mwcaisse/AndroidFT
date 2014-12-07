package com.ricex.aft.common.auth;

/** AFT Authentication class that holds the  Request Headers used
 * 
 * @author Mitchell Caisse
 *
 */

public class AFTAuthentication {

	/** The Request Header containing the user's authentication details as a request for a Token */
	public static final String AFT_AUTH_INIT_HEADER = "AFT_AUTH_INIT";	
	
	/** The Request Header containing the AFT Authentication Token */
	public static final String AFT_AUTH_TOKEN_HEADER = "AFT_AUTH_TOKEN";
}
