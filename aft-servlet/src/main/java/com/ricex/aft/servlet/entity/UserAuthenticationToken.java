package com.ricex.aft.servlet.entity;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.ricex.aft.common.entity.AbstractEntity;

/** Represents an Authentication Token for a user
 * 
 * @author Mitchell Caisse
 *
 */
public class UserAuthenticationToken extends AbstractEntity implements Authentication {

	private static final long serialVersionUID = -4045504875231509991L;
	
	/** The user this token is for */
	private User user;
	
	/** The token string */
	private String authenticationToken;
	
	/** The UID of the device this token is for */
	private String deviceUid;
	
	/** Whether or not this token is active */
	private boolean active;
	
	/** Whether or not this authentication is authenticated */
	private boolean authenticated;
	
	/** The date on which this token expires */
	private Date expirationDate;
	
	/** The last time this token was used to login */
	private Date lastLogin;
	
	/** The remote address (IP) of the last login */
	private String lastLoginAddress;

	/** Creates a new isntance of UserAuthenticationToken
	 * 
	 */
	public UserAuthenticationToken() {
		authenticated = false;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the authenticationToken
	 */
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	/**
	 * @param authenticationToken the authenticationToken to set
	 */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	/**
	 * @return the deviceUid
	 */
	public String getDeviceUid() {
		return deviceUid;
	}

	/**
	 * @param deviceUid the deviceUid to set
	 */
	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
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
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the lastLoginAddress
	 */
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	/**
	 * @param lastLoginAddress the lastLoginAddress to set
	 */
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	public String getName() {
		return user.getUsername();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	public Object getCredentials() {
		return authenticationToken;
	}

	public Object getDetails() {
		return deviceUid;
	}

	public Object getPrincipal() {
		return user;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (!isAuthenticated) {
			throw new IllegalArgumentException("Can not revoke authentication!");
		}
		if (user == null || StringUtils.isBlank(deviceUid)) {
			throw new IllegalArgumentException("Can not be authenticated with no user or device uid!");
		}
		if (!isActive()) {
			throw new IllegalArgumentException("Authentication Token is not active! Can not be authenticated!");
		}
		//check last login info
		if (StringUtils.isBlank(lastLoginAddress)) {
			throw new IllegalArgumentException("Last Login Address must be set before the token can be authenticated!");
		}		
		//check if last login time is within 5 seconds of now
		if (lastLogin == null || new Date().getTime() - lastLogin.getTime() > 5000) {
			throw new IllegalArgumentException("Last login time must be set before the token can be authenticated!");
		}
		
		authenticated = isAuthenticated; 
		
	}	

}
