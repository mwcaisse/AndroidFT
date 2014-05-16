/**
 * 
 */
package com.ricex.aft.android.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Mitchell Caisse
 *
 */
public class JsonDateMillisecondsEpochDeserializer implements JsonDeserializer<Date>, JsonSerializer<Date> {
	
	/** Deserializes the Date in the form of milliseconds since the epoch.
	 * 
	 */
	
	public Date deserialize(JsonElement json, Type typeOfT,	JsonDeserializationContext context) 
			throws JsonParseException {
		return new Date(json.getAsJsonPrimitive().getAsLong());
	}
	
	@Override
	public JsonElement serialize(Date source, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(source.getTime());
	}
}


