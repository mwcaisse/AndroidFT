/**
 * 
 */
package com.ricex.aft.client.view.request;

import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Request;

/**
 *  The view for displaying a request.
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestView extends Tab {

	/** Enum representing the Mode of this RequestView
	 * 
	 * @author Mitchell Caisse
	 *
	 */
	
	public enum Mode { CREATE, EDIT, VIEW };
	
	/** The request that this request view will be displaying */
	private final Request request;
	
	/** The mode of this request view */
	private final Mode mode;
	
	/** Creates a new RequestView to create a Request
	 * 
	 */
	
	public RequestView() {
		this(new Request(), Mode.CREATE);
	}
	
	/** Creates a new request view to display the specified request
	 * 
	 * @param request The request to display
	 */
	
	public RequestView(Request request) {
		this(request, Mode.VIEW);
	}
	
	/** Creates a new RequestView with the specified request and the mode
	 * 
	 * @param request The request to Edit/View
	 * @param mode The Mode, Create, Edit, View, of this view
	 */
	
	public RequestView(Request request, Mode mode) {
		this.request = request;
		this.mode = mode;
	}
	
}
