/**
 * 
 */
package com.ricex.aft.servlet.util;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/** Simple CORS filter based upon the Spring SimpleCORSFilter
 * 	https://spring.io/guides/gs/rest-service-cors/ 
 * 
 * @author Mitchell Caisse
 *
 */

@Component
public class SimpleCORSFilter implements Filter {

	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS,PUT");
		response.setHeader("Access-Control-Allow-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers","Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
		chain.doFilter(req, resp);
	}

	public void destroy() {}

	public void init(FilterConfig filterConfig) throws ServletException { }
	
}
