package com.ricex.aft.android.request;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.aft.android.AFTConfigurationProperties;
import com.ricex.aft.android.AFTPreferences;
import com.ricex.aft.android.request.exception.InvalidRequestException;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.android.request.exception.UnauthenticationRequestException;
import com.ricex.aft.android.request.exception.UnauthorizedRequestException;
import com.ricex.aft.android.request.user.LoginTokenRequest;
import com.ricex.aft.android.util.AndroidJsonByteArrayBase64Adapter;
import com.ricex.aft.common.auth.AFTAuthentication;
import com.ricex.aft.common.entity.UserInfo;
import com.ricex.aft.common.util.JsonDateMillisecondsEpochDeserializer;
import com.ricex.aft.common.util.UserInfoAdapter;

/** Abstract Request implementing the Request interface. 
 * 
 * @author Mitchell Caisse
 *
 * @param <T> The type of the expected result of the request
 */

public abstract class AbstractRequest<T> implements Request<T> {	
	
	/** The rest template */
	protected RestTemplate restTemplate;
	
	/** The server address */
	protected final String serverAddress;
	
	/** The security context to use */
	private SessionContext sessionContext;
	
	/** Creates a new instance of AbstractRequest. Initializes the required fields
	 * 
	 */
	
	public AbstractRequest() {
		this.serverAddress = AFTConfigurationProperties.getServerAddress() + "api/";		
		this.sessionContext = SessionContext.INSTANCE;	
		restTemplate = new RestTemplate();		
		
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
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
	}
	
	/** Executes the request synchronously and returns the results of the request
	 * 
	 * @return The results of the request
	 * @throws RequestException When an issue occurred while executing the request
	 */
	
	public T execute() throws RequestException {
		AFTResponse<T> results = executeRequest();
		return processAFTResponse(results);
	}
	
	/** Executes the request synchronously and calls the callback with the results or error
	 * 
	 * @param callback The callback to call when the request is finished executing
	 */
	
	@Override
	public void executeAsync(final RequestCallback<T> callback) {
		new AsyncTask<Void, Void, AFTResponse<T>>() {

			@Override
			protected AFTResponse<T> doInBackground(Void... params) {
				AFTResponse<T> response = null;
				try {
					response = executeRequest();
				}
				catch (Exception e) {
					callback.onError(e);
				}	
				return response;
			}			
			
			@Override
			protected void onPostExecute(AFTResponse<T> results) {
				//if results are null, then error callback has been called already
				try {
					T resEntity = processAFTResponse(results);
					callback.onSuccess(resEntity);
				}
				catch (RequestException e) {
					callback.onFailure(e, results);
				}		
			}
			
		}.execute();
	}
	
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	protected static String getDeviceUID() {		
		return AFTPreferences.getDeviceUID();
	}
	
	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request
	 */
	protected abstract AFTResponse<T> executeRequest() throws RequestException;
	
	/** Performs a get to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 * @throws RequestException If an error occurred while making the request
	 */
	protected AFTResponse<T> getForObject(String url, Class<T> responseType, Object... urlVariables) throws RequestException {
		return makeRequest(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, urlVariables);
	}
	
	/** Performs a post to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected AFTResponse<T> postForObject(String url, Object requestBody, Class<T> responseType, Object... urlVariables) throws RequestException {
		return makeRequest(url, HttpMethod.POST, new HttpEntity<Object>(requestBody), responseType, urlVariables);
	}
	
	/** Performs a put to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected AFTResponse<T> putForObject(String url, Object requestBody, Class<T> responseType, Object... urlVariables) throws RequestException {
		return makeRequest(url, HttpMethod.PUT, new HttpEntity<Object>(requestBody), responseType, urlVariables);
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
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected AFTResponse<T> makeRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables) throws RequestException {	
		HttpEntity<?> entity = addAuthenticationHeaders(requestEntity);
		try {
			ResponseEntity<T> results = restTemplate.exchange(url, method, entity, responseType, urlVariables);
			return processSucessfulRequestResponse(results, url, method, requestEntity, responseType, urlVariables);
		}
		catch (HttpStatusCodeException e) {
			String responseBody = e.getResponseBodyAsString();
			HttpStatus status = e.getStatusCode();
			return processErrorRequestResponse(responseBody, status, url, method, requestEntity, responseType, urlVariables);
		}
	}
	
	/** Adds the appropriate Authentication headers to the given HttpEntity. The entity passed in is not modified. New entity
	 * 		is returned.
	 * 
	 * @param entity The HttpEntity to add the request headers
	 * @return The new HttpEntity with the updated Authentication Headers
	 */
	private HttpEntity<?> addAuthenticationHeaders(final HttpEntity<?> entity) {
		//only add the session token header, if we have a session token.
		if (sessionContext.hasSessionToken()) {		
			HttpHeaders headers = new HttpHeaders();	
			headers.putAll(entity.getHeaders());	
			headers.add(AFTAuthentication.AFT_SESSION_TOKEN_HEADER, sessionContext.getSessionToken());
			return new HttpEntity<Object>(entity.getBody(), headers);
		}
		return entity;
	}
	
