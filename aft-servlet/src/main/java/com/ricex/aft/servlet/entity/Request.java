package com.ricex.aft.servlet.entity;

import java.util.Date;

/** A request to send a given file to a specified device
 * 
 * @author Mitchell Caisse
 *
 */

public class Request {
	
	/** The id of this update request */
	private long requestId;
	
	/** The file that this request is going to copy over */
	private File requestFile;
	
	/** String representing the location to save the file on the device */
	private String fileLocation;
	
	/** The status of this request */
	private RequestStatus requestStatus;
	
	/** The last time that status was changed */
	private Date requestStatusUpdated;
	
	/** The device that this request is associated with */
	private Device requestDevice;

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
	 * @return the requestFile
	 */
	
	public File getRequestFile() {
		return requestFile;
	}

	/**
	 * @param requestFile the requestFile to set
	 */
	
	public void setRequestFile(File requestFile) {
		this.requestFile = requestFile;
	}

	/**
	 * @return the fileLocation
	 */
	
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation the fileLocation to set
	 */
	
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
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
	 * @return the requestStatusUpdated
	 */
	
	public Date getRequestStatusUpdated() {
		return requestStatusUpdated;
	}

	/**
	 * @param requestStatusUpdated the requestStatusUpdated to set
	 */
	
	public void setRequestStatusUpdated(Date requestStatusUpdated) {
		this.requestStatusUpdated = requestStatusUpdated;
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
