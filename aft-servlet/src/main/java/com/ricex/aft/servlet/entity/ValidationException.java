/**
 * 
 */
package com.ricex.aft.servlet.entity;


/** Validation Exception that is thrown by a controller when there is an issue
 * 		saving or updating an entity, because the input is not valid
 * 
 * @author Mitchell Caisse
 *
 */


public class ValidationException extends Exception {
	
	/** Creates a new Validation Exception with the given message
	 * 
	 * @param msg The message of the exception
	 */
	public ValidationException(String msg) {
		super(msg);
	}
	
	/** Creates a new Validation exception with the given message and cause
	 * 
	 * @param msg The message of the exception
	 * @param cause The cause of the exception
	 */
	public ValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
