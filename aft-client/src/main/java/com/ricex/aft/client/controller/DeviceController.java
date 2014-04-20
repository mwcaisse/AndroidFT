/**
 * 
 */
package com.ricex.aft.client.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.request.device.FetchAllDevicesRequest;
import com.ricex.aft.client.request.device.FetchDeviceByIdRequest;
import com.ricex.aft.common.entity.Device;

/**
 *  The controller for handling fetches of Devices from the web service
 * 
 * @author Mitchell Caisse
 *
 */
public class DeviceController extends AbstractController {

	/** The logger */
	private static Logger log = LoggerFactory.getLogger(DeviceController.class);
	
	/** The singleton instance of the device controller */
	private static DeviceController _instance;
	
	/** Gets the singleton instance of the device controller
	 * 
	 * @return The singleton instance
	 */
	
	public static DeviceController getInstance() {
		if (_instance == null) {
			_instance = new DeviceController();
		}
		return _instance;
	}
	
	/** Creates a new Device Controller
	 * 
	 */
	
	private DeviceController() {
		
	}
	
	/** Retrieves the device with the given id from the server
	 * 
	 * @param deviceId The id of the device to fetch
	 * @param listener The request listener to notify when the request completes
	 * @return The request that was created
	 */
	
	public IRequest<Device> getDevice(long deviceId, RequestListener<Device> listener) {
		FetchDeviceByIdRequest request = new FetchDeviceByIdRequest(deviceId, listener);
		makeAsyncRequest(request);
		return request;
	}
	
	/** Fetches all of the devices from the web service
	 * 
	 * @param listener The listener to notify when the request completes
	 * @return The request that was created
	 */
	
	public IRequest<List<Device>> getAllDevices(RequestListener<List<Device>> listener) {
		log.info("Making a request to get all devices, listener {} ", listener);
		FetchAllDevicesRequest request = new FetchAllDevicesRequest(listener);
		makeAsyncRequest(request);
		return request;
	}
	
}
