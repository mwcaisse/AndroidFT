/**
 * 
 */
package com.ricex.aft.common.response;

/**
 * @author Mitchell Caisse
 *
 */
public class BooleanResponse {

	/** The value of this response */
	private boolean value;
	
	/** Creates a new Boolean response with the specified value 
	 * 
	 * @param value The value of the response
	 */
	public BooleanResponse(boolean value) {
		this.value = value;
	}
	
	/** Creates a new boolean response, setting the value to false
	 * 
	 */
	
	public BooleanResponse() {
		this(false);
	}
	
	/** Gets the value
	 * 
	 * @return The value
	 */
	
	public boolean getValue() {
		return value;
	}
	
	/** Sets the value
	 * 
	 * @param value The new value
	 */
	
	public void setValue(boolean value) {
		this.value = value;
	}
	
}
