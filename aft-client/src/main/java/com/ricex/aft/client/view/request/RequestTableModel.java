/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.util.Date;

import com.ricex.aft.client.util.ListTableModel;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;

/**
 * @author Mitchell Caisse
 *
 */
public class RequestTableModel  extends ListTableModel<Request> {
	
	/** The column types of the table entries */
	private static final Class<?>[] columnTypes = {String.class, String.class, String.class, RequestStatus.class, Date.class};
	
	/** The name of the columns in the tables */
	private static final String[] columnNames = {"File Name", "Location", "Device Name", "Status", "Last Updated"};
	
	/**
	 *  Creates a new instance of Request Table Model to control the table
	 */
	
	public RequestTableModel() {
		super(columnTypes, columnNames);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Request request = elements.get(rowIndex);
		switch (columnIndex) {
		case 0: return request.getRequestFile().getFileName();
		case 1: return request.getRequestFileLocation();
		case 2: return request.getRequestDevice().getDeviceName();
		case 3: return request.getRequestStatus();
		case 4: return request.getRequestUpdated();
		default: return null;		
		}
	}	

}
