package com.ricex.aft.servlet.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public static final String AFT_AUTH_TOKEN_HEADER = "AFT_AUTH_TOKEN";
	
	public static final String AFT_AUTH_NEED_TOKEN_HEADER = "AFT_NEED_TOKEN";
	
	public AFTAuthenticationFilter() {
		super(new RequestHeaderRequestMatcher(AFT_AUTH_TOKEN_HEADER));
	}

	/** Attempts to authorize the request
	 * 
	 */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException,	IOException, ServletException {

		String token = request.getHeader(AFT_AUTH_TOKEN_HEADER);
		String needToken = request.getHeader(AFT_AUTH_NEED_TOKEN_HEADER);
		
		log.debug("Token: " + token + " NeedToken?: " + needToken);
		

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("testuser123", "password");
		return getAuthenticationManager().authenticate(auth);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) 
			throws ServletException, IOException {
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		chain.doFilter(request, response);
	}

}
