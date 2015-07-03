package com.ricex.aft.android.request.request;

import com.ricex.aft.android.request.AFTResponse;
import com.ricex.aft.android.request.AbstractRequest;
import com.ricex.aft.android.request.exception.RequestException;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.response.LongResponse;

public class UpdateRequestRequest extends AbstractRequest<LongResponse> {

	/** The Request to update */
	private Request request;
	
	/** Creates a new instance of UpdateRequestRequest
	 * 
	 * @param request The request to update
	 */
	
	public UpdateRequestRequest(Request request) {
		this.request = request;
	}

	@Override
	protected AFTResponse<LongResponse> executeRequest() throws RequestException {
		return putForObject(serverAddress + "request/update", request, LongResponse.class);
	}

}
