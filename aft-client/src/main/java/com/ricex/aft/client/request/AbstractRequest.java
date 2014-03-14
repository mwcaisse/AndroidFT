/**
 * 
 */
package com.ricex.aft.client.request;

import com.ricex.aft.client.controller.RequestListener;

/** The base request class for IRequest
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractRequest implements IRequest{

	/** The id of this request */
	private long id;
	
	/** The request listener for this request */
	protected RequestListener listener;
	
	protected AbstractRequest(RequestListener listener) {
		this.listener = listener;
	}
	
	/** Returns the id of this request
	 * 
	 * @return The id of the request
	 */
	
	public long getId() {
		return id;
	}
	
	public void onSucess() {
		
	}

	public void onFailure() {
		
	}

	public void onCancelled() {
		
	}
}
