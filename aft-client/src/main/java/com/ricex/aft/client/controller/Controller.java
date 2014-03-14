/**
 * 
 */
package com.ricex.aft.client.controller;

/** The controller interface to interact with the database, using an Async connection
 * 
 * @author Mitchell Caisse
 *
 */
public interface Controller<T, I> {
	
	/** Fetches the element with the given id from the web service
	 * 
	 * @param id The id of the element to fetch
	 * @param listener The request listener to notify of the results
	 */
	
	public void get(I id, RequestListener listener);
	
	/** Fetches all of the elements from the web service
	 * 	@param listener The request listener to notify of the results
	 */
	
	public void getAll(RequestListener listener);
	
	/** Creates the specified element on the web service
	 * 
	 * @param toCreate The element to create
	 * @param listener The request listener to notify of the results
	 */
	
	public void create(T toCreate, RequestListener listener);
	
	/** Updates the specified element on the web service
	 * 
	 * @param toUpdate The element to update
	 */
	
	public void update(T toUpdate, RequestListener listener);
	
}
