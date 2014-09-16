/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.List;

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
	
	/** Returns the device with the given ID
	 * 
	 * @param deviceId The id of the device to fetch
	 * @return The device with the given id
	 */
	public Device getDevice(long deviceId) {
		return deviceMapper.getDeviceId(deviceId);
	}
	
	/** Checks if the given device key matches the device key defined for the given device.
	 * 
	 * If the device key of the specified device is null, then it is considered matching.
	 * 
	 * @param deviceId The id of the device to check
	 * @param deviceKey The string to test
	 * @return True if the device keys match, or the key of the specified device is null
	 */
	public boolean deviceKeyEquals(long deviceId, String deviceKey) {
		String dk = deviceMapper.getDeviceKey(deviceId);
		return (dk == null) || (dk.equals(deviceKey));
	}
	
	/** Updates the given device
	 * 
	 * @param device The device to update
	 * @return 0 if successful, -1 if failed
	 */
	
	public long updateDevice(Device device) {
		if (deviceKeyEquals(device.getDeviceId(), device.getDeviceKey())) {
			deviceMapper.updateDevice(device);
			return 0;
		}
		return -1;
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
