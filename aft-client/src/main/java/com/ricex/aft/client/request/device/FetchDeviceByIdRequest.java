/**
 * 
 */
package com.ricex.aft.client.request.device;

import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.DeviceCache;
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
	
	/** Adds the received device and its requests to the Device and Request Cache, then notified the listener
	 * 	the request has completed
	 * 
	 */
	
	@Override
	public void onCompletion() {
		DeviceCache.getInstance().add(response);
		onSucess();
	}
	
	/** Converts the JSON string received from the server, into the correct object for this Request
	 * 
	 * @param jsonString The JSONString containing the object
	 * @return The resulting java object from the JSON string
	 */
	
	protected Device convertResponseFromJson(String jsonString) {
		return new Gson().fromJson(jsonString, Device.class);
	}
	
	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.get(baseServiceUrl + "device/{id}")
			.routeParam("id", Long.toString(deviceId));
	}

}
