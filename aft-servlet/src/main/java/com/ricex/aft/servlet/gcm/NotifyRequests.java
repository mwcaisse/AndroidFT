/**
 * 
 */
package com.ricex.aft.servlet.gcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ricex.aft.servlet.gcm.entity.SyncMessage;

/** Notify devices that they have requests available
 * @author Mitchell Caisse
 *
 */

public class NotifyRequests implements Runnable {

	
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
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String,String>();
		headers.add("Content-Type","application/json");
		headers.add("Authorization","key=AIzaSyD0ZBXFhwOc_I1ig50mweBPWpeQiCkmnlw");
		HttpEntity<SyncMessage> entity = new HttpEntity<SyncMessage>(message, headers);
		
		restTemplate.exchange(requestUrl, HttpMethod.POST, entity, Map.class);

		
	}
	
}
