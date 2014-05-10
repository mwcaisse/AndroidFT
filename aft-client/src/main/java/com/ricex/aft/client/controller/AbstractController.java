/**
 * 
 */
package com.ricex.aft.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ricex.aft.client.request.IRequest;

/**
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractController {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestController.class);
	
	/** Executes the given request as an AsyncJson request
	 * 
	 * @param request The request to execute
	 */
	
	protected void makeAsyncRequest(IRequest<?> request) {
		log.debug("Making the Async call, and registerign the callback controller");
		request.getServerRequest().asJsonAsync(new ControllerCallback(request));
	}
	
	/** Unirest Callback implementation to be used to listen for the results of the executed request
	 * 
	 * @author Mitchell Caisse
	 *
	 */
	
	private class ControllerCallback implements Callback<JsonNode> {

		/** Logger instance */
		private Logger log = LoggerFactory.getLogger(ControllerCallback.class);
		
		/** The request that this callback is for */
		private IRequest<?> request;
		
		/** Creates a new ControllerCallback that will notify listener of the results of the request
		 * 
		 * @param listener The RequestListener to notify of the results
		 */
		
		private ControllerCallback(IRequest<?> request) {
			log.debug("Created a callback controller for request: {}", request);
			this.request = request;					
		}
		
		@Override
		public void cancelled() {
			log.debug("Request cancelled: {}", request);
			try { //Catch any exceptions that are thrown
				request.onCancelled();
			}
			catch (Throwable t) {
				log.error("Cancelled", t);
			}
		}
		
		@Override
		public void completed(HttpResponse<JsonNode> response) {	
			log.debug("Request completed: {}", request);
			try {
				request.processResponse(response.getBody().toString(), response.getCode());
			}
			catch (Throwable t) {
				log.error("Completed", t);
			}
		}
		
		@Override
		public void failed(UnirestException e) {
			log.debug("Request failed: {}", request);
			try {
				request.onFailure(e);
			}
			catch (Throwable t) {
				log.error("Failed", t);
			}
		}
		
	}
}
