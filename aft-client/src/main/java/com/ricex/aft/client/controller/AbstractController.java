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

	
	/** Executes the given request as an AsyncJson request
	 * 
	 * @param request The request to execute
	 */
	
	protected void makeAsyncRequest(IRequest<?> request) {
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
			this.request = request;					
		}
		
		public void cancelled() {
			try { //Catch any exceptions that are thrown
				request.onCancelled();
			}
			catch (Throwable t) {
				log.error("Cancelled", t);
			}
		}

		public void completed(HttpResponse<JsonNode> response) {	
			try {
				request.processResponse(response.getBody().toString(), response.getCode());
			}
			catch (Throwable t) {
				log.error("Completed", t);
			}
		}

		public void failed(UnirestException e) {
			try {
				request.onFailure(e);
			}
			catch (Throwable t) {
				log.error("Failed", t);
			}
		}
		
	}
}
