
package com.ricex.aft.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

/** Properties wrapper around Android Shared preferences to retrieve and store
 * 		values
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTPreferences {
	
	/** Property for the registration id */
	public static final String PROPERTY_REG_ID = "registration_id";
	
	/** Property for application version */
	public static final String PROPERTY_APP_VERSION = "appVersion";
	
	/** Property for the user's username */
	public static final String PROPERTY_USERNAME = "username";
	
	/** Property for the user's auth token */
	public static final String PROPERTY_AUTH_TOKEN = "authToken";
	
	/** The name of the shared preferences that is used to store and retrieve the properties */
	private static final String SHARED_PREFERENCES_NAME = "AFT_ANDROID_PREFERENCES";
	
	/** The Shared preferences to use to store + retrieve values */
	private static SharedPreferences preferences;
	
	/** Creates a new AFTPreferences */
	private AFTPreferences() {
	}
	
	/** Returns the value associated with the given key
	 * 
	 * @param key The key to get the value for
	 * @return The value with the given key, or empty string "" if the key doesn't exist.
	 */
	public static String getValue(String key) {
		return getValue(key, "");
	}
	
	/** Returns the value associated with the given key
	 * 
	 * @param key The key to get the value for
	 * @param def The string to return if a value with the specified key was not found
	 * @return The value with the given key, or def if the key doesn't exist.
	 */
	public static String getValue(String key, String def) {
		return preferences.getString(key, def);
	}
	
	/** Returns the Integer value associated with the given key
	 * 
	 * @param key The key to get the value for
	 * @return The integer value with the given key, or 0 if the key doesn't exist
	 */
	public static int getIntValue(String key) {
		return getIntValue(key, 0);
	}
	
	/** Returns the Integer value associated with the given key
	 * 
	 * @param key The key to get the value for
	 * @param def The default value to return if the key doesn't exist
	 * @return The integer value with the given key, or def if the key doesn't exist
	 */
	public static int getIntValue(String key, int def) {
		return preferences.getInt(key, def);
	}
	
	/** Associates the given key with the given value.
	 * 
	 * @param key The key of the value
	 * @param value The value
	 * @return True if the value was successfully stored.
	 */
	public static boolean setValue(String key, String value) {
		return preferences.edit().putString(key, value).commit();
	}
	
	/** Associates the given key with the given value.
	 * 
	 * @param key The key of the value
	 * @param value The value
	 * @return True if the value was successfully stored.
	 */
	public static boolean setIntValue(String key, int value) {
		return preferences.edit().putInt(key, value).commit();
	}
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	public static String getDeviceUID() {		
		String androidId = Settings.Secure.getString(PushFileApplication.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
		return androidId;
	}		
	
	/** Updates the shared preferences object to use
	 * 
	 */
	public static void initializeSharedPreferences() {
		preferences = PushFileApplication.getAppContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
	}	
	
}
