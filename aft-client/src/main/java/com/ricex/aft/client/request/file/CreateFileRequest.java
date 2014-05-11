/**
 * 
 */
package com.ricex.aft.client.request.file;

import com.mashape.unirest.http.Unirest;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.AbstractRequest;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.response.LongResponse;

/**
 * @author Mitchell Caisse
 *
 */
public class CreateFileRequest extends AbstractRequest<File> {

	/** The file to create */
	private File file;
	
	
	/** Creates a request to create a new file with the specified name and contents
	 * 
	 * @param file The file to create
	 */
	
	public CreateFileRequest(File file, RequestListener<File> listener) {
		super(listener);
		this.file = file;
	}
	
	/** Converts the response returned from the server into a LOng representing the FileId, 
	 * 		adds it to the instance of the file passed in, and returns it
	 * 
	 */
	
	protected File convertResponseFromJson(String jsonString) {
		long fileId = gson.fromJson(jsonString, LongResponse.class).getValue();
		file.setFileId(fileId);
		return file;
	}

	/** Constructs the request to send to the server to upload the file
	 * 
	 */
	
	protected void constructServerRequest() {
		serverRequest = Unirest.post(baseServiceUrl + "file/upload?fileName=" + file.getFileName())
				.header("Content-Type", "application/json")
				.body(gson.toJson(file.getFileContents(), byte[].class));		
	}
	
}
