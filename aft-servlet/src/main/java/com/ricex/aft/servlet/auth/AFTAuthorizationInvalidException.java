package com.ricex.aft.servlet.auth;

import org.springframework.security.core.AuthenticationException;

/** Base class for Authorization exceptions caused by an invalid AFT Authorization initizatlion header or token
 * 
 * @author Mitchell Caisse
 *
 */

public abstract class AFTAuthorizationInvalidException extends AuthenticationException {

	private static final long serialVersionUID = 3579593545130954451L;

	
	public AFTAuthorizationInvalidException(String msg) {
		super(msg);
	}
	
	public AFTAuthorizationInvalidException(String msg, Throwable t) {
		super(msg, t);
	}

}
