/**
 * 
 */
package com.ricex.aft.android.requester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.content.Context;

import com.ricex.aft.android.AFTProperties;
import com.ricex.aft.common.entity.File;
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
		Request[] requests = restTemplate.getForObject(serverAddress + "request/new/{deviceUid}", Request[].class, getDeviceUID());
		return Arrays.asList(requests);
	}
	
	/** Updates the specified request
	 * 
	 * @param toUpdate The request to update
	 * @return The status value returned
	 */
	
	public long updateRequest(Request toUpdate) {
		addDeviceUploadKey(toUpdate);
		HttpEntity<Request> entity = new HttpEntity<Request>(toUpdate);
		ResponseEntity<LongResponse> res = restTemplate.exchange(serverAddress + "request/update", HttpMethod.PUT, entity, LongResponse.class);
		return res.getBody().getValue();
	}
	
	/** Adds the device upload key to the specified request to upload
	 * 
	 * @param request The request to add the key to
	 */
	private void addDeviceUploadKey(Request request) {
		request.getRequestDevice().setDeviceKey(getOrGenerateUploadKey());
	}	
}
