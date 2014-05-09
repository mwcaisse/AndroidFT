/**
 * 
 */
package com.ricex.aft.client.view.request.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.controller.RequestController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.view.request.RequestView;

/** Create action called when the  Create button is pressed in the RequestView
 * 
 * TODO: Implement a callback class, that the action reports its results to
 * 
 * @author Mitchell Caisse
 *
 */
public class CreateAction extends AbstractAction implements RequestListener<Long> {
	
	/** The logger */
	private static Logger log = LoggerFactory.getLogger(CreateAction.class);
	
	/** The request view this create action is in */
	private final RequestView requestView;
	
	/** Creates a save action to save the request in the specified RequestView
	 * @param requestView The requestView
	 */
	
	public CreateAction(RequestView requestView) {
		this.requestView = requestView;
	}

	/** Saves the request to the database controller
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		requestView.populateRequest();
		RequestController.getInstance().createRequest(requestView.getRequest(), this);		
	}

	/** Called when the create request is completed
	 * 
	 */
	
	public void onSucess(IRequest<Long> request) {		
		log.info("Creating request sucessful");
	}

	/** Called when the create request was cancelled
	 * 
	 */
	public void cancelled(IRequest<Long> request) {
		log.warn("Creating request was cancelled");
	}

	/** Called when the create request failed
	 * 
	 */
	
	public void onFailure(IRequest<Long> request, Exception e) {
		log.error("Error creating request", e);
	}

}
