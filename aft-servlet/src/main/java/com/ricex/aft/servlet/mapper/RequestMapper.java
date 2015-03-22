/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;

/** Request Mapper for fetching request data
 * 
 * @author Mitchell Caisse
 *
 */
public interface RequestMapper {
	
	/** Returns the request with the given id
	 * 
	 * @param requestId The id of the request to fetch
	 * @return The request with the given id
	 */

	public Request getRequestById(long requestId);
	
	/** Returns a list of all requests
	 * 
	 * @return A list of all requests
	 */
	
	public List<Request> getAllRequests();
	
	/** Returns a list of all requests owned by the given user
	 * 
	 * @param userId The id of the user
	 * @return A list of all the requests owned by the user
	 */
	public List<Request> getAllRequestsByUser(long userId);
	
	/** Returns a list of all requests owned by the given user and with the specified statuses
	 * 
	 * @param userId The id owner of the requests
	 * @param statuses The statuses of the requests to fetch
	 * @return The list of requests owned by the specified owners, with the specified statuses.
	 */
	public List<Request> getAllRequestsByUserAndStatus(@Param ("userId") long userId, @Param("statuses") RequestStatus[] statuses);
	
	/** Returns a list of requests that belong to the specified user and have been updated between the specified date range
	 * 
	 * @param userId The id of the user
	 * @param startDate The starting date of the range
	 * @param endDate The ending date of the range
	 * @return The requests that have been updated between the date range
	 */	
	public List<Request> getRequestsByUserInRange(@Param ("userId") long userId,
												  @Param ("startDate") Date startDate,
												  @Param ("endDate") Date endDate);
	
	/** Returns a list of all requests for the device with the specified deviceUid
	 * 
	 * @param deviceUid The unique id of the device in a hex string
	 * @return List of requests
	 */
	
	public List<Request> getRequestsForDevice(String deviceUid);
	
	/** Returns a list of all requests with the given status to fetch for the specified device
	 * 
	 * @param deviceUid The device's unique id in a hex string
	 * @param status The status of the request to fetch
	 * @return List of requests
	 */
	
	public List<Request> getRequestsForDeviceWithStatus(@Param("deviceUid") String deviceUid, @Param("status") RequestStatus status);
	
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
