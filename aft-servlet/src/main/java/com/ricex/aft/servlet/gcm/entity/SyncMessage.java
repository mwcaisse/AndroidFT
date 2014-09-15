/**
 * 
 */
package com.ricex.aft.servlet.gcm.entity;

import java.util.List;

/** Message sent to the GCM to trigger a sync on android devices
 * 
 * @author Mitchell Caisse
 *
 */
public class SyncMessage {

	
	/** List of registration ids to send the message to */
	private List<String> registration_ids;
	
	/** Maps a single user to multiple devices / registration ids */
	private String notification_key;
	
	/** String that collapses a group of messages together when the device is offline */
	private String collapse_key;
	

	/**
	 * @return the registration_ids
	 */
	
	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	/**
	 * @param registration_ids the registration_ids to set
	 */
	
	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	/**
	 * @return the notification_key
	 */
	
	public String getNotification_key() {
		return notification_key;
	}

	/**
	 * @param notification_key the notification_key to set
	 */
	
	public void setNotification_key(String notification_key) {
		this.notification_key = notification_key;
	}

	/**
	 * @return the collapse_key
	 */
	
	public String getCollapse_key() {
		return collapse_key;
	}

	/**
	 * @param collapse_key the collapse_key to set
	 */
	
	public void setCollapse_key(String collapse_key) {
		this.collapse_key = collapse_key;
	}
	
}
