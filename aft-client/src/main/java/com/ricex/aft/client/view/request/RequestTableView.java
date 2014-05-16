/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.awt.BorderLayout;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.cache.CacheListener;
import com.ricex.aft.client.cache.CacheUpdateEvent;
import com.ricex.aft.client.cache.RequestCache;
import com.ricex.aft.client.controller.RequestController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Request;

/**
 *  The request table view that will display a list of all requests
 *  
 * @author Mitchell Caisse
 *
 */
public class RequestTableView extends Tab implements CacheListener, RequestListener<List<Request>> {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestTable.class);
	
	/** The request table view that will display all of the requests */
	private RequestTable requestTableView;
	
	/** Creates a new RequestTableView and initializes the request table
	 * 
	 */
	
	public RequestTableView() {
		requestTableView = new RequestTable();
		
		//add ourselves as a cache listener to RequestCache
		RequestCache.getInstance().addCacheListener(this);
		
		//request the list of all requests from the server
		RequestController.getInstance().getAll(this);
		
		//add the table view as the view of this JPanel
		setLayout(new BorderLayout());
		add(requestTableView, BorderLayout.CENTER);
	}


	/** Called when the request to fetch all of the requests was successful,
	 *   Does not update the table directly, lets an update to the RequestCache update the table
	 */
	
	public void onSucess(IRequest<List<Request>> request) {
		//do nothing
	}

	/** Called when the request to fetch all of the request was cancelled
	 * 
	 */
	
	public void cancelled(IRequest<List<Request>> request) {
		log.warn("Request to update request table view was cancelled");
	}

	/** Called when the request to fetch all of the requests was canceled, log the error
	 * TODO: implement better error handling
	 */
	
	public void onFailure(IRequest<List<Request>> request, Exception e) {
		log.error("Request to update request table view failed", e);
	}

	/** An update has occurred in the RequestTable, update the request table to reflect the change made
	 * 
	 */
	
	public void update(CacheUpdateEvent e) {
		requestTableView.setTableData(RequestCache.getInstance().getAll());
	}
	
	/**
	 * {@inheritDoc}
	 * @return false
	 */
		
	@Override
	public boolean onTabClose() {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @return false
	 */
	
	@Override	
	public boolean isClosable() {
		return false;
	}
	
}
