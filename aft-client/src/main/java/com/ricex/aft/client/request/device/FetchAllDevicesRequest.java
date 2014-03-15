/**
 * 
 */
package com.ricex.aft.client.request.device;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.DeviceCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class FetchAllDevicesRequest extends AbstractRequest<List<Device>> {

	/** Creates a new FetchAllDevices Request with the specified listener
	 * 
	 * @param listener The request listener to be notified when the request is completed
	 */
	
	public FetchAllDevicesRequest(RequestListener<List<Device>> listener) {
		super(listener);
	}
	
	/** Adds the devices and thier requests to the Device and Request Cache and then notifies the
	 * 	listener of the completion
	 */
	
	public void onCompletion() {
		DeviceCache.getInstance().add(response);
		onSucess();	
	}
	
	/** Converts the JSON string received from the server, into the correct object for this Request
	 * 
	 *  It will be called by processResponse to properly parse the response
	 * 
	 * @param jsonString The JSONString containing the object
	 * @return The resulting java object from the JSON string
	 */
	
	protected List<Device> convertResponseFromJson(String jsonString) {
		return new Gson().fromJson(jsonString, new TypeToken<List<Device>>() {}.getType());
	}
	
	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.get(baseServiceUrl + "device/all");
		
	}	
}
