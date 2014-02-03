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

/** Notify devices that they have requests available
 * @author Mitchell Caisse
 *
 */

public class NotifyRequests implements Runnable {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(NotifyRequests.class);
	
	/** The syn message to send */
	private SyncMessage message;
	
	/** Notifies the device with the given registration id that there are request available
	 * 
	 * @param registrationId The registration id
	 */
	
	public NotifyRequests(String registrationId) {
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
	 * @param message The message to se nd
	 * @return The response from the server
	 */
	
	private static ResponseEntity<Map> postMessage(String requestUrl, SyncMessage message) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<SyncMessage> entity = createHttpEntity(message);
		return restTemplate.exchange(requestUrl, HttpMethod.POST, entity, Map.class);
	}
	
	private void parseResponse(ResponseEntity<Map> response) {
		int responseCode = response.getStatusCode().value();
		
		if (responseCode == 200) {
			log.debug("Request sent to GCM server without issue, status 200 OK");
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
		}
		else {
			log.warn("Unhandlded response code: " + response.getStatusCode().toString());
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
