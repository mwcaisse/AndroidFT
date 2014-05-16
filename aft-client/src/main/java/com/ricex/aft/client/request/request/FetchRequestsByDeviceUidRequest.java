/**
 * 
 */
package com.ricex.aft.client.request.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class FetchRequestsByDeviceUidRequest extends AbstractRequest<List<Request>> {
	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(FetchRequestsByDeviceUidRequest.class);
	
	/** The id of the request to fetch */
	private String deviceUid;	
	
	/** Creates a new Fetch Request By Id Request to fetch the request with the specified id
	 * 
	 * @param requestId The id of the request to fetch
	 * @param listener The listener to notify when the request is completed
	 */
	
	public FetchRequestsByDeviceUidRequest(String deviceUid, RequestListener<List<Request>> listener) {
		super(listener);
		log.debug("Constructor deviceId: {}", deviceUid);
		this.deviceUid = deviceUid;
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
	
	protected List<Request> convertResponseFromJson(String jsonString) {
		return gson.fromJson(jsonString, new TypeToken<List<Request>>() {}.getType());
	}

	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		log.debug("ConstructServerRequest: deviceId {}", deviceUid);
		serverRequest = Unirest.get(baseServiceUrl + "request/all/{deviceId}")
			.routeParam("deviceId", deviceUid);
	}
}
