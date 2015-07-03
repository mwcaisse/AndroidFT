/**
 * 
 */
package com.ricex.aft.android.request.device;

import android.os.Build;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.response.LongResponse;

/** Request to register the device with the server
 * 
 * 	Returns a long representing the ID of the newly created device. An ID of less than 0 is
 * 		an invalid device id.
 * 
 * 	If an error occurred while creating the device, and RequestException will be thrown.
 * 
 * @author Mitchell Caisse
 *
 */
public class RegisterDeviceRequest extends AbstractRequest<LongResponse> {

	/** The GCM registration ID of the device to register */
	private String registrationId;
	
	/** Creates a new instance of RegisterDeviceRequest
	 * 
	 * @param registrationId The GCM registration id of the device
	 */
	
	public RegisterDeviceRequest(String registrationId) {
		this.registrationId = registrationId;
	}
	
	@Override
	protected AFTResponse<LongResponse> executeRequest() throws RequestException {
		Device device = createDevice(registrationId);
		return postForObject(serverAddress + "device/register", device, LongResponse.class);
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
