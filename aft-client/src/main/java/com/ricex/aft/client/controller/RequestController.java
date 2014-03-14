/**
 * 
 */
package com.ricex.aft.client.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

/** The controller for handling fetches for requests from the web service
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestController {

	public RequestController() {
		
	}
	
	/** Fetches the request with the specifeid ID from the web service
	 * 
	 * @param id The id of the request to request
	 */
	
	public void get(long id) {
		Unirest.get("http://localhost:8080/aft-servlet/manager/request/{id}")
			.routeParam("id", Long.toString(id)).asJsonAsync(new Callback<JsonNode>() {

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void completed(HttpResponse<JsonNode> response) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void failed(UnirestException e) {
					// TODO Auto-generated method stub
					
				}
				
			});
	
	}
}
