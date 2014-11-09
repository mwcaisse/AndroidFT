package com.ricex.aft.servlet.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/** Login Controller to support logging in and logging out
 * 
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping(value= "/")
public class LoginViewController extends ViewController {

	/** Access point to log into the application
	 * 
	 * @return The login view
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		if (isAuthenticated()) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("auth/login");
	}
	
	/** View to allow a new user to register
	 * 
	 * @return The view for registration
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView register() {
		if (isAuthenticated()) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("auth/register");
	}
}
