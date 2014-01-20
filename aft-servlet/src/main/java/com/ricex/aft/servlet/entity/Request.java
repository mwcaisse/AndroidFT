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
	
	
}
