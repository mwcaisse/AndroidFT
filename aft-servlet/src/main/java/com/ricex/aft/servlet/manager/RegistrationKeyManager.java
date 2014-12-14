package com.ricex.aft.servlet.manager;

import java.util.List;

import com.ricex.aft.servlet.entity.RegistrationKey;
import com.ricex.aft.servlet.mapper.RegistrationKeyMapper;

/** The reigstration key manager for manipulating registration keys
 * 
 * @author Mitchell Caisse
 *
 */

public enum RegistrationKeyManager {

	INSTANCE;
	
	/** The registration key mapper to fetch and persist data */
	private RegistrationKeyMapper registrationKeyMapper;
	
	/** Creates a Registration Key Manager
	 * 
	 */
	private RegistrationKeyManager() {
		
	}
	
	
	/** Returns the registration key with the specified id
	 * 
	 * @param id The id of the registration key to fetch
	 * @return The registration key with the specified id
	 */
	public RegistrationKey getRegistrationKey(long id) {
		return registrationKeyMapper.getRegistrationKey(id);
	}
	
	/** Fetches the registration key information for the given key
	 * 
	 * @param key The key to fetch the registration key for
	 * @return The registration key
	 */
	public RegistrationKey getRegistrationKey(String key) {
		return registrationKeyMapper.getRegistrationKeyByKey(key);
	}
	
	/** Fetches a list of all registration keys
	 * 
	 * @return The list of all registration keys
	 */
	public List<RegistrationKey> getAllRegistrationkeys() {
		return registrationKeyMapper.getAllRegistrationKeys();
	}
	
	/** Updates the specified registration key
	 * 
	 * @param registrationKey The registration key to update
	 */
	public void updateRegistrationKey(RegistrationKey registrationKey) {
		registrationKeyMapper.updateRegistrationKey(registrationKey);
	}
	
	/** checks if the given registration key is valid
	 * 
	 * @param key The registration key to check
	 * @return True if the registration key is valid, false otherwise
	 */
	public boolean isRegistrationKeyValid(String key) {
		RegistrationKey registrationKey = getRegistrationKey(key);
		return registrationKey != null && isRegistrationKeyValid(registrationKey);
	}
	
	/** Consumes the registration key with the specified key
	 * 
	 * @param key The registration key
	 * @return True if the key is valid and the key was able to be used. False if the key is invalid
	 */
	public boolean consumeRegistrationKey(String key) {
		RegistrationKey registrationKey = getRegistrationKey(key);
		if (registrationKey != null && isRegistrationKeyValid(registrationKey)) {
			registrationKey.setUsesRemaining(registrationKey.getUsesRemaining() - 1);
			//check if the key has any uses remaining
			if (registrationKey.getUsesRemaining() <= 0) {
				registrationKey.setActive(false);
			}
			updateRegistrationKey(registrationKey);
			return true;
		}
		else {
			return false;
		}
	}
	
	/** Determines if the given registration key is valid
	 * 
	 * @param key The registration key to check for validity
	 * @return True if the registration key is valid, false otherwise
	 */
	protected boolean isRegistrationKeyValid(RegistrationKey key) {
		return key.isActive() && key.getUsesRemaining() > 0;
	}


	/**
	 * @return the registrationKeyMapper
	 */
	public RegistrationKeyMapper getRegistrationKeyMapper() {
		return registrationKeyMapper;
	}


	/**
	 * @param registrationKeyMapper the registrationKeyMapper to set
	 */
	public void setRegistrationKeyMapper(RegistrationKeyMapper registrationKeyMapper) {
		this.registrationKeyMapper = registrationKeyMapper;
	}
	
	
	
}
