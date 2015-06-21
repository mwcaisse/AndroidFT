/**
 * 
 */
package com.ricex.aft.android.requester;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;

import com.ricex.aft.android.AFTConfigurationProperties;
import com.ricex.aft.android.requester.exception.InvalidRequestException;
import com.ricex.aft.android.requester.exception.RequestException;
import com.ricex.aft.android.requester.exception.UnauthorizedRequestException;
import com.ricex.aft.common.auth.AFTAuthentication;

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
		this.serverAddress = AFTConfigurationProperties.getServerAddress() + "api/";		
		this.securityContext = SecurityContext.INSTANCE;	
		restTemplate = new RestTemplate();	
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
	protected <T> AFTResponse<T> getForObject(String url, Class<T> responseType, Object... urlVariables) {
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
	
	protected <T> AFTResponse<T> postForObject(String url, Object requestBody, Class<T> responseType, Object... urlVariables) {
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
	
	protected <T> AFTResponse<T> makeRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables) {	
		HttpEntity<?> entity = addAuthenticationHeaders(requestEntity);
		ResponseEntity<String> results = restTemplate.exchange(url, method, entity, String.class, urlVariables);		
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
		new AsyncTask<Void, Void, AFTResponse<T>>() {

			@Override
			protected AFTResponse<T> doInBackground(Void... params) {
				AFTResponse<T> response = null;
				try {
					response = makeRequest(url, method, requestEntity, responseType, urlVariables);
				}
				catch (Exception e) {
					callback.onError(e);
				}	
				return response;
			}			
			
			@Override
			protected void onPostExecute(AFTResponse<T> results) {
				//if results are null, then error callback has been called already
				if (results != null) {
					if (results.isValid()) {
						callback.onSuccess(results.getResponse());
					}
					else {
						callback.onFailure(results);
					}
				}
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
	private <T> AFTResponse<T> processRequestResponse(ResponseEntity<String> responseEntity, String url, HttpMethod method, HttpEntity<?> requestEntity, 
			Class<T> responseType, Object... urlVariables) {
		
		AFTResponse<T> response = new AFTResponse<T>(responseEntity.getBody(), responseEntity.getStatusCode(), responseType);
		
		//TODO: Revisit with updated security
		if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			//401 was returned, prompt the user to login
			securityContext.invalidateAFTToken(); //invalidate the current token, if any
			securityContext.getAftToken(); //request a new token
			response = makeRequest(url, method, requestEntity, responseType, urlVariables);
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
	protected <T> T processAFTResponse(AFTResponse<T> response) throws RequestException {
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

	/** Adds the appropriate Authentication headers to the given HttpEntity. The entity passed in is not modified. New entity
	 * 		is returned.
	 * 
	 * @param entity The HttpEntity to add the request headers
	 * @return The new HttpEntity with the updated Authentication Headers
	 */
	private HttpEntity<?> addAuthenticationHeaders(final HttpEntity<?> entity) {
		HttpHeaders headers = new HttpHeaders();	
		headers.putAll(entity.getHeaders());	
		headers.add(AFTAuthentication.AFT_AUTH_TOKEN_HEADER, securityContext.getAftToken());
		return new HttpEntity<Object>(entity.getBody(), headers);
	}
	
	
}
