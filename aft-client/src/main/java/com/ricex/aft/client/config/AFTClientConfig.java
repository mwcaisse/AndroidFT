/**
 * 
 */
package com.ricex.aft.client.config;

/** The configuration class for the Client
 * 
 * @author Mitchell Caisse
 *
 */
public enum AFTClientConfig {

	INSTANCE;
	
	
	/** The username of the account to use when connecting */
	private String clientUserName;
	
	/** The password of the account to use when connecting */
	private String clientPassword;
	
	/** The address of the server to connect to */
	private String serverAddress;
	
	private AFTClientConfig() {
		this.serverAddress="http://localhost:8080/";
		this.clientUserName = "";
		this.clientPassword = "";
	}

	/**
	 * @return the clientUserName
	 */
	
	public String getClientUserName() {
		return clientUserName;
	}

	/**
	 * @param clientUserName the clientUserName to set
	 */
	
	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}

	/**
	 * @return the clientPassword
	 */
	
	public String getClientPassword() {
		return clientPassword;
	}

	/**
	 * @param clientPassword the clientPassword to set
	 */
	
	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

	/**
	 * @return the serverAddress
	 */
	
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * @param serverAddress the serverAddress to set
	 */
	
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	
	
	
}
