package com.ricex.aft.android.requester;


/** The callback to use when making an asynchronous request
 * 
 * @author Mitchell Caisse
 *
 * @param <T> The type of the response excepted from the request
 */
public interface RequesterCallback<T> {
	
	/** Called when the request completed successfully
	 * 
	 * @param results The results of the request
	 */
	
	public void onSuccess(T results);
	
	/** Called when the request failed, received a response other than OK (HTTP 200) from the server, or
	 * 		an internal error occurred.
	 * 
	 * @param e The exception indicating why it failed
	 */
	
	public void onFailure( Exception e);
}
