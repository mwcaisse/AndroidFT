package com.ricex.aft.android.requester;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.os.AsyncTask;

import com.ricex.aft.common.auth.AFTAuthentication;
import com.ricex.aft.common.auth.AuthUser;
import com.ricex.aft.common.response.BooleanResponse;

public class UserRequester extends AbstractRequester {

	/** Creates a new UserRequester with the specified application context
	 * 
	 * @param context The application context
	 */
	public UserRequester(Context context) {
		super(context);
	}
	
	/** Logs into the server with a username and password and receives a session token if credentials are valid
	 * 
	 * @param username The username to log in to the server with
	 * @param password The password to log in to the server with
	 * @return True if login was successful, false otherwise
	 * @throws InvalidCredentialsException If the provided credentials are invalid
	 */
	public boolean loginPassword(String username, String password) throws InvalidCredentialsException {
		AuthUser user = new AuthUser(username, password);
		
		AFTResponse<BooleanResponse> res = postForObject(serverAddress + "user/login", user, BooleanResponse.class);
		return res.getResponse().getValue();
	}

	/** Logins into the server and requests an Authentication Token if the credentials are valid
	 * 
	 * @param username The username of the user to login as
	 * @param password The user's password
	 * @return The authentication token received, or null if no token was received, currently this should only occur
	 * 			if there was a server error during the request.
	 * @throws InvalidCredentialsException thrown if the credentials the user provided are invalid
	 */
	
	public String fetchAuthenticationToken(String username, String password) throws InvalidCredentialsException {
		AuthUser user = new AuthUser(username, password);
		
		HttpEntity<?> entity = new HttpEntity<AuthUser>(user);
		String requestUrl = serverAddress + "user/login";
		ResponseEntity<BooleanResponse> results = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, BooleanResponse.class, new Object[0]);
		if (results.getStatusCode().equals(HttpStatus.OK)) {
			boolean validCredentials = results.getBody().getValue();
			if (!validCredentials) {
				throw new InvalidCredentialsException("Unable to login as user: " + username + ". Invalid credentials");
			}
			//return extractSessionToken(results);
			
		}		

		//server responded with an error, add appropriate error handling here at some point
		return null;
	}
	
	/** Makes an async login request to the server and provides a authentication token if the credentials are valid
	 * 
	 * @param username The username of the user to login as
	 * @param password The user's password
	 * @param callack The callback to call with the results
	 * @return The authentication token received, or null if no token was received, currently this should only occur
	 * 			if there was a server error during the request.
	 * @throws InvalidCredentialsException thrown if the credentials the user provided are invalid
	 */
	
	public void fetchAuthenticationTokenAsync(final String username, final String password, final RequesterCallback<String> callback) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					callback.onSuccess(fetchAuthenticationToken(username, password));
				}
				catch (InvalidCredentialsException e) {
					callback.onError(e);
				}
				return null;
			}			
	
		}.execute();
	}
	
	/** Extracts the session token from the http response
	 * 
	 * @param entity The http response to extract the token from
	 */
	
	private void extractSessionToken(ResponseEntity<?> entity) {
		String token = entity.getHeaders().getFirst(AFTAuthentication.AFT_SESSION_TOKEN_HEADER);
		//set it as the session token, if it was found, and is not empty
		if (token != null && !token.isEmpty()) {
			SessionContext.INSTANCE.setSessionToken(token);
		}

	}
}
