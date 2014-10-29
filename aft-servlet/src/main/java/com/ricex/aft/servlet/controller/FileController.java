/**
 * 
 */
package com.ricex.aft.servlet.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.response.LongResponse;
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

public class FileController extends BaseController {

	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(FileController.class);
	
	/** The file manager that will be used to fetch files */
	private FileManager fileManager;
	
	/** Creates a new FileController and sets up the FileManager instance
	 * 
	 */
	
	public FileController() {

	}
	
	/** Retrieves the information (meta-data) for the file with the specified id
	 *  
	 * 
	 * @param fileId The id of the file
	 * @return A file object containing the meta data
	 */
	@RequestMapping(value = "/info/{fileId}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody File getFileInfo(@PathVariable long fileId) {
		return fileManager.getFileInfo(fileId);
	}
	
	/** Returns the raw contents of the file with the specified id
	 * 
	 * 
	 * @param fileId The id of the file
	 * @return A byte array containing the raw bytes of the file
	 */
	
	@RequestMapping(value = "/contents/{fileId}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody byte[] getFileContents(@PathVariable long fileId) {
		return fileManager.getFileContents(fileId);
	}
	
	/** Downloads the file with the given file id.
	 * 
	 * Similar to getFileContents, but this adds the attachment header for use in web browsers
	 * 
	 * @param fileId The id of the file to download
	 * @param fileName The name of the file, not used, for URL purposes
	 * @return The byte stream of the file
	 */
	
	@RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET, produces={MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public @ResponseBody byte[] downloadFile(@PathVariable long fileId, HttpServletResponse response) {
		File file = fileManager.getFileInfo(fileId); //get the file to retrieve the file name
		response.setHeader("Content-Disposition", "attachment;filename=" + removeSpacesFromFileName(file.getFileName()));
		return fileManager.getFileContents(fileId);
	}
	
	/** Creates the specified file and returns its ID.
	 * 
	 *  The contents of the file should be included in the body of the method as a base64 encoded string. The
	 *  	name of the file is passed in as the path variable "fileName".
	 *  
	 *  The file contents should be in JSON form, of a base64 encoded string
	 *  
	 * @param fileContents The byte array of the file contents to create
	 * @param fileName The name of the file to create
	 * @return The ID of the created file, or -1 if creation failed.
	 */
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody LongResponse createFile(@RequestBody byte[] fileContents, @RequestParam(value="fileName", required = true) String fileName) {
		return new LongResponse(fileManager.createFile(fileContents,fileName));
	}
	
	/** Creates the specified file and returns its ID.
	 * 
	 *  The contents of the file should be included in the body of the method as a raw byte array. The
	 *  	name of the file is passed in as the path variable "fileName".
	 *  
	 *  The file contents should be uploaded in raw form, as an octet-stream.
	 *  
	 * @param fileContents The byte array of the file contents to create
	 * @param fileName The name of the file to create
	 * @return The ID of the created file, or -1 if creation failed.
	 */
	
	@RequestMapping(value = "/rawUpload", method = RequestMethod.POST, consumes={MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public @ResponseBody LongResponse createFileRaw(@RequestBody byte[] fileContents, @RequestParam(value="fileName", required = true) String fileName) {
		return new LongResponse(fileManager.createFile(fileContents,fileName));
	}
	
	/** Creates the specified file and returns its ID.
	 * 
	 * The contents of the file should be uploaded as a multipart file and as part of a form upload
	 * 
	 * @param fileName The name of the file
	 * @param file The multipart file to upload
	 * @return The id of the file created, or -1 if an error occurred during creation.
	 */
	
	@RequestMapping(value = "/formUpload", method = RequestMethod.POST)
	public @ResponseBody LongResponse formUploadFile(@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				return new LongResponse(fileManager.createFile(bytes, fileName));				
			}
			catch (Exception e) {
				log.warn("Failed to save file {}", fileName, e);
			}
		}
		return new LongResponse(-1l);
	}
	
	/** Removes the spaces from the file name and replaces them with underscores ("_")
	 * 
	 * @param fileName The name of the file to update
	 * @return The file name with the spaces removed
	 */
	protected String removeSpacesFromFileName(String fileName) {
		return fileName.replace(' ', '_');
	}

	/**
	 * @return the fileManager
	 */
	
	public FileManager getFileManager() {
		return fileManager;
	}

	/**
	 * @param fileManager the fileManager to set
	 */
	
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	
	
}
