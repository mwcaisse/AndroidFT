/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ricex.aft.servlet.entity.Authority;
import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.mapper.UserMapper;

/** The User manager for fetching and retrieving users
 * 
 * @author Mitchell Caisse
 *
 */
public enum UserManager implements UserDetailsService {

	INSTANCE;

	
	/** The user mapper to fetch users from the database */
	private UserMapper userMapper;
	
	/** Retreives a user by thier username
	 * 
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username)	throws UsernameNotFoundException {
		User user = userMapper.fetchUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user with the name: " + username);
		}
		return userToUserDetails(user);
	}
	
	/** Converts the given user object, to a user details object
	 * 
	 * @param user The user to convert
	 * @return The resulting UserDetails object
	 */
	
	private static UserDetails userToUserDetails(User user) {		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), user.isUserActive(),
					!user.isAccountExpired(), !user.isAccountPasswordExpired(), user.isUserLocked(),user.getAuthorities());
		return userDetails;
	}	
	
}
