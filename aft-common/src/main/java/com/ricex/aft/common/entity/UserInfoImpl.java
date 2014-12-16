package com.ricex.aft.common.entity;


/** Represents a the details of a user. Used to sending information about the owner of a device / file / request
 * 
 * @author Mitchell Caisse
 *
 */
public class UserInfoImpl extends AbstractEntity implements UserInfo {
	
	/** The username of the user */
	private String username;
	
	/** The name of the user */
	private String name;
	
	/** Creates a new User Details */
	public UserInfoImpl() {
		
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Determines if the specified object is equal to this object
	 * 
	 */
	public boolean equals(Object other) {
		if (!(other instanceof UserInfo)) {
			return false;
		}
		UserInfo info = (UserInfo)other;
		//the othere is a UserInfo, return true if the ids are equal
		return info.getId() == getId();
	}
}
