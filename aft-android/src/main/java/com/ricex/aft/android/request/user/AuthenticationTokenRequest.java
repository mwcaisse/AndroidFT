package com.ricex.aft.android.request.user;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.requester.exception.RequestException;

/** Request to fetch an Authentication Token from the server, to use for future authentication/login requests
 * 
 * @author Mitchell Caisse
 *
 */

public class AuthenticationTokenRequest extends AbstractRequest<String> {

	/** Creates a new instance of AuthenticationTokenRequest
	 * 
	 */
	
	public AuthenticationTokenRequest() {
		
	}
	
	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected AFTResponse<String> executeRequest() throws RequestException {
		return postForObject(serverAddress + "user/token", getDeviceUID(), String.class);
	}

}
