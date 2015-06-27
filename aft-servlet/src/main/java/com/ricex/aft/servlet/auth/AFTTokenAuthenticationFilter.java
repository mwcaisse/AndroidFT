package com.ricex.aft.servlet.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import com.ricex.aft.common.auth.AFTAuthentication;

/** Part of the AFT API Authorization Filter Chain
 * 
 *  Extracts the token from the request, and sets the appropriate Authorization
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/** The logger */
	private static Logger log = LoggerFactory.getLogger(AFTTokenAuthenticationFilter.class);
	
	/** The token manager to fetch tokens */
	private TokenManager tokenManager;
	
	/** Creates a new AFT Token Authentication Filter with the default request matcher
	 * 
	 */
	public AFTTokenAuthenticationFilter() {
		super(new RequestHeaderRequestMatcher(AFTAuthentication.AFT_SESSION_TOKEN_HEADER));
	}

	/** Retrieve the Authentication token from the Request Header, and set the user's authentication
	 * 
	 */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,	HttpServletResponse response) 
			throws AuthenticationException,	IOException, ServletException {
		
		log.debug("AFT Token authentication");
		String tokenId = request.getHeader(AFTAuthentication.AFT_SESSION_TOKEN_HEADER);
		Token token = tokenManager.getToken(tokenId);
		
		log.debug("TokenId: " + tokenId + " Token: " + token);		
		if (token != null) {
			return token.getAuthentication();
		}
		//no token was found, clear security context, and send an error back
		SecurityContextHolder.clearContext();		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: Token is invalid or expired.");
		
		//no token was found, return null
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) 
			throws ServletException, IOException {
		//successful auth, set the header, and proceed forward
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
