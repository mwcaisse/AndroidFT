/**
 * 
 */
package com.ricex.aft.servlet.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/** View Controller for the home page
 * 
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping(value= "/")
public class HomeController extends ViewController {

	/** The controller entry to return the view for the home page
	 * 
	 * @return The view representing the home page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("home");
		return model;
	}
}
