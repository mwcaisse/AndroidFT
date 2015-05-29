package com.ricex.aft.servlet.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.ricex.aft.common.auth.AFTAuthentication;
import com.ricex.aft.common.auth.AuthUser;
import com.ricex.aft.servlet.controller.api.FileController;

/** Class for authorizing users through an API entry point rather than the HTML Form log in
 * 
 * @author Mitchell Caisse
 *
 *
 */
public class APIUserAuthenticator {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(APIUserAuthenticator.class);
	
	/** The Spring AuthenticationManager to use when authenticating users */
	private AuthenticationManager authenticationManager;
	
	/** The token manager to use when creating authentication tokens */
	private TokenManager tokenManager;
	
	/** Constructs an APIUserAuthroizer 
	 */
	public APIUserAuthenticator() {
		
	}
	
	/** Authenticates a user and provides them with an Authentication Token
	 * 
	 * @param user The user's credentials
	 * @param clientAddress The ip address of the user's machine
	 * @return The token that has been created for the user
	 * @throws AuthenticationException If the user was unable to be authenticated
	 */
	public Token authenticateUser(AuthUser user, String clientAddress) throws AuthenticationException {
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());	
		
		//authenticate the user, this will throw an Authentication exception if user could not be authenticated
		auth = authenticationManager.authenticate(auth);
		
		//create a token for the user, and add it to the AFT Token header in the response
		log.info("User: {} successfully authenticated, generating an authorization token!", auth.getName());
		Token token = getTokenForUser(auth, clientAddress);
		
		return token;
	}
	
	/** Creates or fetches an existing token for the specified user
	 * 
	 * @param auth The authentication / user to get the token for
	 * @param clientAddress The address of the user
	 * @return The token for the user to use
	 */
	private Token getTokenForUser(Authentication auth, String clientAddress) {
		String username = auth.getName();
		//check if this user already has a token
		Token token = tokenManager.getTokenForUser(username);
		if (token == null) {
			log.debug("Token for {} not found. Creating new token.", token);
			//if not create a new token for the user
			token = tokenManager.createToken(username, auth, clientAddress);
		}
		return token;
	}

	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @return the tokenManager
	 */
	public TokenManager getTokenManager() {
		return tokenManager;
	}

	/**
	 * @param tokenManager the tokenManager to set
	 */
	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}
	
}
