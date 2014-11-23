/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;
import com.ricex.aft.servlet.entity.exception.EntityException;
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
	
	/** The FileManager to use to update request files */
	private FileManager fileManager;
	
	/** The DeviceManager to use to update request files */
	private DeviceManager deviceManager;
	
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
	
	/** Returns a list of all requests owned by the specified user
	 * 
	 * @param userId The id of the user
	 * @return A list of all the requests owned by the user
	 */
	public List<Request> getAllRequestsByUser(long userId) {
		return requestMapper.getAllRequestsByUser(userId);
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
	 * @throws EntityException if the request is invalid
	 */
	
	public long createRequest(Request request) throws EntityException {
		if (isValidRequest(request)) {
			//save the request to the database
			requestMapper.createRequest(request);	
			//update the files for the request
			fileManager.updateFilesForRequest(request);
			return request.getRequestId();
		}
		return -1; // invalid request
	}
	
	/** Updates the given request
	 * 
	 * @param request The request to update
	 * @return The id of the request if success, -1 if invalid request
	 * @throws EntityException if the request is invalid
	 */
	
	public long updateRequest(Request request) throws EntityException {
		if (isValidRequest(request)) {			
			//set the request to be updated now
			request.setRequestUpdated(new Date());			
			//update the request in the database
			requestMapper.updateRequest(request);
			//update the files for the request
			fileManager.updateFilesForRequest(request);
			return request.getRequestId();
		}
		return -1; //invalid request
	}
	
	/** Determines if the specified request is valid or not.
	 * 
	 * If the request does not have a device, file, or status, it is considered invalid
	 * 
	 * @param request The request to check for validity
	 * @return True if valid
	 * @throws EntityException if the request is invalid
	 */
	
	protected boolean isValidRequest(Request request) throws EntityException {
		//check to make sure it has a device, with a valid device id
		Device requestDevice = request.getRequestDevice();
		if (!deviceManager.deviceExists(requestDevice.getDeviceId())) {
			throw new EntityException("The specified device does not exist");
		}
		//check to make sure the request has atleast one file
		if (request.getRequestFiles().size() == 0) {
			throw new EntityException("A request must contain atleast one file");
		}
		for (File requestFile : request.getRequestFiles()) {
			if (requestFile.getFileId() < 0) {
				throw new EntityException("Invalid file");
			}
		}	
		// check that the request status was set 
		if (request.getRequestStatus() == null) {
			throw new EntityException("The request must have a status");
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

	/**
	 * @return the fileManager
	 */
	
	public FileManager getFileManager() {
		return fileManager;
	}

	/**
	 * @param fileManager the fileManager to set
	 */
	
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	/**
	 * @return the deviceManager
	 */
	
	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	/**
	 * @param deviceManager the deviceManager to set
	 */
	
	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}
	
	
	
}
