/**
 * 
 */
package com.ricex.aft.android.container;

import java.util.ArrayList;

import com.ricex.aft.common.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public class RequestContainer {

	/** The requests in this container */
	private ArrayList<Request> requests;
	
	/** Creates a new Request Container
	 * 
	 */
	
	public RequestContainer() {
		
	}
	
	/** Retrieves the requests
	 * 
	 * @return The requests
	 */
	
	public ArrayList<Request> getRequests() {
		return requests;
	}
	
	/** Sets the requests
	 * 
	 * @param requests The requests to set
	 */
	
	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}
	
}
