package com.ricex.aft.android.request.device;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.common.response.BooleanResponse;

/** Request to check if the device is registered
 * 
 * @author Mitchell Caisse
 *
 */

public class CheckRegistrationRequest extends AbstractRequest<BooleanResponse> {

	/** Creates a new instance of CheckRegistrationRequest
	 * 
	 */
	
	public CheckRegistrationRequest() {
		
	}
	
	protected AFTResponse<BooleanResponse> executeRequest() throws RequestException {
		String deviceUid = getDeviceUID();
		return getForObject(serverAddress + "device/isRegistered/{deviceUid}", BooleanResponse.class, deviceUid);
	}

}
