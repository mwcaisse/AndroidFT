/**
 * 
 */
package com.ricex.aft.client.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

/**
 * @author Mitchell Caisse
 *
 */
public class JsonDateMillisecondsEpochDeserializer implements JsonDeserializer<Date> {
	
	/** Deservializes the Date in the form of millisecconds since the epoch.
	 * 
	 */
	
	public Date deserialize(JsonElement json, Type typeOfT,	JsonDeserializationContext context) 
			throws JsonParseException {
		return new Date(json.getAsJsonPrimitive().getAsLong());
	}
	//
	public JsonElement serialize(Date source, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(source.getTime());
	}
}


