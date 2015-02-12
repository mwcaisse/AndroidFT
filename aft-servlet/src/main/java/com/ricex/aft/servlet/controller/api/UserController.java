package com.ricex.aft.servlet.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.common.response.BooleanResponse;
import com.ricex.aft.servlet.entity.UserRegistration;
import com.ricex.aft.servlet.entity.exception.EntityException;
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
	
	/** Checks if the specified username is available or not
	 * 
	 * @param username The username to check
	 * @return True if the username is available, false otherwise
	 */
	@RequestMapping(value="/isAvailable", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody BooleanResponse isUsernameAvailable(@RequestParam String username) {
		return new BooleanResponse(userManager.isUsernameAvailable(username));
	}
	
	/** Creates the given user account, and gives them a basic role of USER
	 * 
	 * @param userRegistration The user object representing the user to create
	 * @return True if creation was successful, false otherwise
	 * @throws EntityException If the User is invalid
	 */
	@RequestMapping(value="/register", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody BooleanResponse registerUser(@RequestBody UserRegistration userRegistration) throws EntityException {
		return new BooleanResponse(userManager.createUser(userRegistration));
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
