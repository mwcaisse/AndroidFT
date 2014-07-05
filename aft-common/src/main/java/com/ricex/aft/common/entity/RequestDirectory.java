/**
 * 
 */
package com.ricex.aft.common.entity;

/**
 *  The Directory that a request should put the file into
 *  
 * @author Mitchell Caisse
 *
 */
public enum RequestDirectory {

	/** The root of the storage device (ie. root of external storage) */
	ROOT("ROOT"),
	
	/** The documents folder */
	DOCUMENTS("DOCUMENTS"),
	
	/** The downloads folder */
	DOWNLOADS("DOWNLOADS"),
	
	/** The movies folder */
	MOVIES("MOVIES"),
	
	/** The music folder */
	MUSIC("MUSIC"),
	
	/** The pictures folder */
	PICTURES("PICTURES");
	
	
	/** Converts a string to its corresponding request directory.
	 * 
	 * @param str The string to convert to a RequestDirectory
	 * @return The Request Directory corresponding to the string, or null if it is invalid
	 */
	
	public static RequestDirectory fromString(String str) {
		for (RequestDirectory requestDirectory : values()) {
			if (str.equals(requestDirectory.name)) {
				return requestDirectory;
			}
		}
		return null; // invalid
	}
	
	/** The human readable name of this Request Directory */
	private String name;
	
	/** Creates a new Request Directory with the given string as a human readable name
	 * 
	 * @param name The human readable value of the RequestDirectory
	 */
	
	private RequestDirectory(String name) {
		this.name = name;
	}
	
	/** Returns the string representation of this RequestDirectory
	 * 
	 */
	
	public String toString() {
		return name;
	}
	
}
