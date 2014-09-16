
package com.ricex.aft.android;

import android.content.Context;
import android.content.SharedPreferences;

/** Properties wrapper around Android Shared preferences to retreive and store
 * 		values
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTProperties {

	/** The key used to store the device upload key */
	public static final String KEY_DEVICE_UPLOAD_KEY = "deviceUploadKey";
	
	/** The instance of this singleton properties */
	private static AFTProperties _instance;
	
	/** Retrieves the singleton instance of this properties
	 * 
	 * @return The singleton instance
	 */
	public static AFTProperties getInstance() {
		if (_instance == null) {
			_instance = new AFTProperties();
		}
		return _instance;
	}
	
	/** The name of the shared preferences that is used to store and retreive the properties */
	private static final String SHARED_PREFERENCES_NAME = "aftProperties";
	
	/** The context to use to get the android preferences */
	private Context context;
	
	/** The Shared preferences to use to store + retrieve values */
	private SharedPreferences preferences;
	
	/** Creates a new AFTProperties */
	private AFTProperties() {
		
	}
	
	/** Returns the value associated with the given key
	 * 
	 * @param key The key to get the value for
	 * @return The value with the given key, or empty string "" if the key doesn't exist.
	 */
	public String getValue(String key) {
		return preferences.getString(key, "");
	}
	
	/** Associates the given key with the given value.
	 * 
	 * @param key The key of the value
	 * @param value The value
	 * @return True if the value was successfully stored.
	 */
	public boolean setValue(String key, String value) {
		return preferences.edit().putString(key, value).commit();
	}
	
	/** Sets the context to use to retrieve properties
	 * 
	 * @param context The context to set
	 */
	public void setContext(Context context) {
		this.context = context;
		updateSharedPreferences();
	}	
	
	/** Updates the shared preferences object to use
	 * 
	 */
	private void updateSharedPreferences() {
		this.preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
	}	
	
}
