package com.ricex.aft.servlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mitchell Caisse
 *
 */

@Controller
public class DeviceController {
	
	@RequestMapping("/device")
	public String deviceTest() {
		return "Hello World";
	}

}
