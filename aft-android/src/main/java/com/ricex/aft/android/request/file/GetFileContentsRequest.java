package com.ricex.aft.android.request.file;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.request.exception.RequestException;

/** Request to get the contents of a file
 * 
 * @author Mitchell Caisse
 *
 */

public class GetFileContentsRequest extends AbstractRequest<byte[]> {

	/**The id of the file */
	private long fileId;
	
	/** Creates a new instance of GetFileContentsRequest
	 * 
	 * @param fileId The id of the file 
	 */
	
	public GetFileContentsRequest(long fileId) {
		this.fileId = fileId;
	}

	@Override
	protected AFTResponse<byte[]> executeRequest() throws RequestException {
		return getForObject(serverAddress + "file/contents/{fileId}", byte[].class, fileId);
	}

}
