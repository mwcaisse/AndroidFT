/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import com.ricex.aft.servlet.entity.User;

/** MyBatis Mapper interface for fetching users from the database
 * 
 * @author Mitchell Caisse
 *
 */
public interface UserMapper {

	/** Fetches a user from the database by their user name
	 * 
	 * @param username The username of the user to fetch
	 * @return The user, or null if not found
	 */
	
	public User fetchUserByUsername(String username);
	
	/** Fetches a user from the database by their id
	 * 
	 * @param id The id of the user to fetch
	 * @return The user, or null if not found
	 */
	
	public User fetchUserById(long id);
	
}
