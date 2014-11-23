/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.List;

import com.ricex.aft.common.entity.Device;

/** Device Mapper for fetching device data
 * 
 * @author Mitchell Caisse
 *
 */
public interface DeviceMapper {

	/** Returns a list of all devices
	 * 
	 * @return list of devices
	 */
	
	public List<Device> getAllDevices();
	
	/** Returns a list of all devices that belong to the given user
	 * 
	 * @param userId The ID of the user
	 * @return The list of devices this user owns
	 */
	public List<Device> getAllDevicesByUser(long userId);
	
	/** Returns the device with the given Id
	 * 
	 * @param deviceId The id of the device to fetch
	 * @return The device with the given ID
	 */
	public Device getDeviceId(long deviceId);
	
	/** Returns the device with the given UID
	 * 
	 * @param deviceUid The UID of the device to fetch
	 * @return The device with the given UID
	 */
	
	public Device getDeviceUid(String deviceUid);
	
	/** Updates the device
	 * 
	 * @param device The device to update, uses the unique id to update
	 */
	
	public void updateDevice(Device device);
	
	/** Creates the given device. Will assign the device a new id
	 * 
	 * @param device The device to create
	 */
	
	public void createDevice(Device device);
}
