/**
 * 
 */
package com.ricex.aft.servlet.entity;

import com.ricex.aft.common.entity.AbstractEntity;

/** Wrapper class for the contents of a file
 * 
 * @author Mitchell Caisse
 *
 */
public class FileContent extends AbstractEntity {


	/** The contents of the file */
	private byte[] fileContents;
	
	/** Creates a new File Content
	 * 
	 */
	public FileContent() {
		
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
