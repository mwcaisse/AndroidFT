/**
 * 
 */
package com.ricex.aft.client.controller;

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
			request.onCancelled();
		}

		public void completed(HttpResponse<JsonNode> response) {
			request.processResponse(response.getRawBody(), response.getCode());
		}

		public void failed(UnirestException e) {
			request.onFailure(e);
		}
		
	}
}
