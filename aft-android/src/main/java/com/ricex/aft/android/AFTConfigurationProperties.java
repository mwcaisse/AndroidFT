package com.ricex.aft.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.util.Log;

public class AFTConfigurationProperties {

	/** The key for the version property */
	private static final String KEY_SERVER_ADDRESS = "server.root";

	
	/** The properties to use */
	private static Properties properties;
	
	/** Load in the properties from file
	 * 
	 */
	static {
		properties = new Properties();
		try {
			InputStream propertiesIn = AFTConfigurationProperties.class.getResourceAsStream("/aft-android.properties");
			properties.load(propertiesIn);
			propertiesIn.close();
		}
		catch (IOException e) {
			Log.e("AFTConfigProps", "Error loading in properties file: aft-android.properties!", e);
		}	
	}	

	/** Returns the address of the web service
	 * 
	 * @return The server address
	 */
	public static String getServerAddress() {
		return getProperty(KEY_SERVER_ADDRESS);
	}
	
	/** Returns the value of the property with the given key
	 * 
	 * @param key The key of the property to fetch
	 * @return The value of the property
	 */
	private static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
