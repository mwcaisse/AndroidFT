/**
 * 
 */
package com.ricex.aft.servlet.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ricex.aft.servlet.entity.ValidationException;

/** Base Controller for common functionality between all controllers
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class BaseController {

	/** Creates a new Base Controller
	 * 
	 */
	public BaseController() {
		
	}
	
	/** Handles validation exceptions and returns the proper message
	 * 
	 * @param e The validation exception that occurred
	 * @param resp The http response
	 * @return The message to return
	 */
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public @ResponseBody String handleValidationException(ValidationException e, HttpServletResponse resp) {
		return e.getMessage();
	}	
	
}
