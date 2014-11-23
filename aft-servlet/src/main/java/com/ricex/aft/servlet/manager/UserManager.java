package com.ricex.aft.servlet.manager;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ricex.aft.common.entity.UserInfoImpl;
import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.entity.UserRole;
import com.ricex.aft.servlet.entity.exception.ValidationException;
import com.ricex.aft.servlet.mapper.UserMapper;

/** The user manager for manipulating users
 * 
 * @author Mitchell Caisse
 *
 */
public enum UserManager implements UserDetailsService {

	/** The singleton instance of the User Manager
	 * 
	 */
	INSTANCE;
	
	/** The user mapper to use for accessing the data store	 */
	private UserMapper userMapper;
	
	/** The Password Encoder to use when creating / saving users and passwords */
	private PasswordEncoder passwordEncoder;
	
	/** Creates a new User Manager
	 * 
	 */
	private UserManager() {
		
	}
	
	/** Fetches the user with the given id
	 * 
	 * @param userId The id of the user to fetch
	 * @return The user with the given id
	 */
	public User getUserById(long userId) {
		return userMapper.getUserById(userId);
	}
	
	/** Fetches the user with the given username
	 * 
	 * @param username The username of the user
	 * @return The user with the specified username
	 */
	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}
	
	/** Fetches the user with the given username,
	 * 	The same as getUserByUsername, but this is implemented by UserDetailsService
	 */
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return getUserByUsername(username);
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
	
	/** Creates the specified user
	 * 
	 * @param user The user to create
	 * @return True if creation successful, false otherwise
	 * @throws ValidationException If the given user was not valid, sets the message to the reason
	 */
	public boolean createUser(User user) throws ValidationException {
		//check to make sure that the user is valid
		if (isUserValid(user)) {
			//hash password, and initialize active and locked fields
			hashUserPassword(user);
			user.setActive(true);
			user.setLocked(false);
			//create the user
			userMapper.createUser(user);
			//asign the user role to the user
			assignRoleToUser(user.getUserId(), UserRole.ROLE_USER);
			return true;
		}
		return false;
	}
	
	/** Hashes the password for the specified user
	 * 
	 * @param user The user to hash the password for
	 */
	private void hashUserPassword(User user) {
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
	}
	
	/** Assigns the given role to the given user
	 * 
	 * @param userId The id of the user to assign the role to
	 * @param role The role to assign to the user
	 * @return true if assigning the role was successful, false otherwise
	 */
	//TODO: Possibly make validation exception more general, or add some method of returning error messages here
	// Poassibly another type of exception? meh.
	public boolean assignRoleToUser(long userId, UserRole role) {
		if (getUserById(userId) == null) {
			return false; // a user with the given id does not exist
		}
		if (userHasRole(userId, role)) {
			return false; // the user already has this role
		}
		//user exists, assign him the role
		userMapper.assignRoleToUser(userId, role);
		return true; // role assignment worked.
	}
	
	/** Determines if a user has a specified role
	 * 
	 * @param userId The id of the user to check
	 * @param role The role to check
	 * @return True if the user has the role, false otherwise
	 */
	public boolean userHasRole(long userId, UserRole role) {
		//check if the list of roles for the user contains the given role
		return userMapper.getRolesForUser(userId).contains(role);
	}
	
	/** Checks if the given user is valid
	 * 
	 * @param user The user to check
	 * @return True if the user is valid, false otherwise
	 * @throws ValidationException If the user is not valid, sets the message as to the reason
	 */
	protected boolean isUserValid(User user) throws ValidationException{
		if (!isUsernameAvailable(user.getUsername())) {
			throw new ValidationException("The username is not valid!");
		}		
		return true;
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

	
	/**
	 * @return the passwordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	
	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
}

