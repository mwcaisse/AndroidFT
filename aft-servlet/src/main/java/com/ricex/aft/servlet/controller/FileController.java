/**
 * 
 */
package com.ricex.aft.servlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  File Controller for uploading files and retreiving files
 *  
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/file")

public class FileController {

	
	/** Retreives the file with the given id
	 * 
	 * @param fileId The id of the file to fetch
	 */
	@RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
	public void getFile(@PathVariable long fileId) {
		
	}
	
	/** Uploads the given file, and assigns it an id
	 * 
	 * @return The id of the uploaded file
	 */
	
	@RequestMapping(value = "/upload/", method = RequestMethod.POST)
	public long createFile() {
		return 0;
	}
}
