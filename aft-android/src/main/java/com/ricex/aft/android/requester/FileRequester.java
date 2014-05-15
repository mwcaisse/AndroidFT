/**
 * 
 */
package com.ricex.aft.android.requester;

import android.content.Context;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;

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
	
	/** Returns a complete copy of the file for the specified request
	 * 
	 * @param request The request to fetch the file fore
	 * @return The file in the request
	 */
	
	public File getCompleteFile(Request request) {
		return getCompleteFile(request.getRequestFile().getFileId());
	}
}
