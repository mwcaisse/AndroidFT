package com.ricex.aft.servlet.manager;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.servlet.AFTProperties;
import com.ricex.aft.servlet.controller.api.DeviceController;
import com.ricex.aft.servlet.entity.DeviceImage;
import com.ricex.aft.servlet.mapper.DeviceImageMapper;

/** The manager for the Device Image entity
 * 	Facilitates requests between the controller and the database
 * 
 * @author Mitchell Caisse
 *
 */

public enum DeviceImageManager {

	/** The singleton instance of the Device Image Manager */
	INSTANCE;
	
	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(DeviceImageManager.class);
	
	/** The device image mapper to save / fetch device images */
	private DeviceImageMapper deviceImageMapper;
	
	/** The Device Manager to use */
	private DeviceManager deviceManager;
	
	/** The default image contents*/
	private byte[] defaultImageContents;
	
	/** Creates the Device Image Manager
	 * 
	 */
	private DeviceImageManager() {
		loadDefaultImage();
	}
	
	/** Load in the default device image
	 * 
	 */
	private void loadDefaultImage()  {
		InputStream defaultImageStream = getClass().getResourceAsStream("/device/default_icon.png");
		try {
			defaultImageContents = new byte[defaultImageStream.available()];
			defaultImageStream.read(defaultImageContents, 0, defaultImageStream.available());
			defaultImageStream.close();
		}
		catch (IOException e) {
			log.error("Failed to load default device image", e);
			defaultImageContents = new byte[0]; // set the image to an empty array
		}
	}
	
	/** Returns the device image for the device with the specified id
	 * 
	 * @param deviceId The id of the device to fetch the image for
	 * @return The device image or null if an image if the specified device does not exist
	 */
	public DeviceImage getDeviceImage(long deviceId) {
		return deviceImageMapper.getDeviceImage(deviceId);
	}
	
	/** Returns the device image for the device with the specified id, or the default device image if one does not exist
	 * 
	 * @param deviceId The id of the device to fetch the image for
	 * @return The device image or the default image if the specified device does not exist
	 */
	public DeviceImage getDeviceImageOrDefault(long deviceId) {
		DeviceImage img = deviceImageMapper.getDeviceImage(deviceId);
		if (img == null) {
			img = new DeviceImage();
			img.setDeviceId(deviceId);
			img.setImageContents(defaultImageContents);
			img.setImageSize(defaultImageContents.length);
		}
		return img;
	}
	
	/** Determines if the specified device has a device image
	 * 
	 * @param deviceId The id of the device to checl
	 * @return True if the device has an image, false otherwise
	 */
	public boolean deviceHasImage(long deviceId) {
		return deviceImageMapper.getDeviceImage(deviceId) != null;
	}
	
	/** Creates or updates the device image for the specified device
	 * 
	 * @param deviceId The id of the device to update the image for
	 * @param image The image
	 * @return True if updating was successful, false otherwise
	 */
	public boolean uploadDeviceImage(long deviceId, byte[] image) {
		if (!deviceManager.deviceExists(deviceId) || image.length < 0) {
			//if the device doesn't exist or the image is empty, return false
			return false;
		}
		//create the device image
		DeviceImage deviceImage = new DeviceImage();
		deviceImage.setDeviceId(deviceId);
		deviceImage.setImageContents(image);
		deviceImage.setImageSize(image.length);
		
		if (deviceHasImage(deviceId)) {
			deviceImageMapper.updateDeviceImage(deviceImage);
			return true;
		}
		else {
			deviceImageMapper.createDeviceImage(deviceImage);
			return deviceImage.getDeviceId() >= 0; // succesful if the device id is greater or equal to 0
		}
	}

	/**
	 * @return the deviceImageMapper
	 */
	public DeviceImageMapper getDeviceImageMapper() {
		return deviceImageMapper;
	}

	/**
	 * @param deviceImageMapper the deviceImageMapper to set
	 */
	public void setDeviceImageMapper(DeviceImageMapper deviceImageMapper) {
		this.deviceImageMapper = deviceImageMapper;
	}

	/**
	 * @return the deviceManager
	 */
	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	/**
	 * @param deviceManager the deviceManager to set
	 */
	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	/**
	 * @return the defaultImageContents
	 */
	public byte[] getDefaultImageContents() {
		return defaultImageContents;
	}

	/**
	 * @param defaultImageContents the defaultImageContents to set
	 */
	public void setDefaultImageContents(byte[] defaultImageContents) {
		this.defaultImageContents = defaultImageContents;
	}
	
}
