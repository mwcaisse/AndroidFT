package com.ricex.aft.android.request.file;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.requester.exception.RequestException;
import com.ricex.aft.common.entity.File;

/** Request to get the information on a File
 * 
 * @author Mitchell Caisse
 *
 */

public class GetFileInfoRequest extends AbstractRequest<File> {

	/** The id of the file to request the info for */
	private long fileId;
	
	/** Creates a new instance of GetFileInfoRequest
	 * 
	 * @param fileId The id of the file
	 */
	
	public GetFileInfoRequest(long fileId) {
		this.fileId = fileId;
	}

	protected AFTResponse<File> executeRequest() throws RequestException {
		return getForObject(serverAddress + "file/info/{fileId}", File.class, fileId);
	}

}
