package com.ricex.aft.android.request.request;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.common.entity.Request;

/** Request to get the new requests for a device
 * 
 * @author Mitchell Caisse
 *
 */

public class GetNewRequestsForDeviceRequest extends AbstractRequest<Request[]> {

	/** The UID of the device to fetch requests for */
	private String deviceUid;
	
	/** Creates a new instance of GetNewRequestForDeviceRequest
	 * 
	 *  Initializes the deviceUid to the Uid of this device. 
	 */
	
	public GetNewRequestsForDeviceRequest() {
		this(getDeviceUID());
	}
	
	/** Creates a new instance of GetNewrequestsForDeviceRequest
	 * 
	 * @param deviceUid The UID of the device to fetch the requests for
	 */
	public GetNewRequestsForDeviceRequest(String deviceUid) {
		this.deviceUid = deviceUid;
	}

	@Override
	protected AFTResponse<Request[]> executeRequest() throws RequestException {
		return getForObject(serverAddress + "request/new/{deviceUid}", Request[].class, deviceUid);
	}

}
