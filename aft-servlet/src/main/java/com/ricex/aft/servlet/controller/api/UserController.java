package com.ricex.aft.servlet.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ricex.aft.common.auth.AFTAuthentication;
import com.ricex.aft.common.auth.AuthUser;
import com.ricex.aft.common.response.BooleanResponse;
import com.ricex.aft.servlet.auth.APIUserAuthenticator;
import com.ricex.aft.servlet.auth.Token;
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
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	/** The user manager to use */
	private UserManager userManager;
	
	/** The User Authenticator to use to authenticate users */
	private APIUserAuthenticator userAuthenticator;

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
	
	/** Log in via API, without the html form at /login
	 * 
	 *  If login is successful will grant the user an authorization token in the AFTToken Header and return true.
	 * 
	 * @param user The user's login credentials
	 * @param response The http response to be sent back to the client
	 * @return True if the user logged in successfully, false if not
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody BooleanResponse testLogin(@RequestBody AuthUser user, HttpServletRequest request, HttpServletResponse response) {		

		Token token = userAuthenticator.authenticateUser(user, request.getRemoteAddr());
		//check if a valid token was received
		if (token == null) {
			return new BooleanResponse(false);
		}
		else {
			response.addHeader(AFTAuthentication.AFT_AUTH_TOKEN_HEADER, token.getTokenId());			
			return new BooleanResponse(true);
		}
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

	/**
	 * @return the userAuthenticator
	 */
	public APIUserAuthenticator getUserAuthenticator() {
		return userAuthenticator;
	}

	/**
	 * @param userAuthenticator the userAuthenticator to set
	 */
	public void setUserAuthenticator(APIUserAuthenticator userAuthenticator) {
		this.userAuthenticator = userAuthenticator;
	}	
	
}
