package com.ricex.aft.common.entity;

/** Represents a the details of a user. Used to sending information about the owner of a device / file / request
 * 
 * @author Mitchell Caisse
 *
 */
public class UserDetails {

	/** The id of this user */
	private long userId;
	
	/** The username of the user */
	private String username;
	
	/** The name of the user */
	private String name;
	
	/** Creates a new User Details */
	public UserDetails() {
		
	}

	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
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
	
	
	
}
