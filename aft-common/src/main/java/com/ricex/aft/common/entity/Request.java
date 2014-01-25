package com.ricex.aft.common.entity;

import java.io.Serializable;
import java.util.Date;

/** A request to send a given file to a specified device
 * 
 * @author Mitchell Caisse
 *
 */

public class Request implements Serializable {
	
	/** The id of this update request */
	private long requestId;
	
	/** The id of the file that this request will send */
	private long requestFileId;
	
	/** String representing the location to save the file on the device */
	private String requestFileLocation;
	
	/** The status of this request */
	private RequestStatus requestStatus;
	
	/** The last time that request was changed */
	private Date requestUpdated;
	
	/** The device that this request is associated with */
	private Device requestDevice;

	/** Creates a new request
	 *  Initializes the request with a request status of NEW
	 */
	
	public Request() {
		requestStatus = RequestStatus.NEW;
	}
	
	/**
	 * @return the requestId
	 */
	
	public long getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requestFileId
	 */
	
	public long getRequestFileId() {
		return requestFileId;
	}

	/**
	 * @param requestFileId the requestFileId to set
	 */
	
	public void setRequestFileId(long requestFileId) {
		this.requestFileId = requestFileId;
	}

	/**
	 * @return the requestFileLocation
	 */
	
	public String getRequestFileLocation() {
		return requestFileLocation;
	}

	/**
	 * @param requestFileLocation the requestFileLocation to set
	 */
	
	public void setRequestFileLocation(String requestFileLocation) {
		this.requestFileLocation = requestFileLocation;
	}

	/**
	 * @return the requestStatus
	 */
	
	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	/**
	 * @param requestStatus the requestStatus to set
	 */
	
	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	/**
	 * @return the requestUpdated
	 */
	
	public Date getRequestUpdated() {
		return requestUpdated;
	}

	/**
	 * @param requestUpdated the requestUpdated to set
	 */
	
	public void setRequestUpdated(Date requestUpdated) {
		this.requestUpdated = requestUpdated;
	}

	/**
	 * @return the requestDevice
	 */
	
	public Device getRequestDevice() {
		return requestDevice;
	}

	/**
	 * @param requestDevice the requestDevice to set
	 */
	
	public void setRequestDevice(Device requestDevice) {
		this.requestDevice = requestDevice;
	}
	
	
	
	
}
