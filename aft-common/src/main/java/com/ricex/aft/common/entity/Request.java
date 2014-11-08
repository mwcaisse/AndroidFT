package com.ricex.aft.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** A request to send a given file to a specified device
 * 
 * @author Mitchell Caisse
 *
 */

public class Request implements Serializable {
	
	/** The id of this update request */
	private long requestId;
	
	/** The name of this request */
	private String requestName;
	
	/** The list of files that this request contains */
	private List<File> requestFiles;
	
	/** String representing the location to save the file on the device */
	private String requestFileLocation;
	
	/** The base directory to put this request in */
	private RequestDirectory requestDirectory;
	
	/** The status of this request */
	private RequestStatus requestStatus;
	
	/** The message corresponding to the request status */
	private String requestStatusMessage;
	
	/** The last time that request was changed */
	private Date requestUpdated;
	
	/** The device that this request is associated with */
	private Device requestDevice;
	
	/** The owner of this request */
	private UserDetails requestOwner;

	/** Creates a new request
	 *  Initializes the request with a request status of NEW
	 */
	
	public Request() {
		requestStatus = RequestStatus.NEW;
		requestDirectory = RequestDirectory.ROOT;
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
	 * @return the requestName
	 */
	
	public String getRequestName() {
		return requestName;
	}

	/**
	 * @param requestName the requestName to set
	 */
	
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	/**
	 * @return the requestFiles
	 */
	
	public List<File> getRequestFiles() {
		return requestFiles;
	}

	/**
	 * @param requestFiles the requestFiles to set
	 */
	
	public void setRequestFiles(List<File> requestFiles) {
		this.requestFiles = requestFiles;
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
	 * @return the requestDirectory
	 */
	
	public RequestDirectory getRequestDirectory() {
		return requestDirectory;
	}

	/**
	 * @param requestDirectory the requestDirectory to set
	 */
	
	public void setRequestDirectory(RequestDirectory requestDirectory) {
		this.requestDirectory = requestDirectory;
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
	 * @return the requestStatusMessage
	 */
	
	public String getRequestStatusMessage() {
		return requestStatusMessage;
	}

	/**
	 * @param requestStatusMessage the requestStatusMessage to set
	 */
	
	public void setRequestStatusMessage(String requestStatusMessage) {
		this.requestStatusMessage = requestStatusMessage;
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
	
	
	/**
	 * @return the requestOwner
	 */
	public UserDetails getRequestOwner() {
		return requestOwner;
	}
	
	/**
	 * @param requestOwner the requestOwner to set
	 */
	public void setRequestOwner(UserDetails requestOwner) {
		this.requestOwner = requestOwner;
	}

	/** Determines if two requests are equal
	 * 
	 */
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Request)) {
			return false;
		}
		Request req = (Request) other;
		return req.requestId == this.requestId;
	}
	
	
	
}
