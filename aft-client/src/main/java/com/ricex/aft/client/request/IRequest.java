/**
 * 
 */
package com.ricex.aft.client.request;

/**
 * @author Mitchell Caisse
 *
 */
public interface IRequest {

	/** Gets the ID of this request
	 * 
	 * @return The ID of this request
	 */
	
	public long getId();
	
	public void onSucess();
	
	public void onFailure();
	
	public void onCancelled();
}
