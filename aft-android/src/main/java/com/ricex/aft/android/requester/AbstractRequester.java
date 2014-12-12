/**
 * 
 */
package com.ricex.aft.android.requester;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.aft.android.AFTConfigurationProperties;
import com.ricex.aft.android.util.AndroidJsonByteArrayBase64Adapter;
import com.ricex.aft.common.auth.AFTAuthentication;
import com.ricex.aft.common.entity.UserInfo;
import com.ricex.aft.common.util.JsonDateMillisecondsEpochDeserializer;
import com.ricex.aft.common.util.UserInfoAdapter;

/**
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractRequester {

	/** The context to use to fetch Device UID */
	protected final Context context;
	
	/** The rest template */
	private RestTemplate restTemplate;
	
	/** The server address */
	protected final String serverAddress;

	/** The security context to use */
	private SecurityContext securityContext;
	
	/** Creates a new Abstract requestor with the specified context
	 * 
	 * @param context The context
	 */
	
	public AbstractRequester(Context context) {
		this.context = context;
		//this.serverAddress = "https://home.fourfivefire.com/aft-servlet/api/";
		//this.serverAddress = "http://192.168.1.160:8888/aft-servlet/api/";
		this.serverAddress = AFTConfigurationProperties.getServerAddress() + "api/";
		
		this.securityContext = SecurityContext.INSTANCE;
		
		//Create the gson object to decode Json messages
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(byte[].class, new AndroidJsonByteArrayBase64Adapter())
				.registerTypeAdapter(UserInfo.class, new UserInfoAdapter())
				.create();
		
		//create the Gson message converter for spring, and set its Gson
		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		
		//add the gson message converter to the rest template
		restTemplate = new RestTemplate();	
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		
		/*
		String jsonNewRequest = "{\"requestId\":1,\"requestName\":\"Test Request\",\"requestFiles\":[{\"fileId\":3,\"requestId\":1,\"fileName\":\"123.png\",\"fileSize\":155686,\"fileOwner\":{\"userId\":4,\"username\":\"testuser\",\"name\":\"testuser\"}},{\"fileId\":4,\"requestId\":1,\"fileName\":\"10-5-13_YO.png\",\"fileSize\":4811700,\"fileOwner\":{\"userId\":4,\"username\":\"testuser\",\"name\":\"testuser\"}},{\"fileId\":5,\"requestId\":1,\"fileName\":\"10-5-13_YO2.png\",\"fileSize\":4695840,\"fileOwner\":{\"userId\":4,\"username\":\"testuser\",\"name\":\"testuser\"}}],\"requestFileLocation\":\"/tst/\",\"requestDirectory\":\"PICTURES\",\"requestStatus\":\"New\",\"requestStatusMessage\":\"\",\"requestUpdated\":1418010829000,\"requestDevice\":{\"deviceId\":2,\"deviceUid\":\"4c87d6edeb1592c7\",\"deviceName\":\"HTC6435LVW\",\"deviceRegistrationId\":\"APA91bHl3ohv3b4KcxIsBgGWJ0jmhhXmEc_Uugb6wJBcSPj4ryXrDZCX6DAhQYFhfsxL8tLTWuEFYPiWOzLZKTr54h6xOz1XM-sxP-hDW9c5qIIYdwuNoTG55hcBn-Y0KeED40JqTktRpB9lvQ2b_emyvJy0_yvbSQ\",\"deviceOwner\":{\"userId\":4,\"username\":\"testuser\",\"name\":\"testuser\"}},\"requestOwner\":{\"userId\":4,\"username\":\"testuser\",\"name\":\"testuser\"}}";
		String jsonFile = "{\"fileId\": 4,\"requestId\": 1,\"fileName\": \"10-5-13_YO.png\",\"fileSize\": 4811700,\"fileOwner\":{\"userId\": 4,\"username\": \"testuser\",\"name\": \"testuser\"}}";
		String jsonUser = "{\"userId\":4,\"username\":\"testuser\",\"name\":\"testuser\"}";
		Request request = gson.fromJson(jsonNewRequest, Request.class);
		UserInfo info = request.getRequestOwner();
		Log.i("AR", "UserInfo: " + info);
		Log.i("AR", "UserInfo Name: " + info.getName());*/
		
	}
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	public String getDeviceUID() {		
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return androidId;
	}	
	
	/** Performs a get to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 */
	public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {
		return makeRequest(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, urlVariables);
	}
	
	/** Performs a post to the specified url, and returns the results as the specified type
	 * 
	 * @param url THe url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 */
	
	public <T> T postForObject(String url, Object requestBody, Class<T> responseType, Object... urlVariables) {
		return makeRequest(url, HttpMethod.POST, new HttpEntity<Object>(requestBody), responseType, urlVariables);
	}
	
	/** Makes a generic request to the server with the specified attributes
	 * 
	 *  Automatically adds and requests the authentication to each request. Will automatically retry the request if the
	 *  	Authentication token has expired	
	 *  
	 * @param url The URL to make the request on
	 * @param method The HTTP method of the request
	 * @param requestEntity The request Entity representing the request body and headers
	 * @param responseType The expected response type of the request
	 * @param urlVariables Any url parameters
	 * @return The results from the request, or null if there were any errors
	 */
	
	protected <T> T makeRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables) {	
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
			Class<T> responseType, Object... urlVariables) {
		
		T res = null;
		//check the status of the response
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			res = responseEntity.getBody();
			//if we need authentication token, extract it from the response
			if (securityContext.needAuthenticationToken()) {
				extractAuthenticationToken(responseEntity);
			}
		}
		else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			if (securityContext.needAuthenticationToken()) {
				//401 was returned, and we still need a token
				//TODO: means invalid credentials, raise this up somehow
				res = null; // for now we return null
			}
			else {
				//401 was returned, and we don't need a token,
				// our token has most likely expired, invalidate our current token, and re-request
				securityContext.invalidateAFTToken();
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
		HttpHeaders headers = new HttpHeaders();	
		headers.putAll(entity.getHeaders());		
		//check if we have an authentication token
		if (securityContext.needAuthenticationToken()) {
			//TODO: hard coded for now, update after credential manager is added
			headers.add(AFTAuthentication.AFT_AUTH_INIT_HEADER, securityContext.getCredentials());
		}
		else {
			headers.add(AFTAuthentication.AFT_AUTH_TOKEN_HEADER, securityContext.getAftToken());
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
			securityContext.setAftToken(token);
		}
	}
	
}
