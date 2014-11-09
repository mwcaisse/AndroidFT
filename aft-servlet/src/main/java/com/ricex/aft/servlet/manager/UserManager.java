package com.ricex.aft.servlet.manager;

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

