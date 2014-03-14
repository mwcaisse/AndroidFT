/**
 * 
 */
package com.ricex.aft.client.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.ricex.aft.client.request.IRequest;

/**
 * @author Mitchell Caisse
 *
 */
public class AbstractController {

	
	protected void makeAsyncRequest(BaseRequest request, RequestListener listener) {
		
	}
	
	private class ControllerCallback implements Callback<JsonNode> {

		/** The request that this callback is for */
		private IRequest request;
		
		/** Creates a new ControllerCallback that will notify listener of the results of the request
		 * 
		 * @param listener The RequestListener to notify of the results
		 */
		
		private ControllerCallback(IRequest request) {
			this.request = request;
		}
		
		public void cancelled() {
			request.onCancelled();
		}

		public void completed(HttpResponse<JsonNode> response) {
			request.onSucess();
		}

		public void failed(UnirestException e) {
			request.onFailure();
		}
		
	}
}
