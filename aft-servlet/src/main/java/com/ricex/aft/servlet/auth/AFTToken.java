package com.ricex.aft.servlet.auth;

import java.util.Date;

import org.springframework.security.core.Authentication;

/** AFT Authentication Token
 * 
 * @author Mitchell Caisse
 *
 */

public class AFTToken implements Token {

	/** The unique id of the token */
	private String tokenId;
	
	/** The username of the owner of this token */
	private String username;
	
	/** The authentication for this token */
	private Authentication authentication;
	
	/** The client address associated with the token */
	private String clientAddres;
	
	/** When the token was created */
	private Date createDate;
	
	/** When the token expires */
	private Date expirationDate;

	
	/** Creates a new token with the default expiration time
	 *  
	 * @param tokenId The id of the token
	 * @param username The username of the owner of the token
	 * @param auth The authentication of the token
	 * @param clientAddress The address of the client for this token
	 */
	 
	public AFTToken(String tokenId, String username, Authentication auth, String clientAddress) {
		this(tokenId, username, auth, clientAddress, new Date());
	}
	
	/** Creates a new Token
	 *  
	 * @param tokenId The id of the token
	 * @param username The username of the owner of the token
	 * @param auth The authentication of the token
	 * @param clientAddress The address of the client for this token
	 * @param expirationDate When the token expires
	 */	 

	public AFTToken(String tokenId, String username, Authentication auth, String clientAddress, Date expirationDate) {
		this.tokenId = tokenId;
		this.username = username;
		this.authentication = auth;
		this.clientAddres = clientAddress;
		this.expirationDate = expirationDate;
		
		createDate = new Date();
		
	}
	
	/**
	 * @return the tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}
	
	/**
	 * @return the username
	 */
	
	public String getUsername() {
		return username;
	}

	/**
	 * @return the authentication
	 */
	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * @return the clientAddres
	 */
	public String getClientAddres() {
		return clientAddres;
	}

	/** Checks if this token is expired or not
	 * 
	 * @return True if the token is expired, false otherwise
	 */
	@Override
	public boolean isExpired() {
		return new Date().after(expirationDate); 
	}	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AFTToken) {
			AFTToken other = (AFTToken) o;
			return this.tokenId.equals(other.tokenId);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return tokenId.hashCode();
	}
	
	
}
