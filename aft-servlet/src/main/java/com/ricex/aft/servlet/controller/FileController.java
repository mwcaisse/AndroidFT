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
 *  File Controller for uploading files and retreiving files
 *  
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/file")

public class FileController {

	/** The file manager that wil be used to fetch files */
	private FileManager fileManager;
	
	public FileController() {
		fileManager = FileManager.INSTANCE;
	}
	
	/** Retrieves the information about the file with the given id
	 * 
	 * @param fileId The id of the file to fetch
	 * @return The information about the file
	 */
	@RequestMapping(value = "/info/{fileId}", method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody File getFileInfo(@PathVariable long fileId) {
		return fileManager.getFile(fileId);
	}
	
	/** Returns the contents of the file with the specified id
	 * 
	 * @param fileId The id of the file contents to fetch
	 * @return The raw bytes of the file
	 */
	
	@RequestMapping(value = "/contents/{fileId}", method = RequestMethod.GET, produces={"application/octet-stream"})
	public @ResponseBody byte[] getFileContents(@PathVariable long fileId) {
		return fileManager.getFileContents(fileId);
	}
	
	/** Returns the whole file, info and contents, with the specified id
	 * 
	 * @param fileId The id of the file to fetch
	 * @return The file with the given id
	 */
	
	@RequestMapping(value = "/{fileId}", method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody File getFile(@PathVariable long fileId) {
		return fileManager.getFile(fileId);
	}
	
	/** Uploads the given file, and assigns it an id
	 * * 
	 * @param fileContents The contents of the file to upload
	 * @param fileName The name of the file
	 * @return The id of the uploaded file
	 */
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody long createFile(@RequestBody byte[] fileContents, @RequestParam(value="fileName", required = true) String fileName) {
		return fileManager.createFile(fileContents,fileName);
	}
}
