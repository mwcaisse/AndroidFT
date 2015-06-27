package com.ricex.aft.servlet.mapper;

import java.util.List;

import com.ricex.aft.servlet.entity.UserAuthenticationToken;

/** UserAuthenticationToken Mapper for retreiving and updating User Authentication Tokens
 * 
 * @author Mitchell Caisse
 *
 */

public interface UserAuthenticationTokenMapper {

	/** Fetches the User Authentication Token with the given id
	 * 
	 * @param id The id of the token to fetch
	 * @return The token
	 */
	public UserAuthenticationToken getUserAuthenticationToken(long id);

	/** Fetches a User Authentication Token by the Token string
	 * 
	 * @param token The token string of the token to fetch
	 * @return The token
	 */
	
	public UserAuthenticationToken getUserAuthenticationTokenByToken(String token);
	
	/** Fetches all of the User Authentication Tokens associated with a user
	 * 
	 * @param userId The ID of the user
	 * @return The tokens
	 */
	
	public List<UserAuthenticationToken> getUserAuthenticationTokensForUser(long userId);
	
	/** Creates a new User Authentication Token
	 * 
	 * @param token The token to create
	 */
	
	public void createUserAuthenticationToken(UserAuthenticationToken token);
	
	/** Updates an existing User Authentication Token
	 * 
	 * @param token The token to update
	 */
	
	public void updateUserAuthenticationToken(UserAuthenticationToken token);
	
}
