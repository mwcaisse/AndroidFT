package com.ricex.aft.common.data;

import com.ricex.aft.common.entity.RequestStatus;
import com.ricex.aft.common.entity.UserInfo;


/** Container Class to transfer file information / file meta data
 * 
 * @author Mitchell Caisse
 *
 */

public class FileInfo {

	/** The id of the file */
	private long id;
	
	/** The name of the file */
	private String fileName;
	
	/** The size of the file */
	private long fileSize;
	
	/** The owner of the file */
	private UserInfo fileOwner;
	
	/** The id of the request the file belongs to */
	private long requestId;
	
	private String requestName;
	
	/** The status of the request */
	private RequestStatus requestStatus;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the fileOwner
	 */
	public UserInfo getFileOwner() {
		return fileOwner;
	}

	/**
	 * @param fileOwner the fileOwner to set
	 */
	public void setFileOwner(UserInfo fileOwner) {
		this.fileOwner = fileOwner;
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
}
