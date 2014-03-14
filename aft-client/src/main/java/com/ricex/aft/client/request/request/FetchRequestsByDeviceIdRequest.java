/**
 * 
 */
package com.ricex.aft.client.request.request;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public class FetchRequestsByDeviceIdRequest extends AbstractRequest<List<Request>> {
	
	
	/** The id of the request to fetch */
	private long deviceId;	
	
	/** Creates a new Fetch Request By Id Request to fetch the request with the specified id
	 * 
	 * @param requestId The id of the request to fetch
	 * @param listener The listener to notify when the request is completed
	 */
	
	protected FetchRequestsByDeviceIdRequest(long deviceId, RequestListener listener) {
		super(listener);
		this.deviceId = deviceId;
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
			response = gson.fromJson(new InputStreamReader(rawResponseBody), new TypeToken<List<Request>>() {}.getType());
			RequestCache.getInstance().add(response);
			onSucess();			
		}
		else {
			onFailure(new Exception("Server returned status code: " + httpStatusCode));
		}
	}

	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.get("http://localhost:8080/aft-servlet/manager/all/{deviceId}")
			.routeParam("deviceId", Long.toString(deviceId));
	}
}
