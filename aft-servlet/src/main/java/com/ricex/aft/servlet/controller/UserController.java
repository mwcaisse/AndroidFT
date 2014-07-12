/**
 * 
 */
package com.ricex.aft.servlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.servlet.manager.UserManager;

/**
 * @author Mitchell Caisse
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	/** The Manager for performing operations on users */
	private UserManager userManager;

	
	/** Creates a new User Controller
	 * 
	 */
	
	public UserController() {
		userManager = UserManager.INSTANCE;
	}
	
	/** Controller entry to allow the user to log in / test login in a ui
	 * 
	 */
	
	@RequestMapping(value="/login", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody boolean login() {
		return true;
	}
}
