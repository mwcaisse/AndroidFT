package com.ricex.aft.servlet.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.response.BooleanResponse;
import com.ricex.aft.common.response.LongResponse;
import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.entity.exception.AuthorizationException;
import com.ricex.aft.servlet.manager.DeviceManager;

/** The SpringMVC Controller for handling devices.
 * 
 * 	Contains methods to fetch devices from the database, as well as register new devices. 	
 * 
 * @author Mitchell Caisse
 *
 */

@Controller
@RequestMapping("/api/device")
public class DeviceController extends ApiController {	
	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(DeviceController.class);
	
	/** Manager for fetching device related information */
	private DeviceManager deviceManager;
	
	/** Creates a new DeviceController Instance, and sets up the deviceManager
	 * 		for use
	 */
	
	public DeviceController() {

	}
	
	@RequestMapping(value="/isRegistered/{deviceUid}", method=RequestMethod.GET, produces={"application/json"})
	public @ResponseBody BooleanResponse isDeviceRegistered(@PathVariable String deviceUid) {
		return new BooleanResponse(deviceManager.deviceExists(deviceUid));
	}
	
	/** Returns a list of all the devices currently in the database. 
	 *
	 * @return List of all available devices
	 */

	@RequestMapping(value="/all", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Device> getAllDevices() {
		return deviceManager.getAllDevices();
	}
	
	/** Returns a list of all the devices that belong to the requesting user
	 * 
	 * @return List of all devices belonging to the requesting user
	 */
	@RequestMapping(value="/mine", method= RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Device> getMyDevices() {
		return deviceManager.getAllDevicesByUser(getCurrentUser().getUserId());
	}
	
	/** Updates the device that currently exists on the database with the specified device.
	 * 
	 *  All fields in the database will be updated, if a field is NULL in the supplied object,
	 *  	the database will be updated will NULL in that field. It is advised to request the 
	 *  	device object, applying desired changes to the object, and then resubmitting it to 
	 *  	make changes.
	 *  
	 *  @param device The device that contains the deviceId to be updated, as well as all of the
	 *  	fields to add to the database
	 *  
	 */
	
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes={"application/json"})
	public void updateDevice(@RequestBody Device device) throws AuthorizationException {
		if (canUserModifyDevice(device, getCurrentUser())) {
			deviceManager.updateDevice(device);
		}
		else {
			throw new AuthorizationException("You are not Authorized to make changes to this Device. You must be the Device owner to modify a device");
		}
	}
	
	/** Creates a new device on the database.
	 * 
	 *  Will add all fields in the specified device to the database. The id field of the device
	 *  	should be left blank, as it will generate a new id for the device, if the field is
	 *  	not NULL it will be ignored.
	 * 	
	 * The registerDevice method should be used in place of this method
	 * 
	 * @param device The new device object to create
	 * @return The ID of the device that was created
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody LongResponse createDevice(@RequestBody Device device) {
		//set the current user as the device owner
		log.debug("User {} is creating a device.", getCurrentUser());
		device.setDeviceOwner(getCurrentUser().toUserInfo());
		return new LongResponse(deviceManager.createDevice(device));
	}
	
	/** Registers a device with the given ID
	 * 
	 *  Will check if the given Device already exists, if so then it will update the existing device.
	 *  	If not it will create the device and return the Id of the created device.
	 *  
	 *  This device should be used when initially registering a device and whenever the device's
	 *  	GCM registration ID changes.
	 * 
	 * @param device The device to register
	 * @return 0 if the device updated successful, the deviceId if a device was created successfully,
	 * 		-1 if the request failed.
	 */
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody LongResponse registerDevice(@RequestBody Device device) throws AuthorizationException {		
		//check if the device exists, if it does update it, otherwise create it
		if (deviceManager.deviceExists(device.getDeviceUid())) {
			//check if the user can modify the device
			if (canUserModifyDevice(device, getCurrentUser())) {
				return new LongResponse(deviceManager.updateDevice(device));
			}
			else {
				throw new AuthorizationException("You are not authorized to change the registration details for this Device");
			}
		}
		else {
			return createDevice(device);
		}	
	}

	/**
	 * @return the deviceManager
	 */
	
	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	/**
	 * @param deviceManager the deviceManager to set
	 */
	
	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}	
	
	/** Determines if the specified user can modify the device
	 * 
	 * @param device The device to check if the user can modify
	 * @param user The user in question
	 * @return True if the given use can modify the device, false if not, or the specified device
	 * 		was not found.
	 */
	private boolean canUserModifyDevice(Device device, User user) { 
		Device dbDevice = deviceManager.getDevice(device.getDeviceId());
		if (dbDevice == null) {
			//couldn't find device by id, try Uid
			dbDevice = deviceManager.getDeviceByUid(device.getDeviceUid());
		}
		return dbDevice != null && user.equals(dbDevice.getDeviceOwner());
	}
	
	

}
