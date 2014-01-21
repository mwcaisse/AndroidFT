/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

import com.ricex.aft.servlet.entity.Request;
import com.ricex.aft.servlet.mapper.RequestMapper;

/**
 * @author Mitchell Caisse
 *
 */
public enum RequestManager {

	/** The singleton instance */
	INSTANCE;
	
	/** The request mapper that will be used to fetch requests from the database */
	private RequestMapper requestMapper;
	
	/** Performs initialization */
	
	private RequestManager() {
		
	}
	
	/** Returns a list of all the requests for the device with the specified uid
	 * 
	 * @param deviceUid The UID of the device
	 * @return The list of requests for the device
	 */
	
	public List<Request> getRequestsForDevice(long deviceUid) {
		return requestMapper.getRequestsForDevice(deviceUid);
	}
	
	/** Returns a list of unprocessed requests for the specified device
	 * 
	 * @param deviceUid The Unique id of the device
	 * @return The list of requests
	 */
	
	public List<Request> getNewRequestsForDevice(long deviceUid) {
		return requestMapper.getNewRequestsForDevice(deviceUid);
	}
	
	/** Saves the given request, and returns the ID of the new request
	 * 
	 * @param request The request to save
	 * @return The id of the request
	 */
	
	public long createRequest(Request request) {
		requestMapper.createRequest(request);
		return request.getRequestId();
	}
	
	/** Updates the given request
	 * 
	 * @param request The request to update
	 * @return 1
	 */
	
	public long updateRequest(Request request) {
		requestMapper.updateRequest(request);
		return 1;
	}

	/**
	 * @return the requestMapper
	 */
	
	public RequestMapper getRequestMapper() {
		return requestMapper;
	}

	/**
	 * @param requestMapper the requestMapper to set
	 */
	
	public void setRequestMapper(RequestMapper requestMapper) {
		this.requestMapper = requestMapper;
	}
	
	
}
