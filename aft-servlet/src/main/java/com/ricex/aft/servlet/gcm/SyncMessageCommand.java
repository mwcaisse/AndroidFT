/**
 * 
 */
package com.ricex.aft.servlet.gcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ricex.aft.servlet.gcm.entity.SyncMessage;

/** Command to send a SyncMessage to the GCM servers
 * @author Mitchell Caisse
 *
 */

public class SyncMessageCommand implements Runnable {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(SyncMessageCommand.class);
	
	/** The syn message to send */
	private SyncMessage message;
	
	/** Notifies the device with the given registration id that there are request available
	 * 
	 * @param registrationId The registration id
	 */
	
	public SyncMessageCommand(String registrationId) {
		message = new SyncMessage();
		message.setCollapse_key("requests_available");
		List<String> registrationIds = new ArrayList<String>();
		registrationIds.add(registrationId);
		message.setRegistration_ids(registrationIds);
	}

	/** Will send the request to the server 
	 * 
	 */
	
	public void run() {		
		String requestUrl = "https://android.googleapis.com/gcm/send";
		ResponseEntity<Map> response = postMessage(requestUrl, message);      
		parseResponse(response);
	}
	
	/** Sends the message to the GCM server
	 * 
	 * @param requestUrl The URL to send the message to
	 * @param message The message to send
	 * @return The response from the server
	 */
	
	private static ResponseEntity<Map> postMessage(String requestUrl, SyncMessage message) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<SyncMessage> entity = createHttpEntity(message);
		return restTemplate.exchange(requestUrl, HttpMethod.POST, entity, Map.class);
	}
	
	/** Parses the response received from the GCM servers
	 * 
	 * @param response The response object received
	 */
	
	private void parseResponse(ResponseEntity<Map> response) {
		int responseCode = response.getStatusCode().value();
		
		if (responseCode == 200) {
			parseSuccessfulResponse(response);
		}
		else if (responseCode == 400) {
			log.error("Bad Request, request was not formatted correctly");
		}
		else if (responseCode == 401) {
			log.error("Authentication Error, unable to authenticate with GCM server,");
		}
		else if (responseCode < 600 && responseCode >= 500) {
			int retryAfter = Integer.parseInt(response.getHeaders().get("Retry-After").get(0));
			log.info("Internal Server error, lets try again in " + retryAfter);
			
			//TODO: implement a exponential backoff, before we implement a retry policy
		}
		else {
			log.warn("Unhandlded response code: " + response.getStatusCode().toString());
		}	
	}
	
	/** Parses a successful response from the GCM server, to ensure that all of the messages
	 * 	were processed sucessfuly
	 * 
	 * @param response The server response
	 */
	private void parseSuccessfulResponse(ResponseEntity<Map> response) {
		Map responseBody = response.getBody();

		int numFailure = (int)responseBody.get("failure");		
		if (numFailure != 0) {
			log.error("Failed to send sync message to GCM: " + responseBody);
		}
	}
	
	/** Creates the HttpEntity to send the sync message to GCM
	 * 
	 * @param message The SyncMessage to send
	 * @return The HttpEntity created
	 */
	
	private static HttpEntity<SyncMessage> createHttpEntity(SyncMessage message) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String,String>();
		headers.add("Content-Type","application/json");
		headers.add("Authorization","key=AIzaSyD0ZBXFhwOc_I1ig50mweBPWpeQiCkmnlw");
		return new HttpEntity<SyncMessage>(message, headers);
	}
	
}
