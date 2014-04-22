package com.ricex.aft.client.request.file;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.File;

public class FetchFileInfoRequest extends AbstractRequest<File> {

	/** The id of the file to fetch */
	private final long fileId;
	
	/** Creates a new request to fetch a specified file
	 * @param fileId The id of the file to fetch
	 * @param listener The listener to inform of the results
	 */
	
	public FetchFileInfoRequest(long fileId, RequestListener<File> listener) {
		super(listener);
		this.fileId = fileId;
	}

	/** Converts the JSON string received from the server, into the correct object for this Request
	 * 
	 *  It will be called by processResponse to properly parse the response
	 * 
	 * @param jsonString The JSONString containing the object
	 * @return The resulting java object from the JSON string
	 */
	
	protected File convertResponseFromJson(String jsonString) {
		return gson.fromJson(jsonString, File.class);
	}

	/** Constructs the Unirest request that will be used to fetch the file from the web service
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.get(baseServiceUrl + "file/info/" + fileId);
	}

}
