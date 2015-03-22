package com.ricex.aft.servlet.controller.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestDirectory;
import com.ricex.aft.common.entity.RequestStatus;
import com.ricex.aft.common.response.LongResponse;
import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.entity.UserRole;
import com.ricex.aft.servlet.entity.exception.AuthorizationException;
import com.ricex.aft.servlet.entity.exception.EntityException;
import com.ricex.aft.servlet.manager.DeviceManager;
import com.ricex.aft.servlet.manager.RequestManager;
import com.ricex.aft.servlet.notifier.DeviceNotifier;

/**
 *  SpringMVC Controller for Requests.
 *  
 *   Deals with requests having to do with requests including the creation of new requests,
 *   	updating of old requests, and retrieving requests on an individual or by device basis.
 * 
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/api/request")
public class RequestController extends ApiController {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestController.class);
	
	/** The request manager to fetch and update requests */
	private RequestManager requestManager;
	
	/** The Device Manager to use to perform operations on device */
	private DeviceManager deviceManager;
	
	/** The device notifier to use to notify a device it has pending request */
	private DeviceNotifier deviceNotifier;
	
	/** Creates a new Request Controller
	 * 
	 */
	
	public RequestController() {

	}
	
	/** Retrieves the request with the specified ID from the database
	 * 
	 * Each request returned does not include a full copy of the RequestFiles, only the meta-data of
	 *  	the files is populated. This is to conserve bandwidth by not sending the
	 *  	potentially large file contents for each request. The file contents can be 
	 *  	retrieved by using FileController.getFileContents. 
	 *  
	 * @param id The id of the request to fetch
	 * @return The request with the specified id
	 */
	
	@RequestMapping(value="/{id}", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Request getRequestById(@PathVariable long id) {
		return requestManager.getRequestById(id);
	}
	
	/** Retrieves a list of all requests currently in the database
	 * 
	 * Each request returned does not include a full copy of the RequestFile, only the meta-data of
	 *  	the file is populated. This is to conserve bandwidth by not sending the
	 *  	potentially large file contents for each request. The file contents can be 
	 *  	retrieved by using FileController.getFileContents. 
	 *    
	 * @return A list of all requests.
	 */
	
	@RequestMapping(value="/all", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Request> getAllRequests() {
		return requestManager.getAllRequests();
	}
	
	/** Retrieves a list of all belonging to the current user
	 * 
	 * Each request returned does not include a full copy of the RequestFile, only the meta-data of
	 *  	the file is populated. This is to conserve bandwidth by not sending the
	 *  	potentially large file contents for each request. The file contents can be 
	 *  	retrieved by using FileController.getFileContents. 
	 *    
	 * @return A list of all requests.
	 */
	@RequestMapping(value="/mine", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Request> getAllMyRequests() {
		return requestManager.getAllRequestsByUser(getCurrentUser().getId());
	}
	
	/** Retrieves a list of all requests belonging to the current user, with one of the specified statuses
	 * 
	 * @param statuses The list of statuses to fetch requests in
	 * @return The requests that are in the specified statuses
	 */
	
	@RequestMapping(value="/mine-by-status", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Request> getAllMyRequestsByStatus(@RequestParam(value = "status") RequestStatus[] statuses) {
		return requestManager.getAllRequestsByUserAndStatus(getCurrentUser().getId(), statuses);
	}
	
	/** Retrieves a list of all requests belonging to the current user that have been updated within the past 2 weeks
	 * 
	 * @return List of all recent requests
	 */
	
	//TODO: Add a paramater to specify the date range of the requests Issue #38
	@RequestMapping(value="/mine-recent", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Request> getAllMyRecentRequests() {
		//start date is two weeks before now
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.WEEK_OF_YEAR, -2);
		
		// end date is the current date
		Calendar endDate = Calendar.getInstance();
		
		return requestManager.getRequestsByUserBetweenRange(getCurrentUser().getId(), startDate, endDate);
	}
	
	
	/** Returns a list of all request associated with the specified device.
	 * 
	 *  This method is designed to be able to be called from a device without the device
	 *  	having a copy of its device object, or internally generated database id. 
	 * 
	 * Each request returned does not include a full copy of the RequestFile, only the meta-data of
	 *  	the file is populated. This is to conserve bandwidth by not sending the
	 *  	potentially large file contents for each request. The file contents can be 
	 *  	retrieved by using FileController.getFileContents. 
	 * 
	 * @param deviceUid The unique id (deviceUid) of the device to fetch the requests for. Should be in a hex string.
	 * 	(Base 16 representation of the long)
	 * @return A list containing all of the requests for the specified device.
	 */
	
	@RequestMapping(value="/all/{deviceUid}", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Request> getRequestsForDevice(@PathVariable String deviceUid) {
		return requestManager.getRequestsForDevice(deviceUid);
	}
	
	
	
	/** Returns a list of all new (unprocessed) requests associated with the specified device.
	 * 	
	 * 	A request is considered new (unprocessed) if its status is set to RequestStatus.NEW 
	 *  This method is designed to be able to be called from a device without the device
	 *  	having a copy of its device object, or internally generated database id. 
	 *  
	 * Each request returned does not include a full copy of the RequestFile, only the meta-data of
	 *  	the file is populated. This is to conserve bandwidth by not sending the
	 *  	potentially large file contents for each request. The file contents can be 
	 *  	retrieved by using FileController.getFileContents. 
	 * 
	 * @param deviceUid The unique id (deviceUid) of the device to fetch the requests for. Should be in a hex string.
	 * 	(Base 16 representation of the long)
	 * @return A list containing the new requests for the specified device.
	 */
	
	@RequestMapping(value="/new/{deviceUid}", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Request> getNewRequestsForDevice(@PathVariable String deviceUid) {
		return requestManager.getNewRequestsForDevice(deviceUid);
	}
	
	/** Returns the list of all possible request directories
	 * 
	 * @return The list of all possible request directories
	 */
	
	@RequestMapping(value="/directories", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<String> getRequestDirectories() {
		List<String> requestDirectories = new ArrayList<String>();
		for (RequestDirectory directory : RequestDirectory.values()) {
			requestDirectories.add(directory.toString());
		}
		return requestDirectories;
	}
	
	/** Returns the list of all possible request statuses
	 * 
	 * @return The list of all request statuses
	 */
	
	@RequestMapping(value="/statuses", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<RequestStatus> getRequestStatuses() {	
		return Arrays.asList(RequestStatus.values());
	}
	
	/** Creates the given request. 
	 * The File for this request must be created separately from the request,
	 * 		and must be created before the request is created. The requestFile field, must contain the fileId
	 * 		field of the file relating to this request. Containing more information will not cause this method
	 * 		to fail, but only the requestFile.fileId is used and is necessary.
	 * 
	 * 	If the file is not created before the request and does not exists, then this method will fail.
	 * 
	 * @param request The request to create, with an undefined id
	 * @return The id of the newly created request
	 * @throws EntityException when the request passed in is invalid
	 */
	
	@RequestMapping(value="/create", method= RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody LongResponse createRequest(@RequestBody Request request) throws EntityException {		
		//set the owner of the request
		request.setRequestOwner(getCurrentUser().toUserInfo());
		long requestId = requestManager.createRequest(request);			
		//if the request saved properly, notify the device it has a new request
		if (requestId > 0) {
			notifyDeviceOfRequest(request);
		}		
		return new LongResponse(requestId);
	}
	
	/** Updated the specified request with the request given.
	 * 
	 *  The data currently stored will be replaced with all of the data inside of the Request. If
	 *  	any fields are NULL then they will be stored as NULL in the database as well. It is 
	 *  	recommended to have an updated copy of the request object you wish to update, copy
	 *  	the changes into it, and submit that as the request to update. This will ensure no
	 *  	fields are improperly set / updated.
	 * 
	 * @param request The fully populated request to update
	 * @return The id of the updated request
	 * @throws EntityException when the request passed in is invalid
	 */
	
	@RequestMapping(value="/update", method= RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody LongResponse updateRequest(@RequestBody Request request) throws EntityException, AuthorizationException {
		if (canUserModifyRequest(request.getId(), getCurrentUser())) {
			return new LongResponse(requestManager.updateRequest(request));
		}
		else {
			throw new AuthorizationException("You are not authorized to make changes to this request. Only the owner can make changes");
		}
	}
	
	/** Notifies the device associated with the given request, about the pending request
	 * 
	 * @param request The request to notify about
	 */
	private void notifyDeviceOfRequest(Request request) {
		Device device = deviceManager.getDevice(request.getRequestDevice().getId());
		deviceNotifier.notifyDevice(device);
	}
	
	/** Determines if the specified user can modify the device with the given id
	 * 
	 * @param deviceId The id of the device
	 * @param user The user to check
	 * @return True if the user can modify the device, false otherwise
	 */
	protected boolean canUserModifyRequest(long requestId, User user) {
		if (user.userHasRole(UserRole.ROLE_ADMIN)) {
			return true;
		}
		//get the request from the database
		Request request = requestManager.getRequestById(requestId);
		//user can modify the request if it was found, and they are the owner
		return request != null && user.equals(request.getRequestOwner());
	}

	/**
	 * @return the deviceNotifier
	 */
	
	public DeviceNotifier getDeviceNotifier() {
		return deviceNotifier;
	}

	/**
	 * @param deviceNotifier the deviceNotifier to set
	 */
	
	public void setDeviceNotifier(DeviceNotifier deviceNotifier) {
		this.deviceNotifier = deviceNotifier;
	}

	/**
	 * @return the requestManager
	 */
	
	public RequestManager getRequestManager() {
		return requestManager;
	}

	/**
	 * @param requestManager the requestManager to set
	 */
	
	public void setRequestManager(RequestManager requestManager) {
		this.requestManager = requestManager;
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
