/**
 * 
 */
package com.ricex.aft.common.entity;


/**
 *  The status of a request
 * 
 * @author Mitchell Caisse
 *
 */
public enum RequestStatus {

	/** Request was just created */
	NEW ("New"), 
	/** Request is currently in progress */
	IN_PROGRESS ("In Progress"),
	/** Request has failed */
	FAILED ("Failed"), 
	/** Request has been completed */
	COMPLETED ("Completed");
	
	/** Converts a string to its corresponding request status.
	 * 
	 * @param str The string to convert to a RequestStatus
	 * @return The Request Status corresponding to the string, or null if it is invalid
	 */
	
	public static RequestStatus fromString(String str) {
		if (str.equals(NEW.name)) {
			return NEW;
		}
		else if (str.equals(IN_PROGRESS.name)) {
			return IN_PROGRESS;
		}
		else if (str.equals(FAILED.name)) {
			return FAILED;
		}
		else if (str.equals(COMPLETED.name)) {
			return COMPLETED;
		}
		else {
			return null; // this is invalid
		}
	}
	
	/** The human readable name of this Request Status */
	private String name;
	
	/** Creates a new Request Status with the given string as a human readable name
	 * 
	 * @param name The human readable value of the RequestStatus
	 */
	
	private RequestStatus(String name) {
		this.name = name;
	}
	
	/** Returns the string representation of this RequestStatus
	 * 
	 */
	
	public String toString() {
		return name;
	}
	
}
