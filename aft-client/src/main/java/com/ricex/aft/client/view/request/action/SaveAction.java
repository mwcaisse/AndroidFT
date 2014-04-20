/**
 * 
 */
package com.ricex.aft.client.view.request.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.ricex.aft.client.controller.RequestController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.common.entity.Request;

/** Save action called when the  Save button is pressed in the RequestView
 * 
 * TODO: Implement a callback class, that the action reports its results to
 * 
 * @author Mitchell Caisse
 *
 */
public class SaveAction extends AbstractAction implements RequestListener<Long> {
	
	/** The request to save */
	private Request request;
	
	/** Creates a new Save Action to save the specified request
	 * 
	 * @param request The request to save
	 */
	
	public SaveAction(Request request) {
		this.request = request;
	}

	/** Saves the request to the database controller
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		RequestController.getInstance().createRequest(request, this);
		
	}

	/** Called when the create request is completed
	 * 
	 */
	
	public void onSucess(IRequest<Long> request) {		
		
	}

	/** Called when the create request was cancelled
	 * 
	 */
	public void cancelled(IRequest<Long> request) {
		
	}

	/** Called when the create request failed
	 * 
	 */
	
	public void onFailure(IRequest<Long> request, Exception e) {
		
	}
	
	

}
