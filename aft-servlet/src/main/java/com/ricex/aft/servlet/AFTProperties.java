package com.ricex.aft.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Class for retreiving properties from the aft-servlet properties
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTProperties {

	/** The key for the version property */
	private static final String KEY_VERSION = "version";
	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(AFTProperties.class);
	
	/** The properties to use */
	private static Properties properties;
	
	/** Load in the properties from file
	 * 
	 */
	static {
		properties = new Properties();
		try {
			InputStream propertiesIn = AFTProperties.class.getResourceAsStream("/aft-servlet.properties");
			properties.load(propertiesIn);
			propertiesIn.close();
		}
		catch (IOException e) {
			log.error("Error loading in properties file: aft-servlet.properties!", e);
		}	
	}
	
	/** Returns the version of the application
	 * 
	 * @return The application version
	 */
	public static String getVersion() {
		return getProperty(KEY_VERSION);
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
