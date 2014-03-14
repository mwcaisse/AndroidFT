/**
 * 
 */
package com.ricex.aft.client.request;

import com.ricex.aft.client.cache.DeviceCache;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class FetchDeviceRequest extends AbstractRequest {

	/**
	 * @param listener
	 */
	protected FetchDeviceRequest(RequestListener listener) {
		super(listener);
		// TODO Auto-generated constructor stub
	}


	public void onSucess() {
		DeviceCache.getInstance().add(new Device());
	}

	public void onFailure() {
		
	}

	public void onCancelled() {

	}

}
