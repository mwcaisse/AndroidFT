/**
 * 
 */
package com.ricex.aft.android.util;

import java.lang.reflect.Type;

import android.util.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.ricex.aft.common.util.JsonByteArrayBase64Adapter;



/**
 * @author Mitchell Caisse
 *
 */
public class AndroidJsonByteArrayBase64Adapter extends JsonByteArrayBase64Adapter {

	
	/** Deserializes the given base64 string into a byte array
	 * 
	 */
	
	@Override
	public byte[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {	
		return Base64.decode(json.getAsString(), 0);
	}

	/** Serializes the given byte array by converting it to a base 64 string
	 * 
	 */

	@Override
	public JsonElement serialize(byte[] source, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(Base64.encodeToString(source, 0));
	}

}
