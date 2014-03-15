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
		if (_instance == null) {
			_instance = new DeviceCache();
		}
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
		elements.put(device.getDeviceId(), device);
	}

}
