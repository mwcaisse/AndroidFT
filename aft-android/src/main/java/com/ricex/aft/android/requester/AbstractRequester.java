/**
 * 
 */
package com.ricex.aft.android.requester;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.aft.android.util.JsonByteArrayBase64Adapter;
import com.ricex.aft.android.util.JsonDateMillisecondsEpochDeserializer;

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
		
		//Create the gson object to decode Json messages
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(byte[].class, new JsonByteArrayBase64Adapter())
				.create();
		
		//create the Gson message converter for spring, and set its Gson
		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		
		//add the gson message converter to the rest template
		restTemplate.getMessageConverters().add(converter);
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
