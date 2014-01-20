/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.List;

import com.ricex.aft.servlet.entity.Device;

/** MyBatis Mapper for devices
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
	
	/** Returns the device with the given UID
	 * 
	 * @param deviceUid The UID of the device to fetch
	 * @return The device with the given UID
	 */
	
	public Device getDeviceUid(long deviceUid);
	
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
