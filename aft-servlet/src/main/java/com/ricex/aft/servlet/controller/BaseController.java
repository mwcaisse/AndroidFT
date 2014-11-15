package com.ricex.aft.servlet.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ricex.aft.servlet.entity.User;

/** Base Controller for all controllers
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class BaseController {

	/** Creates a new Base Controller
	 * 
	 */
	protected BaseController() {
		
	}
	
	/** Returns the Spring representation of the currently logged in user
	 * 
	 * @return The currently logged in user
	 */
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (isAuthenticated()) {
			return (User) auth.getPrincipal();
		}
		return null;
	}
	
	/** Determines if the user requesting the page is authenticated or not
	 * 
	 * @return True if the user is authenticated, false otherwise
	 */
	public boolean isAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//if auth is null or anonymous, return false
		if (auth == null  || (auth instanceof AnonymousAuthenticationToken)) {
			return false;
		}
		return auth.isAuthenticated();
	}
}
