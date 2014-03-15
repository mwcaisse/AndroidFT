/**
 * 
 */
package com.ricex.aft.client.request.device;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.DeviceCache;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Device;

/** Request to fetch a device by an id
 * @author Mitchell Caisse
 *
 */
public class FetchDeviceByIdRequest extends AbstractRequest<Device> {

	/** The id of the device to fetch */
	private long deviceId;
	
	/**
	 * @param deviceId The id of the device to fetch
	 * @param listener The listener to notify of the results
	 */
	
	public FetchDeviceByIdRequest(long deviceId, RequestListener<Device> listener) {
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
			response = gson.fromJson(new InputStreamReader(rawResponseBody), Device.class);
			DeviceCache.getInstance().add(response);
			RequestCache.getInstance().add(response.getRequests());
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
		serverRequest = Unirest.get("http://localhost:8080/aft-servlet/manager/device/{id}")
			.routeParam("id", Long.toString(deviceId));
	}

}
