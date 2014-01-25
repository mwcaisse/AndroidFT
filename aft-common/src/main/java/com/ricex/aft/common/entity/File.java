/**
 * 
 */
package com.ricex.aft.common.entity;

import java.io.Serializable;

/** Represents a file that will be sent over to a device
 * 
 * @author Mitchell Caisse
 *
 *
 */
public class File implements Serializable {

	/** The id of this file */
	private long fileId;
	
	/** The file contents */
	private byte[] fileContents;
	
	/** The name of the file */
	private String fileName;
	
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
	 * @return the fileContents
	 */
	
	public byte[] getFileContents() {
		return fileContents;
	}

	/**
	 * @param fileContents the fileContents to set
	 */
	
	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
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
	
}
