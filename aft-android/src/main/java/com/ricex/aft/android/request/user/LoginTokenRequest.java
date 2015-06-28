package com.ricex.aft.android.request.user;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.requester.exception.RequestException;
import com.ricex.aft.common.auth.AuthToken;
import com.ricex.aft.common.response.BooleanResponse;

/** A Server Request to login to the server to obtain a Session Token.
 * 
 * 	The login is performed with a previously aquired Authentication Token
 * 
 * @author Mitchell Caisse
 *
 */

public class LoginTokenRequest extends AbstractRequest<BooleanResponse> {
	
	/** The user's authentication token */
	private String token;
	
	/** Creates a new Instance of Password Request
	 * 
	 * @param token The authorization token to use to login
	 */
	public LoginTokenRequest(String token) {		
		this.token  = token;
	}

	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request 
	 */
	
	protected AFTResponse<BooleanResponse> executeRequest() throws RequestException {
		AuthToken authToken = new AuthToken(token, getDeviceUID());
		return postForObject(serverAddress + "user/login/token", authToken, BooleanResponse.class);
	}
	
}
