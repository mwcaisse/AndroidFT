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
