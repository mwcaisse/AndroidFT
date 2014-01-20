package com.ricex.aft.servlet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.servlet.entity.Device;
import com.ricex.aft.servlet.manager.DeviceManager;

/**
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/device")
public class DeviceController {	
	
	/** Manager for fetching device related information */
	private DeviceManager deviceManager;
	
	public DeviceController() {
		deviceManager = DeviceManager.INSTANCE;
	}
	
	/** Returns a list of all available devices
	 * 
	 * @return List of all available devices
	 */
	
	@RequestMapping(value="/all", method= RequestMethod.GET)
	public @ResponseBody List<Device> getAllDevices() {
		return null;
	}
	
	/** Updates the information on the specified device
	 *  Used when changing information based upon
	 *  
	 *  TODO: Have a separate create device function?
	 */
	
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void updateDevice() {
		
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void createDevice() {
		
	}
	
	

}
