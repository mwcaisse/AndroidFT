/**
 * 
 */
package com.ricex.aft.servlet.controller.api;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ricex.aft.servlet.controller.BaseController;
import com.ricex.aft.servlet.entity.exception.AuthorizationException;
import com.ricex.aft.servlet.entity.exception.EntityException;
import com.ricex.aft.servlet.manager.DeviceImageManager;

/** Base Controller for common functionality between all controllers
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class ApiController extends BaseController {

	/** The logger instance */
	private static Logger log = LoggerFactory.getLogger(DeviceImageManager.class);
	
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
	@ExceptionHandler(EntityException.class)
	public @ResponseBody String handleValidationException(EntityException e, HttpServletResponse resp) {
		log.error("EntityException", e);
		return e.getMessage();
	}	
	
	/** Handles Authorization Exceptions and returns the proper message
	 * 
	 * @param e The Authorization exception that occurred
	 * @param resp The http response
	 * @return The message to return
	 */
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthorizationException.class)
	public @ResponseBody String handleAuthorizationException(AuthorizationException e, HttpServletResponse resp) {
		log.error("AuthorizationException", e);
		return e.getMessage();
	}
	
}
