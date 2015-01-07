package com.ricex.aft.servlet.mapper;

import com.ricex.aft.servlet.entity.DeviceImage;

/** Mapper interface for fetching and modifying Device Image data
 * 
 * @author Mitchell Caisse
 *
 */
public interface DeviceImageMapper {

	/** Retreives the image for the device with the specified id
	 * 
	 * @param deviceId The id of the device to fetch the image for
	 * @return The image for the specified device
	 */
	public DeviceImage getDeviceImage(long deviceId);
	
	/** Creates the specified device image
	 * 
	 * @param deviceImage The device image to create
	 */
	public void createDeviceImage(DeviceImage deviceImage);
	
	/** Updates the currently existing device image
	 * 
	 * @param deviceImage The device image to update
	 */
	public void updateDeviceImage(DeviceImage deviceImage);
	
	/** Deletes the device image for the specified device
	 * 
	 * @param deviceId The id of device to delete teh device image for
	 */
	public void deleteDeviceImage(long deviceId);
	
}
