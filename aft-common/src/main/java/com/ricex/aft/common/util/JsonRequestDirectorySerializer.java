/**
 * 
 */
package com.ricex.aft.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ricex.aft.common.entity.RequestDirectory;
import com.ricex.aft.common.entity.RequestStatus;

/** Gson Json Serializer for RequestStatus
 * 
 * 		Serializes it based upon its Name / String value
 * 
 * @author Mitchell Caisse
 *
 */
public class JsonRequestDirectorySerializer implements JsonSerializer<RequestDirectory>, JsonDeserializer<RequestDirectory> {

	/** Deserializes the Enum from its User Friendly string into an enum, or if that fails tries to use the value of
	 * 
	 */
	@Override
	public RequestDirectory deserialize(JsonElement json, Type typeOfT,	JsonDeserializationContext context) 
			throws JsonParseException {
		RequestDirectory directory =  RequestDirectory.fromString(json.getAsString());
		//check if parsing from the name was sucessful, if not, try parsing from value of
		if (directory == null) {
			directory = RequestDirectory.valueOf(json.getAsString());
		}
		return directory;
	}
	
	/** Serializes the RequestDirectory into its string form, as defined in RequestDirectory.toString()
	 * 
	 */
	@Override
	public JsonElement serialize(RequestDirectory source, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(source.toString());
	}
	
}