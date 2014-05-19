package com.ricex.aft.common.entity;

import java.io.Serializable;

/** Represents an android device
 * 
 * @author Mitchell Caisse
 *
 */

public class Device implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1662169501591956853L;

	/** The id of this device */
	private long deviceId;
	
	/** The Unique ID of this device, provided by the device itself */
	private String deviceUid;
	
	/** The name of this device */
	private String deviceName;
	
	/** The GCM registration id of the device */
	private String deviceRegistrationId;

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
	 * @return the deviceRegistrationId
	 */
	
	public String getDeviceRegistrationId() {
		return deviceRegistrationId;
	}

	/**
	 * @param deviceRegistrationId the deviceRegistrationId to set
	 */
	
	public void setDeviceRegistrationId(String deviceRegistrationId) {
		this.deviceRegistrationId = deviceRegistrationId;
	}
	
	/** To string method, will return the name of this device
	 *
	 */
	
	public String toString() {
		return getDeviceName();
	}
	
	/**Determines if the two Devices are equal.
	 * 
	 */
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Device)) {
			return false;
		}
		Device dev = (Device) other;		
		return dev.deviceId == this.deviceId;		
	}
	
}
