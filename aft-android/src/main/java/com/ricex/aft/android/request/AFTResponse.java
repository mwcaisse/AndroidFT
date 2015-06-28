package com.ricex.aft.android.request;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.aft.android.util.AndroidJsonByteArrayBase64Adapter;
import com.ricex.aft.common.entity.UserInfo;
import com.ricex.aft.common.util.JsonDateMillisecondsEpochDeserializer;
import com.ricex.aft.common.util.UserInfoAdapter;

public class AFTResponse<T> {
	
	/** The response from the sever in raw, string, format */
	private final String response;
	
	/** The HttpStatus code of the response */
	private final HttpStatus statusCode;
	
	/** The expected type of the response */
	private final Class<T> expectedResponseType;
	
	/** The Gson converter to use */
	private final Gson gson;
	
	/** Creates a new instance of AFTResponse
	 * 
	 * @param response The raw (string) response received from the server
	 * @param statusCode The status code of the response
	 * @param expectedResponseType The type of a valid (http 200) response
	 */
	public AFTResponse(String response, HttpStatus statusCode, Class<T> expectedResponseType) {
		this.response = response;
		this.statusCode = statusCode;
		this.expectedResponseType = expectedResponseType;
		
		gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(byte[].class, new AndroidJsonByteArrayBase64Adapter())
				.registerTypeAdapter(UserInfo.class, new UserInfoAdapter())
				.create();
	}
	
	/** Whether or not this response is valid
	 * 
	 * @return True if the server returned Http OK (200), otherwise false
	 */
	public boolean isValid() {
		return statusCode.equals(HttpStatus.OK);
	}
	
	/** The response from the server if valid
	 * 
	 * @return The response from the server
	 */
	public T getResponse() {
		return gson.fromJson(response, expectedResponseType);
	}
	
	/** Returns the error received by the server, if invalid response
	 * 
	 * @return The error the server returned
	 */
	public String getError() {
		return response;
	}
	
	/** Return the status code received from the server
	 * 
	 * @return the status code received from the server
	 */
	public HttpStatus getStatusCode() {
		return statusCode;
	}	
	
	

}
