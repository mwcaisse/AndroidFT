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

/** SaveAction pressed when the user presses the Save Button on the RequestView
 * @author Mitchell Caisse
 *
 */
public class SaveAction extends AbstractAction implements RequestListener<Long> {

	/** The logger */
	private static Logger log = LoggerFactory.getLogger(SaveAction.class);
	
	/** The request view this save action is a part of */
	private final RequestView requestView;
	
	/** Creates a new SaveAction with the specified RequestView
	 * 
	 * @param requestView The request view
	 */
	
	public SaveAction(RequestView requestView) {
		this.requestView = requestView;
	}
	
	/** Called when the user clicks the Save button, gets the request and sends to the server
	 * 
	 */
	
	public void actionPerformed(ActionEvent e) {
		requestView.populateRequest();
		RequestController.getInstance().updateRequest(requestView.getRequest(), this);
	}

	public void onSucess(IRequest<Long> request) {
		log.info("Saving request sucessful!");
	}

	public void cancelled(IRequest<Long> request) {
		log.warn("Saveing request was cancelled");
	}

	public void onFailure(IRequest<Long> request, Exception e) {
		log.error("Error saving request", e);
	}
	
}
