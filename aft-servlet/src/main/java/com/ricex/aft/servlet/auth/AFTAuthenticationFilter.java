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
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

/** The Authentication Filter to use for parsing in AFT security
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(AFTAuthenticationFilter.class);

	/** The Authentication Token containing the user's authentication details to signal their request for auth */
	public static final String AFT_AUTH_INIT_HEADER = "AFT_AUTH_INIT";	
	
	/** The token manager */
	private TokenManager tokenManager;
	
	public AFTAuthenticationFilter() {
		super(new RequestHeaderRequestMatcher(AFT_AUTH_INIT_HEADER));
	}

	/** Extracts the authentication details from the header, and attempts to authenticate the user
	 * 
	 */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException,	IOException, ServletException {
		
		//get the auth info from the request header + decode it
		String authInit = request.getHeader(AFT_AUTH_INIT_HEADER);
		//String decodedAuth = new String(Base64.decodeBase64(authInit), "UTF-8");
		
		//parse the info into the username + password
		//StringTokenizer st = new StringTokenizer(decodedAuth, "|");
		StringTokenizer st = new StringTokenizer(authInit, "|");	
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
		Token token = tokenManager.createToken(auth, request.getRemoteAddr());
		response.addHeader(AFTTokenAuthenticationFilter.AFT_AUTH_TOKEN_HEADER, token.getTokenId());
		
		SecurityContextHolder.getContext().setAuthentication(auth);	
		
		chain.doFilter(request, response);
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
