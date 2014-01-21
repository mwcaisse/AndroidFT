/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.List;

import com.ricex.aft.servlet.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public interface RequestMapper {

	
	/** Returns a list of all requests for the device with the specified deviceUid
	 * 
	 * @param deviceUid The unique id of the device
	 * @return List of requests
	 */
	public List<Request> getRequestsForDevice(long deviceUid);
	
	/** Returns a list of all new requests to be processed by the device
	 * 
	 * @param deviceUid The device's unique id
	 * @return List of requests
	 */
	
	public List<Request> getNewRequestsForDevice(long deviceUid);
	
	/** Saves the given request to the database
	 * 
	 * @param request The request to save
	 */
	
	public void createRequest(Request request);
	
	/** Updates the request
	 * 
	 * @param request The request to update
	 */
	
	public void updateRequest(Request request);
	
}
