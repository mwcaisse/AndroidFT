package com.ricex.aft.servlet.mapper;

import java.util.List;

import com.ricex.aft.servlet.entity.RegistrationKey;

/** Registration Key Mapper for retreiving registration keys
 * 
 * @author Mitchell Caisse
 *
 */

public interface RegistrationKeyMapper {

	
	/** Returns the registration key with the given id
	 * 
	 * @param id The id of the registration key to fetch
	 * @return The registration key
	 */
	public RegistrationKey getRegistrationKey(long id);
	
	
	/** Returns the registration key with the given key
	 * 
	 * @param key The key to look up
	 * @return The registration key with the specified key
	 */
	public RegistrationKey getRegistrationKeyByKey(String key);
	
	/** Returns a list of all registration keys 
	 * 
	 * @return The list of all registration keys
	 */
	public List<RegistrationKey> getAllRegistrationKeys();
	
	/** Creates the specified registration key
	 * 
	 * @param key The registration key to create
	 */
	public void createRegistrationKey(RegistrationKey key);
	
	/** Updates the specified registration key
	 * 
	 * @param key The registration key to create
	 */
	public void updateRegistrationKey(RegistrationKey key);
	
}
