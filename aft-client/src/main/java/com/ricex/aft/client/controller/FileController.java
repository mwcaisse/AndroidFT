/**
 * 
 */
package com.ricex.aft.client.controller;

import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.request.file.CreateFileRequest;
import com.ricex.aft.client.request.file.FetchFileInfoRequest;
import com.ricex.aft.client.request.file.FetchFileRequest;
import com.ricex.aft.common.entity.File;

/**
 *  The controller for handling fetching and pushing to/from the web server
 *  
 * @author Mitchell Caisse
 *
 */

public class FileController extends AbstractController  {

	
	/** The singleton instance */
	private static FileController _instance;
	
	/** Returns the singleton instance of this controller
	 * 
	 * @return The singleton instance
	 */
	
	public static FileController getInstance() {
		if (_instance == null) {
			_instance = new FileController();
		}
		return _instance;
	}
	
	
	/** Creates a new File Controller
	 * 
	 */
	
	private FileController() {
		
	}
	
	/** Fetches a file with the specified id from the web service
	 * 
	 * @param fileId The id of the file to fetch
	 * @param listener The request listener to notify when the request is complete
	 * @return The request used to fetch the file
	 */
	
	public IRequest<File> getFile(long fileId, RequestListener<File> listener) {
		FetchFileRequest request = new FetchFileRequest(fileId, listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Fetches the meta-data for the file with the specified id
	 * 
	 * @param fileId The file id
	 * @param listener The request listener to notify when the request is complete
	 * @return The request used to fetch the file info
	 */
	
	public IRequest<File> getFileInfo(long fileId, RequestListener<File> listener) {
		FetchFileInfoRequest request = new FetchFileInfoRequest(fileId, listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Sends a request to the server to create a new file
	 * 
	 * @param file The file to create
	 * @param listener The listener to notify when the request is complete
	 * @return The request used to create the file
	 */
	
	public IRequest<File> createFile(File file, RequestListener<File> listener) {
		CreateFileRequest request = new CreateFileRequest(file, listener);
		makeAsyncRequest(request);
		return request;
	}
}
