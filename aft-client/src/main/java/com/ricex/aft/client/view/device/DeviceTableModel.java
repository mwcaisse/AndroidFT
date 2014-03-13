/**
 * 
 */
package com.ricex.aft.client.view.device;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public class DeviceTableModel implements TableModel {

	/** The table model listeners */
	private List<TableModelListener> tableModelListeners;
	
	/** The list of devices that this table will display */
	private List<Device> devices;
	
	/** The column types of the table entries */
	private final Class<?>[] columnTypes = {String.class, Long.class, Boolean.class};
	
	/** The name of the columns in the tables */
	private final String[] columnNames = {"Name", "UUID", "Active"};
	
	
	/**
	 *  Creates a new instance of Device Table Model to control the table
	 */
	
	public DeviceTableModel() {
		tableModelListeners = new ArrayList<TableModelListener>();
		devices = new ArrayList<Device>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public int getColumnCount() {
		return columnTypes.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public int getRowCount() {
		return devices.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Device device = devices.get(rowIndex);
		switch (columnIndex) {
		case 0: return device.getDeviceName();
		case 1: return device.getDeviceUid();
		case 2: return true;
		default: return null;
		
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		
	}
	
	/** Sets the data in the table model
	 * 
	 * @param devices The new list of devices
	 */
	
	public void setDevices(List<Device> devices) {
		this.devices = devices;
		fireTableChangeEvent(new TableModelEvent(this));
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public void addTableModelListener(TableModelListener listener) {
		tableModelListeners.add(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public void removeTableModelListener(TableModelListener listener) {
		tableModelListeners.remove(listener);
	}
	
	/** Notifies all of the table model listeners of the given table model event
	 * 
	 * @param e The TableModelEvent to notify the listeners of
	 */
	
	protected void fireTableChangeEvent(TableModelEvent e) {
		for (TableModelListener listener: tableModelListeners) {
			listener.tableChanged(e);
		}
	}
	
	
}
