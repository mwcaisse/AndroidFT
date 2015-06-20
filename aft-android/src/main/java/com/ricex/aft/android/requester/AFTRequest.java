package com.ricex.aft.android.requester;

//import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/** Represents a Request made to the AFT servlet
 * 
 * @author Mitchell Caisse
 *
 */

public class AFTRequest {

	/** The id of this request */
	private long requestId;
	
	/** The url to make the request to */
	private String requestUrl;
	
	/** The HttpMethod of the request */
	private HttpMethod requestMethod;
	
	/** The request entity containing the body + headers */
	private HttpEntity<?> requestEntity;
	
	/** The expected response type */
	//private ParameterizedTypeReference<?> responseType;
	
	/** The response received from the request */
	private ResponseEntity<?> responseEntity;
	
	/** Any url parameters of the request */
	private Object[] urlVariables;
	
	/** Creates a new AFTRequest with the given id 
	 * 
	 * @param requestId The id of this request
	 */
	public AFTRequest(long requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * @return the requestMethod
	 */
	public HttpMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod the requestMethod to set
	 */
	public void setRequestMethod(HttpMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * @return the requestEntity
	 */
	public HttpEntity<?> getRequestEntity() {
		return requestEntity;
	}

	/**
	 * @param requestEntity the requestEntity to set
	 */
	public void setRequestEntity(HttpEntity<?> requestEntity) {
		this.requestEntity = requestEntity;
	}

	/**
	 * @return the responseType
	 */
	/*public ParameterizedTypeReference<?> getResponseType() {
		return responseType;
	}*/

	/**
	 * @param responseType the responseType to set
	 */
	/*public void setResponseType(ParameterizedTypeReference<?> responseType) {
		this.responseType = responseType;
	}*/

	/**
	 * @return the responseEntity
	 */
	public ResponseEntity<?> getResponseEntity() {
		return responseEntity;
	}

	/**
	 * @param responseEntity the responseEntity to set
	 */
	public void setResponseEntity(ResponseEntity<?> responseEntity) {
		this.responseEntity = responseEntity;
	}

	/**
	 * @return the urlVariables
	 */
	public Object[] getUrlVariables() {
		return urlVariables;
	}

	/**
	 * @param urlVariables the urlVariables to set
	 */
	public void setUrlVariables(Object[] urlVariables) {
		this.urlVariables = urlVariables;
	}

	/**
	 * @return the requestId
	 */
	public long getRequestId() {
		return requestId;
	}
	
}
