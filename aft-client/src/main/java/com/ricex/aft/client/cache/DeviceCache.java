/**
 * 
 */
package com.ricex.aft.client.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/** Adds the specified device to the cache
	 * @param device The device to add
	 */
	public void add(Device device) {		
		elements.put(device.getDeviceId(), device);
	}

}
