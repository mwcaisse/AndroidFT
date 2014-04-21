/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		return requestMapper.getRequestsForDeviceWithStatus(deviceUid, RequestStatus.NEW);
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
