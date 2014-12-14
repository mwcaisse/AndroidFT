package com.ricex.aft.servlet.entity;

/** Entity for Registering a new User account
 * 
 * @author Mitchell Caisse
 *
 */
public class UserRegistration {

	/** The users username */
	private String username;
	
	/** The users password */
	private String password;
	
	/**The user's email address */
	private String emailAddress;
	
	/** The registration key the user used for registration */
	private String registrationKey;
	
	/** Creates a new UserRegistration instance
	 * 
	 */
	public UserRegistration() {
		
	}
	
	/** Converts this User Registration into a User object
	 * 
	 * @return The user object representing this user registration
	 */
	public User toUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmailAddress(emailAddress);
		return user;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the registrationKey
	 */
	public String getRegistrationKey() {
		return registrationKey;
	}

	/**
	 * @param registrationKey the registrationKey to set
	 */
	public void setRegistrationKey(String registrationKey) {
		this.registrationKey = registrationKey;
	}
	
}
