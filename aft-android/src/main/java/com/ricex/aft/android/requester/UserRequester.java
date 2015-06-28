package com.ricex.aft.android.requester;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import android.content.Context;

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

}
