/**
 * 
 */
package com.ricex.aft.servlet.controller.view;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.ricex.aft.servlet.AFTProperties;
import com.ricex.aft.servlet.controller.BaseController;

/** Base controller for view controllers
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class ViewController extends BaseController {

	/** Creates a new View Controller
	 * 
	 */
	protected ViewController() {
		
	} 
	
	/** The version attribute
	 * 
	 * @return The version
	 */
	@ModelAttribute("version")
	public String getVersion() {
		return AFTProperties.getVersion();
	}
	
}
