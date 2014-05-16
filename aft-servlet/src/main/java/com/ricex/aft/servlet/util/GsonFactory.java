/**
 * 
 */
package com.ricex.aft.servlet.util;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.aft.common.util.JsonByteArrayBase64Adapter;
import com.ricex.aft.common.util.JsonDateMillisecondsEpochDeserializer;

/** Factory Object for creating the Gson Parser to use
 * 
 * @author Mitchell Caisse
 *
 */
public class GsonFactory {

	
	/** Creates a new GsonFactory
	 * 
	 */
	
	public GsonFactory() {

	}
	
	/** Factory method to construct the Gson parser
	 * 
	 * @return The gson parser
	 */
	
	public Gson constructGson() {
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
			.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
			.registerTypeAdapter(byte[].class, new JsonByteArrayBase64Adapter())
			.create();
		return gson;
	}
	


}
