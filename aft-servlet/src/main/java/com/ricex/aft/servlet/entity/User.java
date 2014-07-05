/**
 * 
 */
package com.ricex.aft.servlet.entity;

import java.util.Date;
import java.util.List;

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
	
	/** Whehtoer or not the users account is currently locked */
	private boolean userLocked;
	
	/** The Date that the user's account expires */
	private Date userExpirationDate;
	
	/** The Date that the users password expires */
	private Date userPasswordExpirationDate;
	
	/** The list of Authorities for this user */
	private List<Authority> authorities;
	
	/** Creates a new User
	 * 
	 */
	
	public User() {
		
	}
	
	/** Returns whether or no the users account is expired
	 * 
	 * @return True if the account is expired, false otherwise
	 */
	
	public boolean isAccountExpired() {
		if (userExpirationDate == null) {
			return false; // if expiration date is null, then it never expires
		}
		else if (userExpirationDate.before(new Date())) {
			return true; //the expiration date is before now, account expired
		}		
		return false; //account not expired
	}
	
	/** Returns whether or not the users account password is expired
	 * 
	 * @return True if password is expired, false otherwise
	 */
	
	public boolean isAccountPasswordExpired() {
		if (userPasswordExpirationDate == null) {
			return false; // if expiration date is null, then it never expires
		}
		else if (userPasswordExpirationDate.before(new Date())) {
			return true; //the expiration date is before now, password expired
		}
		return false; //password not expired
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

	/**
	 * @return the authorities
	 */
	
	public List<Authority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the userLocked
	 */
	
	public boolean isUserLocked() {
		return userLocked;
	}

	/**
	 * @param userLocked the userLocked to set
	 */
	
	public void setUserLocked(boolean userLocked) {
		this.userLocked = userLocked;
	}
	
}
