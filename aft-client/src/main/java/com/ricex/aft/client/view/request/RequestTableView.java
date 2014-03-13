/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ricex.aft.client.view.tab.Tab;

/**
 *  View for displaying list of all requests
 * 
 * @author Mitchell Caisse
 *
 */

public class RequestTableView extends Tab {

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