	/** Processes the results of an AFT Request that returned a successful http status code (200 OK)
	 * 
	 * @param responseEntity The results of the request 
	 * @param url The url of the request
	 * @param method The method of the request
	 * @param requestEntity The response entity of the request
	 * @param responseType the response type of the request
	 * @param urlVariables The url variables of the request
	 * @return The processed results of the request, the request body or null if there was an error
	 */
	private AFTResponse<T> processSucessfulRequestResponse(ResponseEntity<T> responseEntity, String url, HttpMethod method, HttpEntity<?> requestEntity, 
			Class<T> responseType, Object... urlVariables) throws RequestException {
		
		AFTResponse<T> response = new AFTResponse<T>(responseEntity.getBody(), responseEntity.getStatusCode());		
	
		//if the code wasn't UNAUTHORIZED, and we need a session token, extract it from the response header
		if (sessionContext.needSessionToken()) {
			String sessionToken = extractSessionToken(responseEntity);
			if (StringUtils.isEmpty(sessionToken)) {
				throw new RequestException("Unable to retreive the session token from our request!");
			}
			sessionContext.setSessionToken(sessionToken);

		}
		return response;
	}
	
	/** Processes the results of an AFT Request that returned a non-successful status code (HTTP 400 or 500 )
	 * 
	 * @param responseBody The body of the response returned
	 * @param url The url of the request
	 * @param method The method of the request
	 * @param requestEntity The response entity of the request
	 * @param responseType the response type of the request
	 * @param urlVariables The url variables of the request
	 * @return The processed results of the request, the request body or null if there was an error
	 */
	private AFTResponse<T> processErrorRequestResponse(String responseBody, HttpStatus status, String url, HttpMethod method, 
			HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables) throws RequestException {
		
		AFTResponse<T> response = new AFTResponse<T>(null, responseBody, status);
		
		//TODO: Revisit with updated security
		if (status == HttpStatus.UNAUTHORIZED) {
			//401 was returned, prompt the user to login
			sessionContext.invalidateSessionToken(); //invalidate the current token, if any
			String authToken = AFTPreferences.getValue(AFTPreferences.PROPERTY_AUTH_TOKEN);
			if (StringUtils.isEmpty(authToken)) {
				//we don't have an authtoken. raise this up
				throw new UnauthenticationRequestException("Unable to make request, not authenticated with the server and no credentials");
			}
			//TODO: Add the functionality to get the stored auth token
			boolean res = new LoginTokenRequest(authToken).execute().getValue();
			
			if (res) {
				return executeRequest();
			}
			else {
				throw new UnauthenticationRequestException("Unable to make request, not authenticated, and could not obtain session token!");
			}
			
		}	
		return response;
	}
	
	/** Processes the AFT Response received after making a request.
	 * 
	 *  If the response is valid (server returned Http OK) then the object the server responded with is returned. Otherwise an exception
	 *  	is thrown indicating the error that occurred.
	 * 
	 * @param response The response received from the server
	 * @return The parsed response object
	 * @throws RequestException If response is invalid, the error returned by the server
	 */
	private T processAFTResponse(AFTResponse<T> response) throws RequestException {
		if (response.isValid()) {
			return response.getResponse();
		}
		else {
			String error = response.getError();
			switch (response.getStatusCode()) {
			case BAD_REQUEST:
				throw new InvalidRequestException(error);			
			case FORBIDDEN:
				throw new UnauthorizedRequestException(error);		
			default:
				throw new RequestException(error);
			}
		}
	}
	
	/** Extracts the session token from the http response
	 * 
	 * @param entity The http response to extract the token from
	 * @return The session token
	 */
	
	private String extractSessionToken(ResponseEntity<?> entity) {
		String token = entity.getHeaders().getFirst(AFTAuthentication.AFT_SESSION_TOKEN_HEADER);
		//set it as the authorization token, if it was found, and is not empty
		if (token != null && !token.isEmpty()) {
			return token;
		}
		return null;
	}
	
	
}
