package com.ricex.aft.servlet.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ricex.aft.common.entity.AbstractEntity;
import com.ricex.aft.common.entity.UserInfo;
import com.ricex.aft.common.entity.UserInfoImpl;

/** Represents a user
 * 
 * @author Mitchell Caisse
 *
 */
public class User extends AbstractEntity implements UserDetails {
	
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
	
	/** Determines if the user has the specified role
	 * 
	 * @param role The role to check
	 * @return True if the user has the role, false otherwise
	 */	
	public boolean userHasRole(UserRole role) {
		return roles.contains(role);
	}
	
	/** Determines if the user has atleast one of the roles specified
	 * 
	 * @param roles The roles to check
	 * @return True if the user has one of the roles, false otherwise
	 */
	public boolean userHasOneOfRoles(UserRole... roles) {
		for (UserRole role : roles) {
			if (userHasRole(role)) {
				return true;
			}
		}
		return false;		
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

	/** Returns the list of Authorities for this user
	 * 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	/** Returns whether the account is not expired or not
	 * 
	 */
	@Override
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	/** Returns whether the account is not locked or not
	 * 
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !isLocked();
	}

	/** Returns whether the credentials are not expired or expired
	 * 
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return !isPasswordExpired();
	}

	/** Returns whether the account is enabled or not
	 * 
	 */
	@Override
	public boolean isEnabled() {
		return isActive();
	}
	
	/** Converts this user into a UserInfo
	 * 
	 * @return The resulting user info, with sensitive fields removed
	 */
	public UserInfo toUserInfo() {
		return convertToUserInfo(this);
	}
	
	/** Converts the specified User into a User Info, removing sensitive fields
	 * 
	 * @param user The user to convert
	 * @return The resulting User Info
	 */
	public static UserInfo convertToUserInfo(User user) {
		UserInfoImpl userInfo = new UserInfoImpl();
		userInfo.setId(user.getId());
		userInfo.setName(user.username);
		userInfo.setUsername(user.username);		
		return userInfo;
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
