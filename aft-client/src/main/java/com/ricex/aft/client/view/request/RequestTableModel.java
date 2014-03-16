/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;

/**
 * @author Mitchell Caisse
 *
 */
public class RequestTableModel implements TableModel {
	/** The table model listeners */
	private List<TableModelListener> tableModelListeners;
	
	/** The list of devices that this table will display */
	private List<Request> requests;
	
	/** The column types of the table entries */
	private final Class<?>[] columnTypes = {String.class, String.class, String.class, RequestStatus.class, Date.class};
	
	/** The name of the columns in the tables */
	private final String[] columnNames = {"File Name", "Location", "Device Name", "Status", "Last Updated"};
	
	/**
	 *  Creates a new instance of Device Table Model to control the table
	 */
	
	public RequestTableModel() {
		tableModelListeners = new ArrayList<TableModelListener>();
		requests = new ArrayList<Request>();
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
		return requests.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	//private final String[] columnNames = {"File Name", "Location", "Device Name", "Status", "Last Updated"};
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Request request = requests.get(rowIndex);
		switch (columnIndex) {
		case 0: return request.getRequestFile().getFileName();
		case 1: return request.getRequestFileLocation();
		case 2: return request.getRequestDevice().getDeviceName();
		case 3: return request.getRequestStatus();
		case 4: return request.getRequestUpdated();
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
	
	/** Returns the request item at the given now
	 * 
	 * @param row The row to fetch the item for
	 * @return The Request object at that row
	 */
	
	public Request getItemAt(int row) {
		return requests.get(row);
	}
	
	/** Sets the data in the table model
	 * 
	 * @param devices The new list of devices
	 */
	
	public void setRequests(List<Request> requests) {
		this.requests = new ArrayList<Request>(requests);
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
