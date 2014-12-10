/**
 * 
 */
package com.ricex.aft.android.requester;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import android.content.Context;
import android.util.Log;

import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.response.LongResponse;


/** Requester for Requests
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestRequester extends AbstractRequester {	

	/** Creates a new instance of Request Requester
	 * 
	 */
	public RequestRequester(Context context) {
		super(context);
	}
	
	/** Retrieves a list of all new requests for this device
	 * 
	 * @return The list of new requests
	 */
	
	public List<Request> getNewRequestsForDevice() {
		Request[] requests = getForObject(serverAddress + "request/new/{deviceUid}", Request[].class, getDeviceUID());
		if (requests.length > 0) {
			Log.i("AFT-RR", "Request: " + gson.toJson(requests[0]));
		}
		return Arrays.asList(requests);
	}
	
	/** Updates the specified request
	 * 
	 * @param toUpdate The request to update
	 * @return The status value returned
	 */
	
	public long updateRequest(Request toUpdate) {
		HttpEntity<Request> entity = new HttpEntity<Request>(toUpdate);
		LongResponse res = makeRequest(serverAddress + "request/update", HttpMethod.PUT, entity, LongResponse.class);
		return res.getValue();
	}	
	
}

