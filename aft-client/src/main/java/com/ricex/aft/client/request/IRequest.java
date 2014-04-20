/**
 * 
 */
package com.ricex.aft.client.request;

import com.mashape.unirest.request.BaseRequest;

/**
 * @author Mitchell Caisse
 *
 */
public interface IRequest<T> {

	/** Gets the ID of this request
	 * 
	 * @return The ID of this request
	 */
	
	public long getId();
	
	/** Returns the response of the Request
	 * 
	 * @return The response of the request
	 */
	
	public T getResponse();
	
	/** Gets the Unirest request that will be executed to retrieve data from the server
	 * 
	 * @return The unirest request.
	 */
	
	public BaseRequest getServerRequest();
	
	/** Processes the response received from the request
	 * 
	 * @param rawResponseBody The raw response body received from the server
	 * @param httpStatusCode The http status code of the response
	 */
	
	public void processResponse(String rawResponseBody, int httpStatusCode);
	
	/** Called when there was an error executing this request
	 * 
	 * @param e The exception that was thrown causing the error
	 */
	
	public void onFailure(Exception e);
	
	/** Called when the request was cancelled
	 * 
	 */
	
	public void onCancelled();
	
}
