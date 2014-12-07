/**
 * 
 */
package com.ricex.aft.android.requester;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.aft.android.util.AndroidJsonByteArrayBase64Adapter;
import com.ricex.aft.common.auth.AFTAuthentication;
import com.ricex.aft.common.util.JsonDateMillisecondsEpochDeserializer;

/**
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractRequester {

	/** The context to use to fetch Device UID */
	protected final Context context;
	
	/** The rest template */
	protected RestTemplate restTemplate;
	
	/** The server address */
	protected final String serverAddress;
	
	/** The current Authentication Token */
	private String aftToken;
	
	/** Creates a new Abstract requestor with the specified context
	 * 
	 * @param context The context
	 */
	
	public AbstractRequester(Context context) {
		this.context = context;
		this.serverAddress = "http://fourfivefire.com:8080/aft-servlet/manager/";
		//this.serverAddress = "http://192.168.1.160:8080/aft-servlet/manager/";
		restTemplate = new RestTemplate();
		
		//Create the gson object to decode Json messages
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(byte[].class, new AndroidJsonByteArrayBase64Adapter())
				.create();
		
		//create the Gson message converter for spring, and set its Gson
		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		
		//add the gson message converter to the rest template
		restTemplate.getMessageConverters().add(converter);
				
	}
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	public String getDeviceUID() {		
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return androidId;
	}	
	
	protected <T> T makeRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... urlVariables) {	
		HttpEntity<?> entity = addAuthenticationHeaders(requestEntity);
		ResponseEntity<T> results = restTemplate.exchange(url, method, entity, responseType, urlVariables);		
		return processRequestResponse(results, url, method, requestEntity, responseType, urlVariables);
	}
	
	//TODO: Make an AFTRequest object
	
	/** Processes the results of an AFT Request 
	 * 
	 * @param responseEntity The results of the request 
	 * @param url The url of the request
	 * @param method The method of the request
	 * @param requestEntity The response entity of the request
	 * @param responseType the response type of the request
	 * @param urlVariables The url variables of the request
	 * @return The processed results of the request, the request body or null if there was an error
	 */
	protected <T> T processRequestResponse(ResponseEntity<T> responseEntity, String url, HttpMethod method, HttpEntity<?> requestEntity, 
			ParameterizedTypeReference<T> responseType, Object... urlVariables) {
		
		T res = null;
		//check the status of the response
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			res = responseEntity.getBody();
			//if we need authentication token, extract it from the response
			if (needAuthenticationToken()) {
				extractAuthenticationToken(responseEntity);
			}
		}
		else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			if (needAuthenticationToken()) {
				//401 was returned, and we still need a token
				//TODO: means invalid credentials, raise this up somehow
				res = null; // for now we return null
			}
			else {
				//401 was returned, and we don't need a token,
				// our token has most likely expired, invalidate our current token, and re-request
				invalidateAFTToken();
				res = makeRequest(url, method, requestEntity, responseType, urlVariables);
			}
		}		
		return res;
	}
	
	/** Adds the appropriate Authentication headers to the given HttpEntity. The entity passed in is not modified. New entity
	 * 		is returned.
	 * 
	 * @param entity The HttpEntity to add the request headers
	 * @return The new HttpEntity with the updated Authentication Headers
	 */
	protected HttpEntity<?> addAuthenticationHeaders(final HttpEntity<?> entity) {
		HttpHeaders headers = entity.getHeaders();		
		//check if we have an authentication token
		if (needAuthenticationToken()) {
			//TODO: hard coded for now, update after credential manager is added
			headers.add(AFTAuthentication.AFT_AUTH_INIT_HEADER, "testuser|password");
		}
		else {
			headers.add(AFTAuthentication.AFT_AUTH_TOKEN_HEADER, aftToken);
		}

		return new HttpEntity<Object>(entity.getBody(), headers);
	}
	
	/** Extracts the authentication token from the http response
	 * 
	 * @param entity The http response to extract the token from
	 */
	
	private void extractAuthenticationToken(ResponseEntity<?> entity) {
		String token = entity.getHeaders().getFirst(AFTAuthentication.AFT_AUTH_TOKEN_HEADER);
		//set it as the authorization token, if it was found, and is not empty
		if (token != null && !token.isEmpty()) {
			aftToken = token;
		}
	}
	
	/** Determines if we need an Authentication token or not
	 * 
	 * @return True if we need a token, false otherwise
	 */
	private boolean needAuthenticationToken() {
		return (aftToken == null || aftToken.isEmpty());
	}
	
	/** Invalidates the AFT Token
	 * 
	 */
	private void invalidateAFTToken() {
		aftToken = null;
	}
	
}
