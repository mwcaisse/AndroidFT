/**
 * 
 */
package com.ricex.aft.client.controller;

/** The controller interface to interact with the database
 * 
 * @author Mitchell Caisse
 *
 */
public interface Controller<T, I> {
	
	public void get();
	
	public void getAll();
	
	public void create();
	
	public void update();
}
