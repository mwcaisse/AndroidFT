/**
 * 
 */
package com.ricex.aft.android.requester;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.Build;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.response.BooleanResponse;
import com.ricex.aft.common.response.LongResponse;

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
		Device device = createDevice(registrationId);
		long res = restTemplate.postForObject(serverAddress + "device/register", device, LongResponse.class).getValue();
		return res >= 0;
	}
	
	/** Checks if this device is registered
	 * 
	 * @return True if the device is registered, false otherwise
	 */
	
	public boolean isRegistered() {
		String deviceUid = getDeviceUID();		
		BooleanResponse res = restTemplate.getForObject(serverAddress + "device/isRegistered/{deviceUid}", BooleanResponse.class, deviceUid);		
		return res.getValue();
	}
	
	/** Creates a device that represents this device
	 * 
	 * @param registrationId The GCM Registration ID of this device
	 * @return a device
	 */
	
	private Device createDevice(String registrationId) {
		Device device = new Device();
		device.setDeviceName(Build.MODEL);
		device.setDeviceUid(getDeviceUID());	
		return device;
	}	

}
