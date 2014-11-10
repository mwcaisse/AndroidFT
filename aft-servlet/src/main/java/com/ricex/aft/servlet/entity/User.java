package com.ricex.aft.servlet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** Represents a user
 * 
 * @author Mitchell Caisse
 *
 */
public class User implements Serializable {

	/** The id of the user */
	private long userId;
	
	/** The username of the user */
	private String username;
	
	/** The users password */
	private String password;
	
	/** The users email address */
	private String emailAddress;
	
	/** Whether or not this user is active */
	private boolean active;
	
	/** Whether or not this user is locked */
	private boolean locked;
	
	/** When the user expires */
	private Date expirationDate;
	
	/** When the users password expires */
	private Date passwordExpirationDate;
	
	/** The list of roles that this user has */
	private List<UserRole> roles;
	
	/** Creates a new User
	 * 
	 */
	public User() {
		
	}
	
	/** Determines whether or not this account has expired
	 * 
	 * @return True if the account has expired, false otherwise
	 */
	public boolean isAccountExpired() {
		if (expirationDate == null) {
			return false; //if no expiration date, account can't expire
		}
		//if the expiration date is in the past, then the account has expired
		return expirationDate.before(new Date()); 
	}
	
	/** Determines whether or not the password for this User has expired
	 * 
	 * @return True if the password is expired, false otherwise
	 */
	public boolean isPasswordExpired() {
		if (passwordExpirationDate == null) {
			return false; //if there is no expiration date, the password can't expire
		}
		//if the expiration date is in the past, then the password has expired
		return expirationDate.before(new Date());
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	
	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}

	
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	
	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	
	/**
	 * @return the passwordExpirationDate
	 */
	public Date getPasswordExpirationDate() {
		return passwordExpirationDate;
	}

	
	/**
	 * @param passwordExpirationDate the passwordExpirationDate to set
	 */
	public void setPasswordExpirationDate(Date passwordExpirationDate) {
		this.passwordExpirationDate = passwordExpirationDate;
	}

	
	/**
	 * @return the roles
	 */
	public List<UserRole> getRoles() {
		return roles;
	}

	
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
	
}
