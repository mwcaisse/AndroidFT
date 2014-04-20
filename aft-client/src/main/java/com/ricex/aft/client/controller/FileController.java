/**
 * 
 */
package com.ricex.aft.client.controller;

/**
 *  The controller for handling fetching and pushing to/from the web server
 *  
 * @author Mitchell Caisse
 *
 */

public class FileController extends AbstractController  {

	
	/** The singleton instance */
	private static FileController _instance;
	
	/** Returns the singleton instance of this controller
	 * 
	 * @return The singleton instance
	 */
	
	public static FileController getInstance() {
		if (_instance == null) {
			_instance = new FileController();
		}
		return _instance;
	}
	
	
	/** Creates a new File Controller
	 * 
	 */
	
	private FileController() {
		
	}
}
