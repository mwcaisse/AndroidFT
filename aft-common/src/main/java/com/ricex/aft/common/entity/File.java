/**
 * 
 */
package com.ricex.aft.common.entity;

import java.io.Serializable;

/** Represents the meta data of a file
 * 
 * @author Mitchell Caisse
 *
 *
 */
public class File implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -7013774805895194303L;

	/** The id of this file */
	private long fileId;
	
	/** The id of the request that this file belongs to */
	private Long requestId;
	
	/** The name of the file */
	private String fileName;
	
	/** The Owner of this file */
	private UserInfoImpl fileOwner;
	
	/**
	 * @return the fileId
	 */
	
	public long getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}	

	/**
	 * @return the requestId
	 */
	
	public Long getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
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
	 * @return the fileOwner
	 */
	public UserInfoImpl getFileOwner() {
		return fileOwner;
	}

	
	/**
	 * @param fileOwner the fileOwner to set
	 */
	public void setFileOwner(UserInfoImpl fileOwner) {
		this.fileOwner = fileOwner;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof File)) {
			return false;
		}
		File file = (File) other;
		return file.fileId == this.fileId;
	}
	
}
