/**
 * 
 */
package com.ricex.aft.android.requester;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public enum DeviceRequester {
	
	/** The singleton instance of DeviceRequest */
	INSTANCE;

	/** The rest template to use to make requests */
	private final RestTemplate restTemplate;
	
	/** The address of the server to send the requests to */
	private final String serverAddress;
	
	private DeviceRequester() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		serverAddress = "http://192.168.1.160:8080/aft-servlet/manager/";
	}
	
	/** Registers the device with the app server
	 * 
	 * @param registrationId The registration ID of the device
	 * @return True if successful, false otherwise
	 */
	
	public boolean registerDevice(String registrationId) {
		Device device = new Device();
		long res = restTemplate.postForObject("device/register", device, Long.class);
		return res >= 0;
	}
}
