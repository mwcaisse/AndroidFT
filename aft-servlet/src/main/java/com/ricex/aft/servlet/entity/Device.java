package com.ricex.aft.servlet.entity;

/** Represents an android device
 * 
 * @author Mitchell Caisse
 *
 */

public class Device {
	
	/** The id of this device */
	private long deviceId;
	
	/** The Unique ID of this device, provided by the device itself */
	private long deviceUid;
	
	/** The name of this device */
	private String deviceName;

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
	 * @return the deviceName
	 */
	
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceUid
	 */
	
	public long getDeviceUid() {
		return deviceUid;
	}

	/**
	 * @param deviceUid the deviceUid to set
	 */
	
	public void setDeviceUid(long deviceUid) {
		this.deviceUid = deviceUid;
	}
	
	
	
	
	
}
