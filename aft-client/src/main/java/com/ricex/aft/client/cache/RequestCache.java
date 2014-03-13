/**
 * 
 */
package com.ricex.aft.client.cache;

import com.ricex.aft.common.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public class RequestCache extends AbstractCache<Request, Long> {

	/** The singleton instance */
	private static RequestCache _instance;
	
	/** Returns the singleton instance of the RequestCache
	 * 
	 * @return The singleton instance
	 */
	
	public static RequestCache getInstance() {
		if (_instance == null) {
			_instance = new RequestCache();
		}
		return _instance;
	}
	
	/** Initializes the request cache for storing requests
	 * 
	 */
	
	private RequestCache() {
		
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void add(Request request) {
		elements.put(request.getRequestId(), request);
	}
}
