package com.ricex.aft.servlet.auth;

/** Thrown if an AFT Authorization Init header is in an invalid format
 * 
 * @author Mitchell Caisse
 *
 */

public class AFTAuthorizationInitHeaderInvalid extends AFTAuthorizationInvalidException{

	private static final long serialVersionUID = 4244351587663421718L;

	/** Creates a AuthorizationInitHeaderInvalid with the specified message
	 * 
	 * @param msg The detail message
	 */
	public AFTAuthorizationInitHeaderInvalid(String msg) {
		super(msg);
	}
	
	/** Creates a AuthorizationInitHeaderInvalid with the specified message and root cause
	 * 
	 * @param msg The detail message
	 * @param t The root cause
	 */
	public AFTAuthorizationInitHeaderInvalid(String msg, Throwable t) {
		super(msg, t);
	}

}
