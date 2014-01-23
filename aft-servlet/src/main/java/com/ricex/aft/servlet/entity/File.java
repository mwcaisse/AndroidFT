/**
 * 
 */
package com.ricex.aft.servlet.entity;

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
	
	/** The location of the file on disk,
	 * TODO: Not sure if this will be a path, or a file itself.
	 */
	private String fileLocation;
	
}
