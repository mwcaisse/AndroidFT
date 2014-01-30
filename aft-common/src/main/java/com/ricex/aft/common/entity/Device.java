package com.ricex.aft.common.entity;

import java.io.Serializable;
import java.util.List;

/** Represents an android device
 * 
 * @author Mitchell Caisse
 *
 */

public class Device implements Serializable {
	
	/** The id of this device */
	private long deviceId;
	
	/** The Unique ID of this device, provided by the device itself */
	private long deviceUid;
	
	/** The name of this device */
	private String deviceName;
	
	/** The GCM registration id of the device */
	private String deviceRegistrationId;
	
	/** The requests associated with this device */
	private List<Request> requests;

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

	/**
	 * @return the requests
	 */
	
	public List<Request> getRequests() {
		return requests;
	}

	/**
	 * @param requests the requests to set
	 */
	
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}	
	
}
