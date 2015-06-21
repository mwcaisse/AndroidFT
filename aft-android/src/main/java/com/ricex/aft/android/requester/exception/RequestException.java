package com.ricex.aft.android.requester.exception;

/** Exception representing an invalid Request
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestException extends Exception {	

	private static final long serialVersionUID = 1L;

	public RequestException(String error) {
		super(error);
	}
	
	public RequestException(String error, Exception cause) {
		super(error, cause);
	}
}
