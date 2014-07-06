/**
 * 
 */
package com.ricex.aft.servlet.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/** Base Controller class 
 * @author Mitchell Caisse
 *
 */
public abstract class BaseController {

	
	/** Returns the username of the currently logged in user
	 * 
	 * @return The username of the user, or empty string if none
	 */
	
	protected String getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			User user = (User)auth.getPrincipal();
			return user.getUsername();
		}
		return "";
	}
}
