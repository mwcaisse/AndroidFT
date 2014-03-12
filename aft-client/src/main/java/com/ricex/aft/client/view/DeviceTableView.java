/**
 * 
 */
package com.ricex.aft.client.view;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ricex.aft.client.view.tab.Tab;

/**
 *  The table view for displaying devices
 *  
 * @author Mitchell Caisse
 *
 */
public class DeviceTableView extends Tab {

	
	/** The JTable for displaying the devices */
	private JTable deviceTable;
	
	/** The table model for the device table */
	private DeviceTableModel deviceTableModel;
	
	/** The ScrollPane for the device table */
	private JScrollPane tableScrollPane;
	
	/** Creates a new Device table view */
	
	public DeviceTableView() {
		deviceTableModel = new DeviceTableModel();
		deviceTable = new JTable(deviceTableModel);
		
		tableScrollPane = new JScrollPane(deviceTable);
		
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
