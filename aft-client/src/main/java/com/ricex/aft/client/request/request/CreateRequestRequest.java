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
			response = gson.fromJson(new InputStreamReader(rawResponseBody), Long.class);
			if (response < 0) { //if the server returned an ID less than 0, then the creation failed
				onFailure(new Exception("Creation failed"));
			}
			else {
				toCreate.setRequestId(response);
				RequestCache.getInstance().add(toCreate);
				onSucess();			
			}
		}
		else {
			onFailure(new Exception("Server returned status code: " + httpStatusCode));
		}
	}

	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		Gson gson = new Gson();
		serverRequest = Unirest.post("http://localhost:8080/aft-servlet/manager/request/create")
				.header("Content-Type", "application/json")
				.body(gson.toJson(toCreate, Request.class));
		
	}
	
}
