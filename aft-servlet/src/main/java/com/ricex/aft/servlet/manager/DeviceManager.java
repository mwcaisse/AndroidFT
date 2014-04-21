/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.servlet.mapper.DeviceMapper;

/** Device Manager, responsible for polling the Mapper/Database for information on devices
 * 
 * @author Mitchell Caisse
 *
 */
public enum DeviceManager {

	/** The singleton instance */
	INSTANCE;
	
	/** The device mapper that will be used to fetch data from the database */
	private DeviceMapper deviceMapper;
	
	/** Initializes the device manager 
	 * 
	 */
	
	private DeviceManager() {
	
	}
	
	/** Returns a list of all devices
	 * 
	 * @return list of all devices
	 */
	
	public List<Device> getAllDevices() {
		return deviceMapper.getAllDevices();
	}
	
	/** Updates the given device
	 * 
	 * @param device The device to update
	 * @return 0
	 */
	
	public long updateDevice(Device device) {
		deviceMapper.updateDevice(device);
		return 0;
	}

	/** Adds the given device to the database, and returns the ID of the new device
	 * 
	 * @param device The new device to add
	 * @return The id of the device
	 */
	
	public long createDevice(Device device) {
		device.setDeviceId(-1);
		deviceMapper.createDevice(device);
		return device.getDeviceId();
	}	
	
	/** Determines if a device with the specified ID exists
	 * 
	 * @param device The device with the ID
	 * @return True if it exists, false if not
	 */
	
	public boolean deviceExists(Device device) {
		return deviceMapper.getDeviceUid(device.getDeviceUid()) != null;
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
