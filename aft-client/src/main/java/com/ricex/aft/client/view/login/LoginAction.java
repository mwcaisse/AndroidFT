package com.ricex.aft.client.view.login;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.ricex.aft.client.AFTClient;
import com.ricex.aft.client.config.AFTClientConfig;

public class LoginAction extends AbstractAction {
	
	/** The LoginWindow this Login Action is operating on */
	private LoginWindow loginWindow;
	
	/** Creates a new Login Action for the given login window
	 * 
	 * @param loginWindow The LoginWindow to create the login action for
	 */
	
	public LoginAction(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	/** Called when the user presses the Login button
	 * 
	 */
	
	public void actionPerformed(ActionEvent ae) {
		
		AFTClientConfig config = AFTClientConfig.INSTANCE;
		
		//set the variables from the window into the config
		config.setServerAddress(loginWindow.getServerAddress());
		config.setClientUserName(loginWindow.getUsername());
		config.setClientPassword(loginWindow.getUserPassword());
		
		//close / hide the login window
		loginWindow.setVisible(false);
		
		// create a show the client window
		AFTClient clientWindow = new AFTClient();
		clientWindow.setVisible(true);
		
		
	}

}
