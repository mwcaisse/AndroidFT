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

	/** Initializes the request cache for storing requests
	 * 
	 */
	
	public RequestCache() {
		
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void add(Request request) {
		elements.put(request.getRequestId(), request);
	}
}
