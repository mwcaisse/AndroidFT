/**
 * 
 */
package com.ricex.aft.android.request.file;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.common.entity.File;

/** Request to get a complete file, contents + info, from the server
 * 
 * @author Mitchell Caisse
 *
 */
public class GetCompleteFileRequest extends AbstractRequest<File> {

	/** The id of the file to request */
	private long fileId;
	
	/** Creates a new instance of GetCompleteFileRequest
	 * 
	 * @param fileId The id of the file to request
	 */
	
	public GetCompleteFileRequest(long fileId) {
		this.fileId = fileId;
	}
	
	@Override
	protected AFTResponse<File> executeRequest() throws RequestException {
		return getForObject(serverAddress + "file/{fileId}", File.class, fileId);
	}

}
