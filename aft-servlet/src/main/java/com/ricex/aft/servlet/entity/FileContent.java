/**
 * 
 */
package com.ricex.aft.servlet.entity;

import java.io.Serializable;

/** Wrapper class for the contents of a file
 * 
 * @author Mitchell Caisse
 *
 */
public class FileContent implements Serializable {

	/** The id of the file */
	private long fileId;
	
	/** The contents of the file */
	private byte[] fileContents;
	
	/** Creates a new File Content
	 * 
	 */
	public FileContent() {
		
	}

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
	
}
