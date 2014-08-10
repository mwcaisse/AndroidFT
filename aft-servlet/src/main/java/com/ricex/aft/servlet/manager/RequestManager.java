/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;
import com.ricex.aft.servlet.mapper.RequestMapper;

/** The Manager for the Request entity. 
 *  Facilitates requests between the RequestController and the database.
 * 
 *  Note that all of the fetch methods, retrieve the Request from the database without the
 *  	requestFile fully populated. The file meta data exists in the requestFile but the 
 *  	contents of the file does not. 
 * 
 * @author Mitchell Caisse
 *
 */
public enum RequestManager {
	
	/** The singleton instance */
	INSTANCE;
	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestManager.class);
	
	/** The request mapper that will be used to fetch requests from the database */
	private RequestMapper requestMapper;
	
	/** Performs initialization */
	
	private RequestManager() {
		
	}
	
	/** Returns the request with the given id
	 * 
	 * @param id The id of the request to fetch
	 * @return The request with the specified id, or null if it doesnt exist
	 */
	
	public Request getRequestById(long id) {
		return requestMapper.getRequestById(id);
	}
	
	/** Returns a list of all requests
	 * 
	 * @return A list of all requests
	 */
	
	public List<Request> getAllRequests() {
		return requestMapper.getAllRequests();
	}
	
	/** Returns a list of all the requests for the device with the specified uid
	 * 
	 * @param deviceUid The UID of the device in a hex string
	 * @return The list of requests for the device
	 */
	
	public List<Request> getRequestsForDevice(String deviceUid) {
		return requestMapper.getRequestsForDevice(deviceUid);
	}
	
	/** Returns a list of unprocessed requests for the specified device
	 * 
	 * @param deviceUid The Unique id of the device in a hex string
	 * @return The list of requests
	 */
	
	public List<Request> getNewRequestsForDevice(String deviceUid) {
		return requestMapper.getRequestsForDeviceWithStatus(deviceUid, RequestStatus.NEW);
	}
	
	/** Saves the given request, and returns the ID of the new request
	 * 
	 * @param request The request to save
	 * @return The id of the request, or -1 if request was invalid
	 */
	
	public long createRequest(Request request) {
		if (isValidRequest(request)) {
			requestMapper.createRequest(request);
			return request.getRequestId();
		}
		return -1; // invalid request
	}
	
	/** Updates the given request
	 * 
	 * @param request The request to update
	 * @return 1 if sucess, -1 if invalid request
	 */
	
	public long updateRequest(Request request) {
		if (isValidRequest(request)) {
			requestMapper.updateRequest(request);
			return 1;
		}
		return -1; //invalid request

	}
	
	/** Determines if the specified request is valid or not.
	 * 
	 * If the request does not have a device, file, file location, or status, it is considered invalid
	 * 
	 * @param request The request to check for validity
	 * @return True if valid, false otherwise
	 */
	
	protected boolean isValidRequest(Request request) {
		//check to make sure it has a device, with a valid device id
		if (request.getRequestDevice() == null || request.getRequestDevice().getDeviceId() < 0) {
			return false; // no device
		}
		//check to make sure the request has atleast one file
		if (request.getRequestFiles().size() == 0) {
			return false; //request must have atleast one file	
		}
		for (File requestFile : request.getRequestFiles()) {
			if (requestFile.getFileId() < 0) {
				return false; // there was an invalid file in the request
			}
		}
		//check to make sure that the request file location is not null, and is not empty
		if (request.getRequestFileLocation() == null || request.getRequestFileLocation().isEmpty()) {
			return false;
		}		
		// check that the request status was set 
		if (request.getRequestStatus() == null) {
			return false; // no request status
		}
		
		return true; // we made it this far we must be valid! woo
	}

	/** Retreives the RequestMppaer that this manager is using to interact with the data store
	 * @return the requestMapper
	 */
	
	public RequestMapper getRequestMapper() {
		return requestMapper;
	}

	/** Sets the RequestMapper that this manager is using to interact with the data store
	 * @param requestMapper the requestMapper to set
	 */
	
	public void setRequestMapper(RequestMapper requestMapper) {
		this.requestMapper = requestMapper;
	}
	
	
}
