/**
 * 
 */
package com.ricex.aft.client.request.device;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.DeviceCache;
import com.ricex.aft.client.cache.RequestCache;
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
		//add all of the requests 
		for (Device device: response) {
			RequestCache.getInstance().add(device.getRequests());
		}
		onSucess();	
	}
	
	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.get("http://localhost:8080/aft-servlet/manager/device/all");
		
	}
	
}
