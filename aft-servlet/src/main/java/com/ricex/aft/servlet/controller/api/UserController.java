package com.ricex.aft.servlet.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ricex.aft.servlet.manager.UserManager;


/** The user controller to perform user related tasks
 * 
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/api/user")
public class UserController extends ApiController {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(FileController.class);
	
	/** The user manager to use */
	private UserManager userManager;
	
	/** Creates a new User Controller
	 * 
	 */
	public UserController() {
		
	}
	
	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}
	
	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
}
