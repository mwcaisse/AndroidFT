/**
 * 
 */
package com.ricex.aft.common.util;

import java.lang.reflect.Type;

import org.apache.commons.codec.binary.Base64;

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
public class JsonByteArrayBase64Adapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

	
	/** Deserializes the given base64 string into a byte array
	 * 
	 */
	
	@Override
	public byte[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {	
		return Base64.decodeBase64(json.getAsString());
	}

	/** Serializes the given byte array by converting it to a base 64 string
	 * 
	 */

	@Override
	public JsonElement serialize(byte[] source, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(Base64.encodeBase64String(source));
	}

}
