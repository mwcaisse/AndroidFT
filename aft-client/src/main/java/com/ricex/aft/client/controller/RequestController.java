/**
 * 
 */
package com.ricex.aft.client.controller;

import java.util.List;

import com.ricex.aft.client.request.request.FetchRequestByIdRequest;
import com.ricex.aft.common.entity.Request;

/** The controller for handling fetches for requests from the web service
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestController extends AbstractController {

	/** The singleton instance of this controller */
	private static RequestController _instance;
	
	/** Returns the singleton instance of this class
	 * 
	 * @return The singleton instance
	 */
	
	public static RequestController getIntance() {
		if (_instance == null) {
			_instance = new RequestController();
		}
		return _instance;
	}
	
	/** Creates a new request controller
	 * 
	 */
	
	private RequestController() {
		
	}
	
	/** Fetches the request with the specified ID from the web service
	 * 
	 * @param id The id of the request to request
	 * @param listener The listener to notify of the results of the request
	 */
	
	public void get(long id, RequestListener<Request> listener) {
		FetchRequestByIdRequest request = new FetchRequestByIdRequest(id, listener);
		makeAsyncRequest(request);
	
	}
	
	/** Fetches all of the requests from the web service
	 * 
	 * @param listener The listener to notify of the results of the request
	 */
	
	public void getAll(RequestListener<List<Request>> listener) {
		
	}


}
