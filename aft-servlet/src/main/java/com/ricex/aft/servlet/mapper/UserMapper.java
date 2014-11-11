package com.ricex.aft.servlet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.entity.UserRole;

/** User Mapper for retreiving user details
 * 
 * @author Mitchell Caisse
 *
 */
public interface UserMapper {

	/** Fetches the user with the given id
	 * 
	 * @param userId The id of the user
	 * @return The user with the given id or null if a user with the given id was not found
	 */
	public User getUserById(long userId);
	
	/** Fetches the user with the given username
	 * 
	 * @param username The username of the user
	 * @return The user with the specified username or null if a user with the username was
	 * 		not found
	 */
	public User getUserByUsername(String username);
	
	/** Fetches the roles for the user with the given user id
	 * 
	 * @param userId The id of the user
	 * @return The list of roles that the user has been assigned
	 */
	public List<UserRole> getRolesForUser(long userId);
	
	/** Creates the given user
	 * 
	 * @param user The user to create
	 */
	public void createUser(User user);
	
	/** Assigns the specified role to the given user
	 * 
	 * @param userId The id of the user to add the role to
	 * @param role The role to add to the given user
	 */
	public void assignRoleToUser(@Param("userId") long userId, @Param("role") UserRole role);
	
}
