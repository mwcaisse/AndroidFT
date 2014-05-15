/**
 * 
 */
package com.ricex.aft.android.requester;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.provider.Settings;

/**
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractRequester {

	/** The context to use to fetch Device UID */
	protected final Context context;
	
	/** The rest template */
	protected RestTemplate restTemplate;
	
	/** The server address */
	protected final String serverAddress;
	
	/** Creates a new Abstract requestor with the specified context
	 * 
	 * @param context The context
	 */
	
	public AbstractRequester(Context context) {
		this.context = context;
		this.serverAddress = "http://fourfivefire.com:8080/aft-servlet/manager/";
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	}
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	public long getDeviceUID() {
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return Long.parseLong(androidId, 16);
	}	
	
}
