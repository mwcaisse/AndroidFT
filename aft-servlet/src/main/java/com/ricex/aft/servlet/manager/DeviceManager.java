/**
 * 
 */
package com.ricex.aft.servlet.manager;

import com.ricex.aft.servlet.mapper.DeviceMapper;

/** Device Manager, responsible for polling the Mapper/Database for information on devices
 * 
 * @author Mitchell Caisse
 *
 */
public enum DeviceManager {

	INSTANCE;
	
	/** The device mapper that will be used to fetch data from the database */
	private DeviceMapper deviceMapper;
	
	/** Initializes the device manager 
	 * 
	 */
	
	private DeviceManager() {
	
	}

	/** Retrieves the device mapper this manager is using
	 * @return the deviceMapper
	 */
	public DeviceMapper getDeviceMapper() {
		return deviceMapper;
	}

	/** Sets the implementation of the device mapper that this manager will use
	 * @param deviceMapper the deviceMapper to set
	 */
	public void setDeviceMapper(DeviceMapper deviceMapper) {
		this.deviceMapper = deviceMapper;
	}
	
}
