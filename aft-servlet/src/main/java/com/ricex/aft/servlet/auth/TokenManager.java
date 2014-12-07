package com.ricex.aft.servlet.auth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

/** Manages the authentication tokens
 * 
 * @author Mitchell Caisse
 *
 */
public class TokenManager {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(TokenManager.class);
	
	/** The collection of tokens */
	private Map<String, Token> tokens;
	
	
	/** Creates a new token manager */
	public TokenManager() {
		tokens = new HashMap<String, Token>();		
	}
	
	/** Gets the Token with the specified token id 
	 * 
	 * @param tokenId The id of the token
	 * @return The token with the given ID, or null if a valid token with that id was not found
	 */
	public Token getToken(String tokenId) {
		
		log.debug("Number of Tokens: " + tokens.size());
		
		Token token = tokens.get(tokenId);	
		log.debug("Fetching token with id: " + tokenId + " Found: " + token);
		
		//check if the token is valid before returning it, if not remove it from the map
		if (token != null && token.isExpired()) {
			log.debug("Expirering Token: " + tokenId);
			tokens.remove(token.getTokenId());
			token = null;
		}
		return token;
	}
	
	/** Creates a new Token with the given authentication
	 * 
	 * @param auth The authentication assosiated with the token
	 * @return The newly created token
	 */
	public AFTToken createToken(Authentication auth, String clientAddress) {
		AFTToken token = new AFTToken(createTokenId(), auth, clientAddress, getExpirationDate());
		log.debug("Adding token with id: " + token.getTokenId());
		tokens.put(token.getTokenId(), token);		
		return token;
	}
	
	/** Creates a unique Token ID using a random UUID
	 * 
	 * @return A new unused token id
	 */
	protected String createTokenId() {
		return UUID.randomUUID().toString();
	}
	
	/** Calculates an expiration date for a token.
	 *	 Creates a date one hour from now
	 *
	 * @return The expiration date for a token
	 */
	protected Date getExpirationDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 1);
		return cal.getTime();
	}
	
}
