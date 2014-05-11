package com.ricex.aft.servlet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.response.LongResponse;
import com.ricex.aft.servlet.gcm.MessageExecutor;
import com.ricex.aft.servlet.gcm.SyncMessageCommand;
import com.ricex.aft.servlet.manager.RequestManager;

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
@RequestMapping("/request")
public class RequestController {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestController.class);
	
	/** The request manager to fetch and update requests */
	private RequestManager requestManager;
	
	/** Creates a new Request Controller and sets up the request manager
	 * 
	 */
	
	public RequestController() {
		requestManager = RequestManager.INSTANCE;
	}
	
	/** Retrieves the request with the specified ID from the database
	 * 
	 * Each request returned does not include a full copy of the RequestFile, only the meta-data of
	 *  	the file is populated. This is to conserve bandwidth by not sending the
	 *  	potentially large file contents for each request. The file contents can be 
	 *  	retrieved by using FileController.getFileContents. 
	 *  
	 * @param id The id of the request to fetch
	 * @return The request with the specified id
	 */
	
	@RequestMapping(value="/{id}", method= RequestMethod.GET, produces={"application/json"})
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
	
	@RequestMapping(value="/all", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Request> getRequestsForDevice() {
		return requestManager.getAllRequests();
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
	 * @param deviceUid The unique id (deviceUid) of the device to fetch the requests for.
	 * @return A list containing all of the requests for the specified device.
	 */
	
	@RequestMapping(value="/all/{deviceUid}", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Request> getRequestsForDevice(@PathVariable long deviceUid) {
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
	 * @param deviceUid The unique id (deviceUid) of the device to fetch the requests for
	 * @return A list containing the new requests for the specified device.
	 */
	
	@RequestMapping(value="/new/{deviceUid}", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Request> getNewRequestsForDevice(@PathVariable long deviceUid) {
		return requestManager.getNewRequestsForDevice(deviceUid);
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
	 */
	
	@RequestMapping(value="/create", method= RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody LongResponse createRequest(@RequestBody Request request) {			
		long requestId = requestManager.createRequest(request);
		
		if (requestId > 0) {
			//we created the request without issue
			SyncMessageCommand notify = new SyncMessageCommand(request.getRequestDevice().getDeviceRegistrationId());
			MessageExecutor.INSTANCE.executeNow(notify);
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
	 */
	
	@RequestMapping(value="/update", method= RequestMethod.PUT, consumes={"application/json"})
	public @ResponseBody LongResponse updateRequest(@RequestBody Request request) {
		return new LongResponse(requestManager.updateRequest(request));
	}
	
}
