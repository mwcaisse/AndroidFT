package com.ricex.aft.servlet.config;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AFTServletApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/** Defines the root spring config classes
	 * 
	 */
	
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfig.class, SecurityConfig.class };
	}

	/** Defines the web config classes
	 * 
	 */
	
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	/** Defines the servlet mappings
	 * 
	 */
	
	protected String[] getServletMappings() {
		return new String[] { "/"};
	}
	
	/** Creates the Filters for the Servlet
	 * 
	 */
	
	protected Filter[] getServletFilters() {
		return new Filter[] {};
	}

}
