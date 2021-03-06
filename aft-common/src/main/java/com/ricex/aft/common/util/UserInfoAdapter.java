package com.ricex.aft.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ricex.aft.common.entity.UserInfo;
import com.ricex.aft.common.entity.UserInfoImpl;

/** Deserializer for UserInfo interface. Deserializes into UserInfoImpl 
 * 
 * @author Mitchell Caisse
 *
 */
public class UserInfoAdapter implements JsonDeserializer<UserInfo>, JsonSerializer<UserInfo> {

	/** Deserialize the UserInfoImpl
	 * 
	 */
	
	@Override
	public UserInfo deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) 
					throws JsonParseException {
		return context.deserialize(json, UserInfoImpl.class);
	}

	@Override
	public JsonElement serialize(UserInfo userInfo, Type type, JsonSerializationContext context) {
		return context.serialize(userInfo, UserInfoImpl.class);
	}
	
	

}