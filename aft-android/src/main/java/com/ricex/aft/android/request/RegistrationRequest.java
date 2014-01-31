/**
 * 
 */
package com.ricex.aft.android.request;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.ricex.aft.common.entity.Device;

/** A request to send the devices registration to the server
 * 
 * @author Mitchell Caisse
 *
 */

//TODO: Load the server address from a config, rather than hard code

public class RegistrationRequest extends SpringAndroidSpiceRequest<Long> {

	/** The device to send */
	private final Device device;
	
	/** Creates a new RegistrationRequest with the device to send to the server
	 * 
	 * @param device The device to send
	 */
	public RegistrationRequest(Device device) {
		super(Long.class);
		this.device = device;
	}

	/**
	 * @see com.octo.android.robospice.request.SpiceRequest#loadDataFromNetwork()
	 */
	@Override
	public Long loadDataFromNetwork() throws Exception {	
		String url = "http://192.168.1.160:8080/aft-servlet/manager/device/register";
		return getRestTemplate().postForObject(url, device, Long.class);
	}

}
