package com.ricex.aft.servlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mitchell Caisse
 *
 */

@Controller
public class DeviceController {
	
	public DeviceController() {
		System.out.println("We are creating a device controller!");
	}
	
	@RequestMapping("/device")
	public @ResponseBody String deviceTest() {
		return "Hello World";
	}

}
