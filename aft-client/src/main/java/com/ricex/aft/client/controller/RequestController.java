/**
 * 
 */
package com.ricex.aft.client.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.request.request.CreateRequestRequest;
import com.ricex.aft.client.request.request.FetchAllRequestsRequest;
import com.ricex.aft.client.request.request.FetchRequestByIdRequest;
import com.ricex.aft.client.request.request.FetchRequestsByDeviceUidRequest;
import com.ricex.aft.client.request.request.UpdateRequestRequest;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;

/** The controller for handling fetches for requests from the web service
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestController extends AbstractController {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestController.class);
	
	/** The singleton instance of this controller */
	private static RequestController _instance;
	
	/** Returns the singleton instance of this class
	 * 
	 * @return The singleton instance
	 */
	
	public static RequestController getInstance() {
		if (_instance == null) {
			_instance = new RequestController();
		}
		return _instance;
	}
	
	/** Creates a new request controller
	 * 
	 */
	
	private RequestController() {
		
	}
	
	/** Fetches the request with the specified ID from the web service
	 * 
	 * @param id The id of the request to request
	 * @param listener The listener to notify of the results of the request
	 * @return The request that was created
	 */
	
	public IRequest<Request> get(long id, RequestListener<Request> listener) {
		FetchRequestByIdRequest request = new FetchRequestByIdRequest(id, listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Fetches all of the requests from the web service
	 * 
	 * @param listener The listener to notify of the results of the request
	 * @return The request that was created
	 */
	
	public IRequest<List<Request>> getAll(RequestListener<List<Request>> listener) {
		FetchAllRequestsRequest request = new FetchAllRequestsRequest(listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Fetches all of the requests that belong to the specified device
	 * 
	 * @param deviceId The UID of the device to fetch the requests for
	 * @param listener The listener to notify of completion of the request
	 * @return The request that was created
	 */
	
	public IRequest<List<Request>> getAllForDevice(long deviceUid, RequestListener<List<Request>> listener) {
		FetchRequestsByDeviceUidRequest request = new FetchRequestsByDeviceUidRequest(deviceUid, listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Creates a new request
	 * 
	 * @param toCreate The request to create
	 * @param listener The listener to notify of the results of the request
	 * @return The request that was created
	 */
	
	public IRequest<Long> createRequest(Request toCreate, RequestListener<Long> listener) {
		log.debug("Creating the CreateRequestRequest..");
		CreateRequestRequest request = new CreateRequestRequest(cloneRequestWithoutFileContents(toCreate), listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Updates the specified request
	 * 
	 * @param toUpdate The request to update
	 * @param listener The listener to notify of the results of the request
	 * @return The request that was created
	 */
	
	public IRequest<Long> updateRequest(Request toUpdate, RequestListener<Long> listener) {
		UpdateRequestRequest request = new UpdateRequestRequest(cloneRequestWithoutFileContents(toUpdate), listener);
		makeAsyncRequest(request);
		return request;
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
