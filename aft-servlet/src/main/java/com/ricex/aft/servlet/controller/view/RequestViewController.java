package com.ricex.aft.servlet.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/** View Controller for the Request page
 * 
 * @author Mitchell Caisse
 *
 */
@Controller
@RequestMapping(value= "/request/")
public class RequestViewController extends ViewController {

	
	/** Returns the index view for the request
	 * 
	 * @return The home / index view for requests
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView requestRoot() {
		return new ModelAndView("request/requests");
	}
	
	/** Returns the view for creating a new request
	 * 
	 * @return The view for creating a new request
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET) 
	public ModelAndView createRequest() {
		return new ModelAndView("request/createRequest");
	}
}
