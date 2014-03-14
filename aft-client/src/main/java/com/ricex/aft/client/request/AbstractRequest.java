/**
 * 
 */
package com.ricex.aft.client.request;

import java.util.UUID;

import com.mashape.unirest.request.BaseRequest;
import com.ricex.aft.client.controller.RequestListener;

/** The base request class for IRequest
 * 
 * Encapsulates the Restful web client implementation, Unirest, from the rest of the application, along with the Controllers
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractRequest<T> implements IRequest<T> {

	/** The id of this request */
	private long id;
	
	/** The request listener for this request */
	protected RequestListener listener;
	
	/** The Unirest request to be sent to the web server */
	protected BaseRequest serverRequest;
	
	/** The response item this received from the server, if the request was successful */
	protected T response;
	
	/** Creates a new instance of Request with the specified RequestListener, and creates a UUID for the request
	 * 
	 * @param listener The request listener to notify of the results of the request
	 */
	
	protected AbstractRequest(RequestListener listener) {
		id = UUID.randomUUID().getLeastSignificantBits();
		this.listener = listener;
		constructServerRequest();
	}	
	
	/** Constructs the Unirest request that will be executed when this result is executed
	 * 
	 */
	
	protected abstract void constructServerRequest();
	
	/** Returns the id of this request
	 * 
	 * @return The id of the request
	 */
	
	public long getId() {
		return id;
	}	
	
	/** Returns the response of the Request
	 * 
	 * @return The response of the request
	 */
	
	public T getResponse() {
		return response;
	}
	
	/** Gets the Unirest request that will be executed to retrieve data from the server
	 * 
	 * @return The unirest request.
	 */
	
	public BaseRequest getServerRequest() {
		return serverRequest;
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
