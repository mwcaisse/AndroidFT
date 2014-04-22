/**
 * 
 */
package com.ricex.aft.client.controller;

import com.ricex.aft.client.request.IRequest;
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
		return null;
	}
	
	/** Fetches the meta-data for the file with the specified id
	 * 
	 * @param fileId The file id
	 * @param listener The request listener to notify when the request is complete
	 * @return The request used to fetch the file info
	 */
	
	public IRequest<File> getFileInfo(long fileId, RequestListener<File> listener) {
		return null;
	}
	
	/** Fetches the contents of the file with the specified id
	 * 
	 * @param fileId The id of the file contents to fetch
	 * @param listener The request listener to notify when the request is complete
	 * @return The request used to fetch the file contents
	 */
	
	public IRequest<File> getFileContents(long fileId, RequestListener<File> listener) {
		return null;
	}
}
