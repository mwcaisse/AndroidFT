/**
 * 
 */
package com.ricex.aft.client.request.request;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Request;

/**
 *  Request to update a request on the web service
 *  
 * @author Mitchell Caisse
 *
 */

public class UpdateRequestRequest extends AbstractRequest<Long> {
	
	/** The request to update */
	private Request toUpdate;
	
	/** Create a a new Update Request Request, to update the specified request on the web service, and notified
	 * 	the specified listener when the request has completed
	 * 
	 * @param toUpdate The request to update
	 * @param listener The listener to notify upon completion
	 */
	
	public UpdateRequestRequest(Request toUpdate, RequestListener<Long> listener) {
		super(listener);
		this.toUpdate = toUpdate;
	}
	
	/** Adds the fetched request to the Request Cache and then notifies the listener of
	 * 	completion
	 * 
	 */
	
	@Override
	public void onCompletion() {
		if (response != toUpdate.getRequestId()) {
			onFailure(new Exception("Updating Request failed, returned id: " + response));
		}
		else {
			RequestCache.getInstance().add(toUpdate);
			onSucess();
		}
	}

	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		Gson gson = new Gson();
		serverRequest = Unirest.put("http://localhost:8080/aft-servlet/manager/request/update")
				.header("Content-Type", "application/json")
				.body(gson.toJson(toUpdate, Request.class));
		
	}
}
