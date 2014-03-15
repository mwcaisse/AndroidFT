/**
 * 
 */
package com.ricex.aft.client.controller;

import java.util.List;

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
	 */
	
	public void getDevice(long deviceId, RequestListener<Device> listener) {
		FetchDeviceByIdRequest request = new FetchDeviceByIdRequest(deviceId, listener);
		makeAsyncRequest(request);
	}
	
	/** Fetches all of the devices from the web service
	 * 
	 * @param listener The listener to notify when the request completes
	 */
	
	public void getAllDevices(RequestListener<List<Device>> listener) {
		FetchAllDevicesRequest request = new FetchAllDevicesRequest(listener);
		makeAsyncRequest(request);
	}
	
}