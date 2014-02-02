package com.ricex.aft.servlet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.common.entity.Device;
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
	
	@RequestMapping(value="/all", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Device> getAllDevices() {
		return deviceManager.getAllDevices();
	}
	
	/** Updates the information on the specified device
	 *  Used when changing information based upon
	 *  
	 *  TODO: Have a separate create device function?
	 */
	
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes={"application/json"})
	public void updateDevice(@RequestBody Device device) {
		deviceManager.updateDevice(device);
	}
	
	/** Creates a new device
	 * 
	 * @param device The new device to create
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody long createDevice(@RequestBody Device device) {
		return deviceManager.createDevice(device);
	}
	
	/** Registers a device with the given ID
	 * 
	 * If a device with the id exists, it will update the device
	 * If a device with the ID does NOT exist, it will create a new device
	 * 
	 * @param device
	 * @return 0 if updated, device ID if created
	 */
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody long registerDevice(@RequestBody Device device) {		
		//check if the device exists, if it does update it, otherwise create it
		if (deviceManager.deviceExists(device)) {
			return deviceManager.updateDevice(device);
		}
		else {
			return deviceManager.createDevice(device);
		}	
	}

}
