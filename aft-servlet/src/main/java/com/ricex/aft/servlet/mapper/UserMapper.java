package com.ricex.aft.servlet.mapper;

import com.ricex.aft.servlet.entity.User;

/** User Mapper for retreiving user details
 * 
 * @author Mitchell Caisse
 *
 */
public interface UserMapper {

	/** Fetches the user with the given id
	 * 
	 * @param id The id of the user
	 * @return The user with the given id or null if a user with the given id was not found
	 */
	public User getUserById(long id);
	
	/** Fetches the user with the given username
	 * 
	 * @param username The username of the user
	 * @return The user with the specified username or null if a user with the username was
	 * 		not found
	 */
	public User getUserByUsername(String username);
	
}
