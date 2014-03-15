/**
 * 
 */
package com.ricex.aft.client.request;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
	protected RequestListener<T> listener;
	
	/** The Unirest request to be sent to the web server */
	protected BaseRequest serverRequest;
	
	/** The response item this received from the server, if the request was successful */
	protected T response;
	
	/** The base service URL of the webservice */
	protected String baseServiceUrl;
	
	/** Creates a new instance of Request with the specified RequestListener, and creates a UUID for the request
	 * 
	 * @param listener The request listener to notify of the results of the request
	 */
	
	protected AbstractRequest(RequestListener<T> listener) {
		id = UUID.randomUUID().getLeastSignificantBits();
		this.listener = listener;
		baseServiceUrl = "http://localhost:8080/aft-servlet/manager/";
		constructServerRequest();
	}	
	
	/** Called when the processResponse has been completed, should do any additional actions with the 
	 * 		response as needed.
	 * 
	 * 	This implementation simply calls onSucess
	 * 
	 */
	public void onCompletion() {
		onSucess();
	}
	
	/** Parses the rawResponseBody into an Object using Gson
	 * 
	 *  If the HTTP Status code is OK (200) the raw response body is parsed into an Object from JSON using GSON
	 *  	and the onSuccess method is called with the resulting object, Otherwise the onFailure method
	 *  	is called. 
	 * 
	 * @param rawResponseBody The InputStream containing the raw body of the server's response
	 * @param httpStatusCode The status code returned by the webservice
	 */
	
	public void processResponse(InputStream rawResponseBody, int httpStatusCode) {
		if (httpStatusCode == 200) {
			Gson gson = new Gson();
			response = gson.fromJson(new InputStreamReader(rawResponseBody), new TypeToken<T>() {}.getType());
			onCompletion();			
		}
		else {
			onFailure(new Exception("Server returned status code: " + httpStatusCode));
		}
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
