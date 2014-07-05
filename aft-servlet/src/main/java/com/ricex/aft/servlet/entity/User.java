/**
 * 
 */
package com.ricex.aft.servlet.entity;

import java.util.Date;

/** User representation of a user as stored in the data service
 * 
 * @author Mitchell Caisse
 *
 */

public class User {

	/** The users id */
	private long userId;
	
	/** The users username */
	private String userName;
	
	/** The users password */
	private String userPassword;
	
	/** Whether or not the users account is currently active */
	private boolean userActive;
	
	/** The Date that the user's account expires */
	private Date userExpirationDate;
	
	/** The Date that the users password expires */
	private Date userPasswordExpirationDate;
	
	/** Creates a new User
	 * 
	 */
	
	public User() {
		
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
	 * @return the userName
	 */
	
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the userActive
	 */
	
	public boolean isUserActive() {
		return userActive;
	}

	/**
	 * @param userActive the userActive to set
	 */
	
	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}

	/**
	 * @return the userExpirationDate
	 */
	
	public Date getUserExpirationDate() {
		return userExpirationDate;
	}

	/**
	 * @param userExpirationDate the userExpirationDate to set
	 */
	
	public void setUserExpirationDate(Date userExpirationDate) {
		this.userExpirationDate = userExpirationDate;
	}

	/**
	 * @return the userPasswordExpirationDate
	 */
	
	public Date getUserPasswordExpirationDate() {
		return userPasswordExpirationDate;
	}

	/**
	 * @param userPasswordExpirationDate the userPasswordExpirationDate to set
	 */
	
	public void setUserPasswordExpirationDate(Date userPasswordExpirationDate) {
		this.userPasswordExpirationDate = userPasswordExpirationDate;
	}
	
	
	
}
