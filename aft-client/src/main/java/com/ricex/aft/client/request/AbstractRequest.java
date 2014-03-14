/**
 * 
 */
package com.ricex.aft.client.request;

import java.util.UUID;

import com.ricex.aft.client.controller.RequestListener;

/** The base request class for IRequest
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractRequest<T> implements IRequest<T> {

	/** The id of this request */
	private long id;
	
	/** The request listener for this request */
	protected RequestListener listener;
	
	/** Creates a new instance of Request with the specified RequestListener, and creates a UUID for the request
	 * 
	 * @param listener The request listener to notify of the results of the request
	 */
	
	protected AbstractRequest(RequestListener listener) {
		id = UUID.randomUUID().getLeastSignificantBits();
		this.listener = listener;
	}
	
	/** Returns the id of this request
	 * 
	 * @return The id of the request
	 */
	
	public long getId() {
		return id;
	}	
	
	/** Notifies the RequestListener than the request has completed successfully
	 * 
	 */
	
	public void onSucess() {
		listener.onSucess(this);
	}
	
	/** Called when the request failed to execute properly, notifies the request listener of the failure
	 * 
	 */
	
	public void onFailure(Exception e) {
		listener.onFailure(this, e);
	}
	
	/** Called when the request was cancelled.
	 * 
	 */
	
	public void onCancelled() {
		listener.cancelled(this);
	}

}
