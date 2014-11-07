package com.ricex.aft.servlet.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/** View Controller for the devices page
 * 
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping(value= "/device/")
public class DeviceViewController {

	/** Returns the index view for the device
	 * 
	 * @return The home / index view for devices
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView deviceRoot() {
		return new ModelAndView("device");
	}
	
}
