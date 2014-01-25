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

import com.google.gson.Gson;
import com.ricex.aft.servlet.entity.Request;
import com.ricex.aft.servlet.manager.RequestManager;

/**
 *  Request Controller, for dealing with requests having to do with requests
 *   -Creation of new requests and updating of old requests
 *   -Retrieving the list of requests for a specific device
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
	
	public RequestController() {
		requestManager = RequestManager.INSTANCE;
	}
	
	/** Returns a list of all requests for a specified device
	 * 
	 * @param deviceId The unique id of the device
	 */
	@RequestMapping(value="/all/{deviceUid}", method= RequestMethod.GET)
	public @ResponseBody List<Request> getRequestsForDevice(@PathVariable long deviceUid) {
		return requestManager.getRequestsForDevice(deviceUid);
	}
	
	/** Returns a list of all new (unprocessed requests) for the specified device
	 * 
	 * @param deviceId The unique id of the device
	 */
	
	@RequestMapping(value="/new/{deviceUid}", method= RequestMethod.GET)
	public @ResponseBody List<Request> getNewRequestsForDevice(@PathVariable long deviceUid) {
		return requestManager.getNewRequestsForDevice(deviceUid);
	}
	
	/** Creates the given request
	 * 
	 * @param request The request to create, with an undefined id
	 * @return The id of the newly created request
	 */
	
	@RequestMapping(value="/create", method= RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody long createRequest(@RequestBody Request request) {			
		return requestManager.createRequest(request);
	}
	
	/** Updates the given request
	 * 
	 * @param request The request to update
	 * TODO: Might want to make this a status code instead..
	 * @return The id of the updated request
	 */
	
	@RequestMapping(value="/update", method= RequestMethod.PUT, consumes={"application/json"})
	public @ResponseBody long updateRequest(@RequestBody Request request) {
		return requestManager.updateRequest(request);
	}
	
}
