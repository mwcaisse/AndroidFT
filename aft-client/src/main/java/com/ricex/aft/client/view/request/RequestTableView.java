/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

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
 *  View for displaying list of all requests
 * 
 * @author Mitchell Caisse
 *
 */

@SuppressWarnings("serial")
public class RequestTableView extends Tab implements CacheListener, RequestListener<List<Request>> {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestTableView.class);
	
	/** The JTable for displaying the devices */
	private JTable requestTable;
	
	/** The table model for the request table */
	private RequestTableModel requestTableModel;
	
	/** The scroll pane for the request table */
	private JScrollPane tableScrollPane;
	
	
	/** Creates a new Request Table View 
	 * 
	 */
	
	public RequestTableView() {
		requestTableModel = new RequestTableModel();
		requestTable = new JTable(requestTableModel);
		
		tableScrollPane = new JScrollPane(requestTable);
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		add(tableScrollPane, BorderLayout.CENTER);
		
		//add ourselves as a cache listener to the REquestCache
		RequestCache.getInstance().addCacheListener(this);

		//request all of the requests from the server
		RequestController.getInstance().getAll(this);		
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

	/** Called when the RequestAllRequests Request is successful,
	 *  The cache update will take care of updating the TableModel though.
	 * 
	 */
	
	public void onSucess(IRequest<List<Request>> request) {
		
	}
	
	/** Called when the RequestAllRequest Request was canceled 
	 * 
	 */
	
	public void cancelled(IRequest<List<Request>> request) {
		log.info("Request to update requests was cancelled");
	}
	
	/** Called when the RequestAllRequests Request fails
	 * 
	 */

	public void onFailure(IRequest<List<Request>> request, Exception e) {
		log.error("Request to update requests failed", e);
	}

	/** Called when the RequestCache has been updated. Updates the TableModel to reflect these changes
	 * 
	 */

	public void update(CacheUpdateEvent e) {
		requestTableModel.setRequests(RequestCache.getInstance().getAll());
	}	
}
