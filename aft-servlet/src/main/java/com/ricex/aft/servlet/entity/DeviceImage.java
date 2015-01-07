package com.ricex.aft.servlet.entity;

import com.ricex.aft.common.entity.AbstractEntity;


/** Class to store + retreive images for a device
 * 
 * @author Mitchell Caisse
 *
 */
public class DeviceImage extends AbstractEntity {

	/** The id of the device this image belongs to */
	private long deviceId;
	
	/** The uid of the device this image belongs to */
	private String deviceUid;
	
	/** The contents of the image */
	private byte[] imageContents;
	
	/** The size of the image */
	private long imageSize;

	/**
	 * @return the deviceId
	 */
	public long getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceUid
	 */
	public String getDeviceUid() {
		return deviceUid;
	}

	/**
	 * @param deviceUid the deviceUid to set
	 */
	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}

	/**
	 * @return the imageContents
	 */
	public byte[] getImageContents() {
		return imageContents;
	}

	/**
	 * @param imageContents the imageContents to set
	 */
	public void setImageContents(byte[] imageContents) {
		this.imageContents = imageContents;
	}

	/**
	 * @return the imageSize
	 */
	public long getImageSize() {
		return imageSize;
	}

	/**
	 * @param imageSize the imageSize to set
	 */
	public void setImageSize(long imageSize) {
		this.imageSize = imageSize;
	}	
	
}
