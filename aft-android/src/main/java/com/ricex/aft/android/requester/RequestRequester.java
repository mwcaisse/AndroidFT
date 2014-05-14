/**
 * 
 */
package com.ricex.aft.android.requester;

import java.util.List;

import android.content.Context;

import com.ricex.aft.android.container.RequestContainer;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;


/** Requester for Requests
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestRequester extends AbstractRequestor {	

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
		RequestContainer container = restTemplate.getForObject(serverAddress + "request/new/{deviceUid}", RequestContainer.class, getDeviceUID());
		return container.getRequests();
	}
	
	/** Updates the specified request
	 * 
	 * @param toUpdate The request to update
	 * @return The status value returned
	 */
	
	public long updateRequest(Request toUpdate) {
		Request cloned = cloneRequestWithoutFileContents(toUpdate);
		return 0;
	}
	
	/** Creates a clone of the specified request without the file contents
	 * 
	 * @param request The request to clone
	 * @return
	 */
	
	private Request cloneRequestWithoutFileContents(Request orig) {
		Request dest = new Request();
		dest.setRequestDevice(orig.getRequestDevice());
		dest.setRequestFileLocation(orig.getRequestFileLocation());
		dest.setRequestId(orig.getRequestId());
		dest.setRequestStatus(orig.getRequestStatus());
		dest.setRequestUpdated(orig.getRequestUpdated());
		
		File reqFile = new File();
		reqFile.setFileId(orig.getRequestFile().getFileId());
		reqFile.setFileName(orig.getRequestFile().getFileName());
		dest.setRequestFile(reqFile);
		
		return dest;
	}
	
}
