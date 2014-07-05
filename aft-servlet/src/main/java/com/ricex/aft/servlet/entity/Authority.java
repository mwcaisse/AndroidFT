/**
 * 
 */
package com.ricex.aft.servlet.entity;

import org.springframework.security.core.GrantedAuthority;


/**
 * @author Mitchell Caisse
 *
 */
public enum Authority implements GrantedAuthority {

	
	ROLE_ADMIN ("ROLE_ADMIN"),
	ROLE_USER ("ROLE_USER");
	
	
	
	/** Converts a string to its corresponding Authority
	 * 
	 * @param str The string containing the Authority name
	 * @return The Authority the string represents, or null if no authority exists
	 */
	
	public static Authority fromString(String str) {
		for (Authority auth : values()) {
			if (str.equals(auth.name)) {
				return auth;
			}
		}
		return null; //invalid authority
	}
	
	
	/** The string name of this Authority */
	private String name;
	
	
	/** Creates a new Authority with the given name
	 * 
	 * @param name The name of the Authority
	 */
	
	private Authority(String name) {
		this.name = name;
	}
	
	/** Returns the string representation of this Authority, which is its name
	 * 
	 */
	
	public String toString() {
		return name;
	}

	/** Returns the string representation of the Granted Authority this Authority represents
	 * 
	 */
	
	@Override
	public String getAuthority() {
		return name;
	}
	
}
