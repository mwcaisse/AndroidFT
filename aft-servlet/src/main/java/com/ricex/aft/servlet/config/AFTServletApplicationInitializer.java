package com.ricex.aft.servlet.config;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ricex.aft.servlet.util.SimpleCORSFilter;

public class AFTServletApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/** Defines the root spring config classes
	 * 
	 */
	
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfig.class};
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
		return new String[] { "/manager/*"};
	}
	
	/** Creates the Filters for the Servlet
	 * 		Specificialy a SimpleCORSFilter
	 */
	
	protected Filter[] getServletFilters() {		
		SimpleCORSFilter corsFilter = new SimpleCORSFilter();		
		return new Filter[] { corsFilter };
	}

}
