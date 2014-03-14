/**
 * 
 */
package com.ricex.aft.client.controller;

import com.ricex.aft.client.request.IRequest;

/**
 *  The RequestListener which listens for the results of a request sent to the
 *  	web service
 * @author Mitchell Caisse
 *
 */

public interface RequestListener {

	/** Called when the request has completed sucessfully
	 * 
	 * @param request The request that has been completed
	 */

	
	public void onSucess(IRequest request);
	
	/** Called when the request was canceled
	 * 
	 * @param request The request that was canceled
	 */

	
	public void cancelled(IRequest request);

	/** Called when the request failed, when we received a response other than OK (HTTP 200) from the server, or
	 * 		the request failed due to other reasons 
	 * 
	 * @param request The request that failed
	 * @param e The exception that caused the request to fail
	 */
	
	public void onFailure(IRequest request, Exception e);
	
}
