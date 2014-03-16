/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.controller.RequestController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.util.DateTableCellRenderer;
import com.ricex.aft.client.view.tab.TabController;
import com.ricex.aft.common.entity.Request;

/**
 *  View for displaying list of all requests
 * 
 * @author Mitchell Caisse
 *
 */

@SuppressWarnings("serial")
public class RequestTable extends JPanel {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(RequestTable.class);
	
	/** The JTable for displaying the devices */
	private JTable requestTable;
	
	/** The table model for the request table */
	private RequestTableModel requestTableModel;
	
	/** The scroll pane for the request table */
	private JScrollPane tableScrollPane;	
	
	/** Creates a new Request Table View 
	 * 
	 */
	
	public RequestTable() {
		requestTableModel = new RequestTableModel();
		requestTable = new JTable(requestTableModel);
		
		requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		requestTable.getColumnModel().getColumn(4).setCellRenderer(new DateTableCellRenderer("MM-dd-yyyy HH:mm:ss"));
		requestTable.addMouseListener(new TableMouseAdapter());
		
		tableScrollPane = new JScrollPane(requestTable);
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		add(tableScrollPane, BorderLayout.CENTER);
		
	}
	
	/** Sets the data displayed in the table to the specified list
	 * 
	 * @param data The new data to display in the table
	 */
	
	public void setTableData(List<Request> data) {
		requestTableModel.setData(data);		
	}
	
	/** Responds to mouse events on the table
	 * 
	 * @author Mitchell Caisse
	 *
	 */
	
	private class TableMouseAdapter extends MouseAdapter {
		
		/** User clicked in the table, check if it is a double click, if so open the selected request
		 * 
		 */
		
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				//double left click, find the selected request, then create a tab for it
				int selectedRow = requestTable.getSelectedRow();
				Request selectedRequest = requestTableModel.getElementAt(selectedRow);
				TabController.INSTANCE.addRequestTab(selectedRequest);				
			}
		}
	}
}
