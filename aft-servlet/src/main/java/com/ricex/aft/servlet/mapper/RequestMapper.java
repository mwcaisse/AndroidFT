/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ricex.aft.servlet.entity.Request;
import com.ricex.aft.servlet.entity.RequestStatus;

/** Request Mapper for fetching requests from the database
 * 
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
	
	/** Returns a list of all requests with the given status to fetch for the specified device
	 * 
	 * @param deviceUid The device's unique id
	 * @param status The status of the request to fetch
	 * @return List of requests
	 */
	
	public List<Request> getRequestsForDeviceWithStatus(@Param("deviceUid") long deviceUid, @Param("status") RequestStatus status);
	
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
