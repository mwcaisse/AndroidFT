/**
 * 
 */
package com.ricex.aft.client.request.request;

import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;

/**
 *  Request to update a request on the web service
 *  
 * @author Mitchell Caisse
 *
 */

public class UpdateRequestRequest extends AbstractRequest<Long> {
	
	/** The request to update */
	private Request toUpdate;
	
	/** Create a a new Update Request Request, to update the specified request on the web service, and notified
	 * 	the specified listener when the request has completed
	 * 
	 * @param toUpdate The request to update
	 * @param listener The listener to notify upon completion
	 */
	
	public UpdateRequestRequest(Request toUpdate, RequestListener<Long> listener) {
		super(listener);
		this.toUpdate = toUpdate;
	}
	
	/** Adds the fetched request to the Request Cache and then notifies the listener of
	 * 	completion
	 * 
	 */
	
	@Override
	public void onCompletion() {
		if (response != toUpdate.getRequestId()) {
			onFailure(new Exception("Updating Request failed, returned id: " + response));
		}
		else {
			RequestCache.getInstance().add(toUpdate);
			onSucess();
		}
	}

	/** Converts the JSON string received from the server, into the correct object for this Request
	 * 
	 * @param jsonString The JSONString containing the object
	 * @return The resulting java object from the JSON string
	 */
	
	protected Long convertResponseFromJson(String jsonString) {
		return gson.fromJson(jsonString, Long.class);
	}

	/** Constructs the Unirest request that will be used to fetch the device from the web service
	 * 
	 */
	
	protected void constructServerRequest() {				
		serverRequest = Unirest.put(baseServiceUrl + "request/update")
				.header("Content-Type", "application/json")
				.body(gson.toJson(cloneRequestWithoutFileContents(toUpdate), Request.class));
		
	}
	
	/** Creates a clone of the specified reqwuest without the file contents
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
