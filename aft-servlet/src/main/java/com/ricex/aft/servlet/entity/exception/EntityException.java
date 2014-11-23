/**
 * 
 */
package com.ricex.aft.servlet.entity.exception;


/** Entity Exception that is thrown by a controller when there is an issue
 * 		saving or updating an entity
 * 
 * @author Mitchell Caisse
 *
 */


public class EntityException extends Exception {
	
	/** Creates a new Entity Exception with the given message
	 * 
	 * @param msg The message of the exception
	 */
	public EntityException(String msg) {
		super(msg);
	}
	
	/** Creates a new Entity exception with the given message and cause
	 * 
	 * @param msg The message of the exception
	 * @param cause The cause of the exception
	 */
	public EntityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
