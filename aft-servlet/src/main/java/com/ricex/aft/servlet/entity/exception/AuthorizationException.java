package com.ricex.aft.servlet.entity.exception;

/** Authorization Exception thrown by the controller when they user tries to perform an
 * 		action that they are not authorized to do
 * 
 * @author Mitchell Caisse
 *
 */

public class AuthorizationException extends Exception {
	
	/** Creates a new Authorization Exception with the given message
	 * 
	 * @param msg The message of the exception
	 */
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	/** Creates a new Authorization exception with the given message and cause
	 * 
	 * @param msg The message of the exception
	 * @param cause The cause of the exception
	 */
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
