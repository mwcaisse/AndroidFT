/**
 * 
 */
package com.ricex.aft.client.view.device;

import com.ricex.aft.client.util.ListTableModel;
import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class DeviceTableModel extends ListTableModel<Device> {	
	
	/** The column types of the table entries */
	private static final Class<?>[] columnTypes = {String.class, Long.class, Boolean.class};
	
	/** The name of the columns in the tables */
	private static final String[] columnNames = {"Name", "UUID", "Active"};	
	
	/**
	 *  Creates a new instance of Device Table Model to control the table
	 */
	
	public DeviceTableModel() {
		super(columnTypes, columnNames);
	}	
	
	/**
	 * {@inheritDoc}
	 */
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Device device = elements.get(rowIndex);
		switch (columnIndex) {
		case 0: return device.getDeviceName();
		case 1: return device.getDeviceUid();
		case 2: return true;
		default: return null;
		
		}
	}		
	
}
