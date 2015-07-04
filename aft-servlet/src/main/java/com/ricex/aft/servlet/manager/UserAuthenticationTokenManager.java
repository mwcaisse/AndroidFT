package com.ricex.aft.servlet.manager;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import com.ricex.aft.common.auth.AuthToken;
import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.entity.UserAuthenticationToken;
import com.ricex.aft.servlet.entity.exception.EntityException;
import com.ricex.aft.servlet.mapper.UserAuthenticationTokenMapper;

public enum UserAuthenticationTokenManager {

	INSTANCE;
	
	/** The User Manager to perform user related tasks */
	private UserManager userManager;
	
	/** The UserAuthenticationTokenMapper to fetch and persist date */
	private UserAuthenticationTokenMapper userAuthenticationTokenMapper;
	
	/** Creates a new instance of UserAuthenticationTokenManager
	 * 
	 */
	private UserAuthenticationTokenManager() {

	}
	
	/** Fetches a User Authenication Token with the specified ID
	 * 
	 * @param id The id of the token
	 * @return The token
	 */
	
	public UserAuthenticationToken getUserAuthenticationToken(long id) {
		return userAuthenticationTokenMapper.getUserAuthenticationToken(id);
	}
	
	/** Fetches a User Authentication Token with the specified token
	 * 
	 * @param token The token
	 * @return The token
	 */
	public UserAuthenticationToken getUserAuthenticationTokenByToken(String token) {
		return userAuthenticationTokenMapper.getUserAuthenticationTokenByToken(token);
	}
	
	/** Updates an UserAuthenticationToken
	 * 
	 * @param token The token to update
	 */
	
	protected void updateUserAuthenticationToken(UserAuthenticationToken token) {
		userAuthenticationTokenMapper.updateUserAuthenticationToken(token);
	}
	
	/** Deactivates all of the auth tokens that belong to a user and belong to a specific device
	 * 
	 * @param deviceUid The uid of the device
	 * @param userId The id of the user
	 */
	
	protected void deactivateAuthenticationToken(String deviceUid, long userId) {
		userAuthenticationTokenMapper.deactivateAuthTokens(deviceUid, userId);
	}
	
	/** Creates and persists a UserAuthenticationToken for the specified User and Device
	 * 
	 * @param userName The username of the user this token is for
	 * @param deviceUid The UID of the device this token will be valid for
	 * @return The newly created UserAuthenitcaitonToken
	 */
	
	public UserAuthenticationToken createUserAuthenticationToken(String username, String deviceUid) throws EntityException {
		User user = userManager.getUserByUsername(username);
		if (user == null) {
			throw new EntityException("Invalid User! No user with that username exists");
		}
		
		if (StringUtils.isBlank(deviceUid)) {
			throw new EntityException("Device Uid cannot be blank!");
		}
		
		//deactivate any old auth tokens for the user and the device
		deactivateAuthenticationToken(deviceUid, user.getId());
		
		String tokenString = generateAuthenitcationToken();
		UserAuthenticationToken token = new UserAuthenticationToken();
		
		token.setActive(true);
		token.setAuthenticationToken(tokenString);
		token.setDeviceUid(deviceUid);
		token.setUser(user);
		
		userAuthenticationTokenMapper.createUserAuthenticationToken(token);
		
		return token;
	}

	/** Authenticates the given AuthToken
	 * 
	 * @param token The token to use to perform the Authentication
	 * @param String representing the address of the requester
	 * @return The authentication	
	 * @throws AuthenticationException If the user could not be authenticated
	 */
	
	public Authentication authenticate(AuthToken token, String remoteAddress) throws AuthenticationException {		
		UserAuthenticationToken userToken = getUserAuthenticationTokenByToken(token.getAuthenticationToken());		
		if (userToken == null) {
			throw new PreAuthenticatedCredentialsNotFoundException("The provided UserAuthenticationToken is not valid");
		}
		
		//check if the deviceUids provided and stored in the token are the same, check that the token has a valid user
		if (!userToken.getDeviceUid().equals(token.getDeviceUid()) || userToken.getUser() == null) {
			throw new BadCredentialsException("Invalid Authentication Token for user and/or device!");
		}
		
		if (!userToken.isActive()) {
			throw new DisabledException("The authentication token is not active!");
		}
		
		userToken.setLastLogin(new Date());
		userToken.setLastLoginAddress(remoteAddress);
		
		updateUserAuthenticationToken(userToken);
		
		//the provided token was found, has a user and deviceUid, user + devideUid match the request. Authenication Successful.
		userToken.setAuthenticated(true);
		
		return userToken;
	}
	
	/** Generates an authentication token
	 * 
	 * @return The generated authentication token
	 */
	
	protected String generateAuthenitcationToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @return the userAuthenticationTokenMapper
	 */
	public UserAuthenticationTokenMapper getUserAuthenticationTokenMapper() {
		return userAuthenticationTokenMapper;
	}

	/**
	 * @param userAuthenticationTokenMapper the userAuthenticationTokenMapper to set
	 */
	public void setUserAuthenticationTokenMapper(UserAuthenticationTokenMapper userAuthenticationTokenMapper) {
		this.userAuthenticationTokenMapper = userAuthenticationTokenMapper;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}	
	
	
	
}


