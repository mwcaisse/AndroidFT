package com.ricex.aft.servlet.entity;

import org.springframework.security.core.GrantedAuthority;


/** A user role
 * 
 * @author Mitchell Caisse
 *
 */
public enum UserRole implements GrantedAuthority {
	
	/** The default User role */
	ROLE_USER ("ROLE_USER"),
	/** The Admin role */
	ROLE_ADMIN ("ROLE_ADMIN");	
	
	/** Converts the given string into its corresponding User Role
	 * 
	 * @param str The string to convert into a User Role
	 * @return  The User Role corresponding to the string, or null if it is invalid 
	 */
	public static UserRole fromString(String str) {
		for (UserRole role : values()) {
			if (role.name.equalsIgnoreCase(str)) {
				return role;
			}
		}
		return null; // not found
	}
	
	/** The name of this role */
	private String name;
	
	/** Creates a new User Role with the given name
	 * 
	 * @param name The name of this role
	 */
	private UserRole(String name) {
		this.name = name;
	}
	
	/** Returns the string representation of this User Role
	 * 
	 */
	public String toString() {
		return name;
	}

	/** Returns the name of this Authority
	 * 
	 */
	@Override
	public String getAuthority() {
		return name;
	}
}
