package com.ricex.aft.servlet.manager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ricex.aft.servlet.mapper.UserMapper;

/** The user manager for manipulating users
 * 
 * @author Mitchell Caisse
 *
 */
public enum UserManager {

	/** The singleton instance of the User Manager
	 * 
	 */
	INSTANCE;
	
	/** The user mapper to use for accessing the data store	 */
	private UserMapper userMapper;
	
	/** Creates a new User Manager
	 * 
	 */
	private UserManager() {
		
	}
	
	/** Determines if the given username is available
	 * 
	 * @param username The username to check
	 * @return True if the username is available, false otherwise
	 */
	public boolean isUsernameAvailable(String username) {
		//if there is no user with this username, then it is available
		return userMapper.getUserByUsername(username) == null;
	}
	
	/**
	 * @return the userMapper
	 */
	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	/**
	 * @param userMapper the userMapper to set
	 */
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}	
	
}

