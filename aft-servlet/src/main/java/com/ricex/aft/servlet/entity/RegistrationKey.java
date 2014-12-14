package com.ricex.aft.servlet.entity;

import java.io.Serializable;

/** Registration key that is required to register an account
 * 
 * @author Mitchell Caisse
 *
 */

public class RegistrationKey implements Serializable {

	/** The id of the registration key */
	private long keyId;
	
	/** The registration key */
	private String registrationKey;
	
	/** The number of uses remaining for this key */
	private int usesRemaining;
	
	/** Whether or not this key is active */
	private boolean active;
	
	/** Creates a new Registration Key
	 * 
	 */
	public RegistrationKey() {
		
	}

	/**
	 * @return the keyId
	 */
	public long getKeyId() {
		return keyId;
	}

	/**
	 * @param keyId the keyId to set
	 */
	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}

	/**
	 * @return the registrationKey
	 */
	public String getRegistrationKey() {
		return registrationKey;
	}

	/**
	 * @param registrationKey the registrationKey to set
	 */
	public void setRegistrationKey(String registrationKey) {
		this.registrationKey = registrationKey;
	}

	/**
	 * @return the usesRemaining
	 */
	public int getUsesRemaining() {
		return usesRemaining;
	}

	/**
	 * @param usesRemaining the usesRemaining to set
	 */
	public void setUsesRemaining(int usesRemaining) {
		this.usesRemaining = usesRemaining;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
