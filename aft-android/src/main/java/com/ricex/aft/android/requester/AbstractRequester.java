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
import android.os.AsyncTask;
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
	protected RestTemplate restTemplate;
	
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
	protected <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {
		return makeRequest(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, urlVariables);
	}
	
	/** Performs a get to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param responseType The expected response
	 * @param callback The RequesterCallback to return the results to on completion
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 */
	protected <T> void getForObjectAsync(String url, Class<T> responseType, RequesterCallback<T> callback, Object... urlVariables) {
		makeRequestAsync(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, callback, urlVariables);
	}
	
	/** Performs a post to the specified url, and returns the results as the specified type
	 * 
	 * @param url THe url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 */
	
	protected <T> T postForObject(String url, Object requestBody, Class<T> responseType, Object... urlVariables) {
		return makeRequest(url, HttpMethod.POST, new HttpEntity<Object>(requestBody), responseType, urlVariables);
	}
	
	/** Performs an async post to the specified url, and returns the results as the specified type
	 * 
	 * @param url THe url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param callback The requester callback to return the results to on completion
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 */
	
	protected <T> void postForObjectAsync(String url, Object requestBody, Class<T> responseType, RequesterCallback<T> callback, Object... urlVariables) {
		makeRequestAsync(url, HttpMethod.POST, new HttpEntity<Object>(requestBody), responseType, callback, urlVariables);
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
	
	/** Makes a generic async request to the server with the specified attributes
	 * 
	 *  Automatically adds and requests the authentication to each request. Will automatically retry the request if the
	 *  	Authentication token has expired	
	 *  
	 * @param url The URL to make the request on
	 * @param method The HTTP method of the request
	 * @param requestEntity The request Entity representing the request body and headers
	 * @param responseType The expected response type of the request
	 * @param callback The callback to call with the results of the request
	 * @param urlVariables Any url parameters
	 * @return The results from the request, or null if there were any errors
	 */
	protected <T> void makeRequestAsync(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, 
										final Class<T> responseType, final RequesterCallback<T> callback, 
										final Object... urlVariables) {
		new AsyncTask<Void, Void, T>() {

			@Override
			protected T doInBackground(Void... params) {
				HttpEntity<?> entity = addAuthenticationHeaders(requestEntity);
				ResponseEntity<T> results = restTemplate.exchange(url, method, entity, responseType, urlVariables);		
				return processRequestResponse(results, url, method, requestEntity, responseType, urlVariables);
			}
			
			@Override
			protected void onPostExecute(T results) {
				callback.onSuccess(results);
			}
			
		}.execute();
	}
	
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
	private <T> T processRequestResponse(ResponseEntity<T> responseEntity, String url, HttpMethod method, HttpEntity<?> requestEntity, 
			Class<T> responseType, Object... urlVariables) {
		
		T res = null;
		//check the status of the response
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			res = responseEntity.getBody();
			//if we need authentication token, extract it from the response
			if (securityContext.needAuthenticationToken()) {
				//extractAuthenticationToken(responseEntity);
			}
		}
		else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			if (securityContext.needAuthenticationToken()) {
				//401 was returned, and we still need a token
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
	private HttpEntity<?> addAuthenticationHeaders(final HttpEntity<?> entity) {
		HttpHeaders headers = new HttpHeaders();	
		headers.putAll(entity.getHeaders());		
		//check if we have an authentication token
		if (securityContext.needAuthenticationToken()) {
			//headers.add(AFTAuthentication.AFT_AUTH_INIT_HEADER, securityContext.getCredentials());
		}
		else {
			headers.add(AFTAuthentication.AFT_AUTH_TOKEN_HEADER, securityContext.getAftToken());
		}

		return new HttpEntity<Object>(entity.getBody(), headers);
	}
	
	
}
