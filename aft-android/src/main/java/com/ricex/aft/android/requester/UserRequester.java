package com.ricex.aft.android.requester;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import android.content.Context;

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

	
	/*
	 * 	public boolean isRegistered() {
		String deviceUid = getDeviceUID();		
		BooleanResponse res = getForObject(serverAddress + "device/isRegistered/{deviceUid}", BooleanResponse.class, deviceUid);		
		return res.getValue();
	}
	
	 */
	
	public boolean areValidCredentials(String username, String password) {
		return false;
	}
	
	public String fetchAuthenticationHeader(String username, String password) throws InvalidCredentialsException {
		AuthUser user = new AuthUser(username, password);
		
		HttpEntity<?> entity = new HttpEntity<AuthUser>(user);
		String requestUrl = serverAddress + "user/login";
		ResponseEntity<BooleanResponse> results = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, BooleanResponse.class, new Object[0]);
		if (results.getStatusCode().equals(HttpStatus.OK)) {
			boolean validCredentials = results.getBody().getValue();
			if (!validCredentials) {
				throw new InvalidCredentialsException("Unable to login as user: " + username + ". Invalid credentials");
			}
			return extractAuthenticationToken(results);
			
		}		

		//server responded with an error, add appropriate error handling here at some point
		return null;
	}
	
	/** Extracts the authentication token from the http response
	 * 
	 * @param entity The http response to extract the token from
	 * @return The token received in the response, or null if no token was received
	 */
	
	private String extractAuthenticationToken(ResponseEntity<?> entity) {
		String token = entity.getHeaders().getFirst(AFTAuthentication.AFT_AUTH_TOKEN_HEADER);
		//set it as the authorization token, if it was found, and is not empty
		if (token != null && !token.isEmpty()) {
			return token;
		}
		return null;
	}
}
