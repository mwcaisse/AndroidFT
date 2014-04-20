/**
 * 
 */
package com.ricex.aft.client.request.request;

import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public class CreateRequestRequest extends AbstractRequest<Long> {

	/** The request to send to the web service to create */
	private Request toCreate;
	
	/** Create a a new Create Request Request, to create the specified request on the web service, and notified
	 * 	the specified listener when the request has completed
	 * 
	 * @param toCreate The request to create
	 * @param listener The listener to notify upon completion
	 */
	
	public CreateRequestRequest(Request toCreate, RequestListener<Long> listener) {
		super(listener);
		this.toCreate = toCreate;
	}
	
	/** Adds the fetched request to the Request Cache and then notifies the listener of
	 * 	completion
	 * 
	 */
	
	@Override
	public void onCompletion() {	
		if (response < 0) { //if the server returned an ID less than 0, then the creation failed
			onFailure(new Exception("Creation failed"));
		}
		else {
			toCreate.setRequestId(response);
			RequestCache.getInstance().add(toCreate);
			onSucess();			
		}
	}
	
	/** Converts the JSON string received from the server, into the correct object for this Request
	 * 
	 * @param jsonString The JSONString containing the object
	 * @return The resulting java object from the JSON string
	 */
	
	protected Long convertResponseFromJson(String jsonString) {
		return gson.fromJson(jsonString, Long.class);
	}

	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.post(baseServiceUrl + "request/create")
				.header("Content-Type", "application/json")
				.body(gson.toJson(toCreate, Request.class));
		
	}
	
}
