/**
 * 
 */
package com.ricex.aft.client.request.request;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Request;

/** 
 *  Fetches a Request from the web service by id
 *  
 * @author Mitchell Caisse
 *
 */
public class FetchRequestByIdRequest extends AbstractRequest<Request> {

	/** The id of the request to fetch */
	private long requestId;	
	
	/** Creates a new Fetch Request By Id Request to fetch the request with the specified id
	 * 
	 * @param requestId The id of the request to fetch
	 * @param listener The listener to notify when the request is completed
	 */
	
	public FetchRequestByIdRequest(long requestId, RequestListener<Request> listener) {
		super(listener);
		this.requestId = requestId;
	}

	/** Adds the fetched request to the Request Cache and then notifies the listener of
	 * 	completion
	 * 
	 */
	
	@Override
	public void onCompletion() {
		RequestCache.getInstance().add(response);
		onSucess();
	}
	
	/** Converts the JSON string received from the server, into the correct object for this Request
	 * 
	 * @param jsonString The JSONString containing the object
	 * @return The resulting java object from the JSON string
	 */
	
	protected Request convertResponseFromJson(String jsonString) {
		return gson.fromJson(jsonString, Request.class);
	}
	
	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.get(baseServiceUrl + "request/{id}")
			.routeParam("id", Long.toString(requestId));
	}
	
}
