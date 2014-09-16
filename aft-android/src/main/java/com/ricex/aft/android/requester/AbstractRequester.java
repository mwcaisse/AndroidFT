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
import com.ricex.aft.android.AFTProperties;
import com.ricex.aft.android.util.AndroidJsonByteArrayBase64Adapter;
import com.ricex.aft.common.util.JsonDateMillisecondsEpochDeserializer;

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
		//this.serverAddress = "http://192.168.1.160:8080/aft-servlet/manager/";
		restTemplate = new RestTemplate();
		
		//Create the gson object to decode Json messages
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(byte[].class, new AndroidJsonByteArrayBase64Adapter())
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
	
	public String getDeviceUID() {		
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return androidId;
	}	
	
	/** Fetches or generates an upload key for this device if one does not exist
	 * 
	 * @return The upload key
	 */
	protected String getOrGenerateUploadKey() {
		String uploadKey = AFTProperties.getInstance().getValue(AFTProperties.KEY_DEVICE_UPLOAD_KEY);
		//check if the upload key exists, and if not create one
		if (uploadKey.isEmpty()) { 
			uploadKey = "uploadKey" + (int)(Math.random() * 10000);
			AFTProperties.getInstance().setValue(AFTProperties.KEY_DEVICE_UPLOAD_KEY, uploadKey);
		}
		return uploadKey;
	}
	
}
