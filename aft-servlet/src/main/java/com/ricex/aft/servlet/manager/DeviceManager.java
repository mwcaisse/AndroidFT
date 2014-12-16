/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.servlet.mapper.DeviceMapper;

/** The Manager for the Device entity. 
 *  Facilitates requests between the DeviceController and the database.
 * 
 * @author Mitchell Caisse
 *
 */

public enum DeviceManager {

	/** The singleton instance */
	INSTANCE;

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(DeviceManager.class);
	
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
	
	/** Returns a list of all devices the specified user owns
	 * 
	 * @param userId The id of the user
	 * @return The  list of all devices the user owns
	 */
	public List<Device> getAllDevicesByUser(long userId) {
		return deviceMapper.getAllDevicesByUser(userId);
	}
	
	/** Returns the device with the given ID
	 * 
	 * @param deviceId The id of the device to fetch
	 * @return The device with the given id
	 */
	public Device getDevice(long deviceId) {
		return deviceMapper.getDeviceId(deviceId);
	}
	
	/** Returns the device with the given uid
	 * 
	 * @param deviceUid The uid of the device
	 * @return The device
	 */
	public Device getDeviceByUid(String deviceUid) {
		return deviceMapper.getDeviceUid(deviceUid);
	}
	
	/** Updates the given device
	 * 
	 * @param device The device to update
	 * @return 0 if successful, -1 if failed
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
		device.setId(-1);
		log.debug("Creating device with owner: " + device.getDeviceOwner().getId());
		deviceMapper.createDevice(device);
		return device.getId();
	}	
	
	/** Determines if a device with the specified ID exists
	 * 
	 * @deprecated Use the {@link #deviceExists(long) deviceExists} method instead.
	 * 
	 * @param device The device with the ID
	 * @return True if it exists, false if not
	 */
	@Deprecated
	public boolean deviceExists(Device device) {
		return deviceExists(device.getDeviceUid());
	}
	
	/** Determines if a device with the specified UID exists
	 * 
	 * @param deviceUid The device with the UID
	 * @return True if it exists, false if not
	 */
	
	public boolean deviceExists(String deviceUid) {
		return deviceMapper.getDeviceUid(deviceUid) != null;
	}
	
	/** Determines if a device with the specified ID exists
	 * 
	 * @param deviceId The id of the device to check
	 * @return True if it exists, false if not
	 */
	public boolean deviceExists(long deviceId) {
		return deviceMapper.getDeviceId(deviceId) != null;
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
