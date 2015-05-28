package com.ricex.aft.servlet.auth;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import com.ricex.aft.common.auth.AFTAuthentication;

/** The Authentication Filter to use for parsing in AFT security
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(AFTAuthenticationFilter.class);	
	
	/** The token manager */
	private TokenManager tokenManager;
	
	public AFTAuthenticationFilter() {
		super(new RequestHeaderRequestMatcher(AFTAuthentication.AFT_AUTH_INIT_HEADER));
		
		new SimpleUrlAuthenticationFailureHandler();
	}

	/** Extracts the authentication details from the header, and attempts to authenticate the user
	 * 
	 */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException,	IOException, ServletException {
		
		//get the auth info from the request header + decode it
		String authInit = request.getHeader(AFTAuthentication.AFT_AUTH_INIT_HEADER);
		
		if (! Base64.isBase64(authInit)) {
			throw new AFTAuthorizationInitHeaderInvalid("AUTH INIT Header must be Base64 Encoded!");
		}
		
		String decodedAuth = new String(Base64.decodeBase64(authInit), "UTF-8");
		log.debug("Decoded AFT-AUTH-INIT INTO: |{}|", decodedAuth);
		//parse the info into the username + password
		StringTokenizer st = new StringTokenizer(decodedAuth, "|");
		
		//check to make sure that the token is in the correct format
		if (st.countTokens() != 2) {
			throw new AFTAuthorizationInitHeaderInvalid("AUTH INIT must be in the form \"username|password\"!");
		}
		String username = st.nextToken();
		String password = st.nextToken();
		
		//test the authentication to ensure that the credentials are valid
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);		
		return this.getAuthenticationManager().authenticate(auth);
	}
	
	/** When the authentication is successful, create the token, and send it back to the user in the response header
	 * 
	 */
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) 
			throws ServletException, IOException {
		//the credentials are valid, create the token	
		log.info("Authentication Valid, getting a token for the user {}", auth.getName());
		Token token = getTokenForUser(auth, request.getRemoteAddr());
		response.addHeader(AFTAuthentication.AFT_AUTH_TOKEN_HEADER, token.getTokenId());
		
		SecurityContextHolder.getContext().setAuthentication(auth);	
		
		chain.doFilter(request, response);
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
