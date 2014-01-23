/**
 * 
 */
package com.ricex.aft.servlet.entity;


/**
 *  The status of a request
 * 
 * @author Mitchell Caisse
 *
 */
public enum RequestStatus {

	/** Request was just created */
	NEW, 
	/** Request has failed */
	FAILED, 
	/** Request has been completed */
	COMPLETED;
	
}
