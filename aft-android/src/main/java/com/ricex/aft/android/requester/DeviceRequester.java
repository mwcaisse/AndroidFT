/**
 * 
 */
package com.ricex.aft.android.requester;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.Build;

import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class DeviceRequester extends AbstractRequester {		
	
	public DeviceRequester(Context context) {
		super(context);
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	}
	
	/** Registers the device with the app server
	 * 
	 * @param registrationId The registration ID of the device
	 * @return True if successful, false otherwise
	 */
	
	public boolean registerDevice(String registrationId) {
		Device device = createDevice();
		device.setDeviceRegistrationId(registrationId);
		long res = restTemplate.postForObject(serverAddress + "device/register", device, Long.class);
		return res >= 0;
	}
	
	/** Checks if this device is registered
	 * 
	 * @return True if the device is registered, false otherwise
	 */
	
	public boolean isRegistered() {
		return false;
	}
	
	/** Creates a device that represents this device
	 * 
	 * @return a device
	 */
	
	private Device createDevice() {
		Device device = new Device();
		device.setDeviceName(Build.MODEL);
		device.setDeviceUid(getDeviceUID());		
		return device;
	}		
}
