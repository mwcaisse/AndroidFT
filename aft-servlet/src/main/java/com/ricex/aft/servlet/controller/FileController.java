/**
 * 
 */
package com.ricex.aft.servlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.servlet.manager.FileManager;

/**
 *  SpringMVC Controller for fetching and creating files in the database.
 *  
 *  Contains functions to fetch whole files, file information, and file contents.
 *  
 *  A whole file is a File object that contains both the file information or meta data,
 *  	and the file contents. Methods also exist for retrieving the file information
 *  	and file contents separately depending on application.
 *  
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/file")

public class FileController {

	/** The file manager that wil be used to fetch files */
	private FileManager fileManager;
	
	/** Creates a new FileController and sets up the FileManager instance
	 * 
	 */
	
	public FileController() {
		fileManager = FileManager.INSTANCE;
	}
	
	/** Retrieves the information (meta-data) for the file with the specified id
	 *  
	 *  The meta-data contains the fileName and fileId. The fileContents field will be left blank.
	 *  This allows an application to fetch information about the file, while not fetching the 
	 *  	potentially large file contents, saving bandwidth.
	 * 
	 * @param fileId The id of the file
	 * @return A file object containing the meta data
	 */
	@RequestMapping(value = "/info/{fileId}", method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody File getFileInfo(@PathVariable long fileId) {
		return fileManager.getFile(fileId);
	}
	
	/** Returns the raw contents of the specified file.
	 * 
	 * The file meta-data should be fetched before hand using getFileInfo.
	 * 
	 * @param fileId The id of the file
	 * @return A byte array containing the raw bytes of the file
	 */
	
	@RequestMapping(value = "/contents/{fileId}", method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody byte[] getFileContents(@PathVariable long fileId) {
		return fileManager.getFileContents(fileId);
	}
	
	/** Returns the specified file.
	 * 
	 * 	Contains both the file meta-data and file-contents. If the file-contents are not necessary it
	 * 		is recommended to use getFileInfo instead, the file-contents can be fetched later using
	 * 		getFileContents if neccesary.
	 * 
	 * @param fileId The id of the file
	 * @return The requested file
	 */
	
	@RequestMapping(value = "/{fileId}", method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody File getFile(@PathVariable long fileId) {
		return fileManager.getFile(fileId);
	}
	
	/** Creates the specified file and returns its ID.
	 * 
	 *  The contents of the file should be included in the body of the method as a raw byte array. The
	 *  	name of the file is passed in as the path variable "fileName".
	 *  
	 * @param fileContents The byte array of the file contents to create
	 * @param fileName The name of the file to create
	 * @return The ID of the created file, or -1 if creation failed.
	 */
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody long createFile(@RequestBody byte[] fileContents, @RequestParam(value="fileName", required = true) String fileName) {
		return fileManager.createFile(fileContents,fileName);
	}
}
