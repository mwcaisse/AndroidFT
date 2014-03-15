/**
 * 
 */
package com.ricex.aft.client.cache;

import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class DeviceCache extends AbstractCache<Device, Long> {	
	
	/** The singleton instance */
	private static DeviceCache _instance;
	
	/** Returns the singleton instance of the DeviceCache
	 * 
	 * @return The singleton instance
	 */
	
	public static DeviceCache getInstance() {
		System.out.println("Getting device cache! ");
		if (_instance == null) {
			_instance = new DeviceCache();
		}
		System.out.println("Returning device cache! ");
		return _instance;
	}
	
	/** Creates a new Device cache and initializes the cache for storing elements
	 * 
	 */
	
	public DeviceCache() {
		
	}

	/** Adds the given device to the element table without firing an update event
	 * 
	 * @param device The device to add
	 */
	
	protected void addElement(Device device) {
		System.out.println("DeviceCache addElement");
		elements.put(device.getDeviceId(), device);
		System.out.println("DeviceCache addElement returning");
	}

}
