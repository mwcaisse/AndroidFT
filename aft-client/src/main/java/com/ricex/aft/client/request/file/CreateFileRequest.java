/**
 * 
 */
package com.ricex.aft.client.request.file;

import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;

/**
 * @author Mitchell Caisse
 *
 */
public class CreateFileRequest extends AbstractRequest<Long> {

	/** The bytes of the file to create */
	private final byte[] fileContents;
	
	/** The name of the file to create */
	private final String fileName;
	
	
	/** Creates a request to create a new file with the specified name and contents
	 * 
	 * @param fileName The name of the file
	 * @param fileContents The raw contents of the file
	 */
	
	public CreateFileRequest(String fileName, byte[] fileContents, RequestListener<Long> listener) {
		super(listener);
		this.fileContents = fileContents;
		this.fileName = fileName;
	}
	
	/** Converts the response from the server into the long the server returns
	 * 
	 */
	
	protected Long convertResponseFromJson(String jsonString) {
		return gson.fromJson(jsonString, Long.class);
	}

	/** Constructs the request to send to the server to upload the file
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.post(baseServiceUrl + "file/upload?fileName=" + fileName)
				.header("Content-Type", "application/json")
				.body(gson.toJson(fileContents, byte[].class));		
	}
	
}
