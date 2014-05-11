/**
 * 
 */
package com.ricex.aft.common.response;

/**
 * @author Mitchell Caisse
 *
 */

public class LongResponse {
	
	/** The value for the response */
	private Long value;
	
	public LongResponse() {
		
	}
	
	/** Creates a new Long response with the specified long
	 * 
	 * @param value The response value
	 */
	
	public LongResponse(Long value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	
	public Long getValue() {
		return value;
	}

	/**
	 * @param data the data to set
	 */
	
	public void setValue(Long value) {
		this.value = value;
	}
}
