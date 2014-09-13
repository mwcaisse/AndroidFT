/**
 * 
 */
package com.ricex.aft.android.requester;

import android.content.Context;

import com.ricex.aft.common.entity.File;

/** Requestor for requesting files 
 * 
 * @author Mitchell Caisse
 *
 */

public class FileRequester extends AbstractRequester {

	
	/** Creates a new File Requester with the specified context
	 * 
	 * @param context The context for the requester
	 */
	
	public FileRequester(Context context) {
		super(context);
	}
	
	/** Returns a complete copy of the file with the specified id
	 * 
	 * @param fileId The ID of the file to fetch
	 * @return The file fetched
	 */
	
	public File getCompleteFile(long fileId) {
		File res = restTemplate.getForObject(serverAddress + "file/{fileId}", File.class, fileId);
		return res;
	}
	
	/** Returns the information for the specified file
	 * 
	 * @param fileId The id of the file to fetch the information for
	 * @return The file information
	 */
	public File getFileInfo(long fileId) {
		File res = restTemplate.getForObject(serverAddress + "file/info/{fileId}", File.class, fileId);
		return res;
	}
	
	/** Returns the contents of the file with the given id
	 * 
	 * @param fileId The id of the file to fetch the contents for
	 * @return The contents of the file
	 */
	public byte[] getFileContents(long fileId) {
		byte[] contents = restTemplate.getForObject(serverAddress + "file/contents/{fileId}", byte[].class, fileId);
		return contents;
	}
	
}
