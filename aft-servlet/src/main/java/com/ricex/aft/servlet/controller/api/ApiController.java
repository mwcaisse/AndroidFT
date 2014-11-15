/**
 * 
 */
package com.ricex.aft.servlet.controller.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ricex.aft.servlet.controller.BaseController;
import com.ricex.aft.servlet.entity.ValidationException;

/** Base Controller for common functionality between all controllers
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class ApiController extends BaseController {

	/** Creates a new Base Controller
	 * 
	 */
	public ApiController() {
		
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