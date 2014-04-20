/**
 * 
 */
package com.ricex.aft.client.controller;

import com.ricex.aft.client.request.IRequest;

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
	 * @return The request that was sent to the server
	 */
	
	public IRequest<T> get(I id, RequestListener<T> listener);
	
	/** Fetches all of the elements from the web service
	 * @param listener The request listener to notify of the results
	 * @return The request that was sent to the server
	 */
	
	public IRequest<T> getAll(RequestListener<T> listener);
	
	/** Creates the specified element on the web service
	 * 
	 * @param toCreate The element to create
	 * @param listener The request listener to notify of the results
	 * @return The request that was sent to the server
	 */
	
	public IRequest<T> create(T toCreate, RequestListener<T> listener);
	
	/** Updates the specified element on the web service
	 * 
	 * @param toUpdate The element to update
	 * @return The request that was sent to the server
	 */
	
	public IRequest<T> update(T toUpdate, RequestListener<T> listener);
	
}
