/**
 * 
 */
package com.ricex.aft.android.requester;

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
	}
	
	/** Registers the device with the app server
	 * 
	 * @param registrationId The registration ID of the device
	 * @return True if successful, false otherwise
	 */
	
	public boolean registerDevice(String registrationId) {
		Device device = createDevice(registrationId);
		long res = postForObject(serverAddress + "device/register", device, LongResponse.class).getValue();
		return res >= 0;
	}
	
	/** Checks if this device is registered
	 * 
	 * @return True if the device is registered, false otherwise
	 */
	
	public boolean isRegistered() {
		String deviceUid = getDeviceUID();		
		BooleanResponse res = getForObject(serverAddress + "device/isRegistered/{deviceUid}", BooleanResponse.class, deviceUid);		
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
		device.setDeviceRegistrationId(registrationId);
		return device;
	}	

}
