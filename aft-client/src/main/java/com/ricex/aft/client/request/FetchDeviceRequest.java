/**
 * 
 */
package com.ricex.aft.client.request;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.ricex.aft.client.cache.DeviceCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class FetchDeviceRequest extends AbstractRequest<Device> {

	/** The device that was returned as a result of this request */
	private Device response;
	
	/**
	 * @param listener
	 */
	
	protected FetchDeviceRequest(RequestListener listener) {
		super(listener);
	}

	public Device getResponse() {
		return response;
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
			onSucess();			
		}
		else {
			onFailure(new Exception("Server returned status code: " + httpStatusCode));
		}
	}

}
