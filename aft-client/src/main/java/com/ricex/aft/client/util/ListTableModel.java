/**
 * 
 */
package com.ricex.aft.client.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * @author Mitchell Caisse
 *
 */
public abstract class ListTableModel<T> implements TableModel {

	/** The table model listeners */
	private List<TableModelListener> tableModelListeners;
	
	/** The list of elements that should be displayed in the table */
	protected List<T> elements;
	
	/** The column types of the table entries */
	private final Class<?>[] columnTypes;
	
	/** The name of the columns in the tables */
	private final String[] columnNames;
	
	/** Creates a new ListTableModel, and initializes the table model listeners and the elements list
	 * 
	 */
	
	public ListTableModel(Class<?>[] columnTypes, String[] columnNames) {
		this.columnTypes = columnTypes;
		this.columnNames = columnNames;
		if (columnNames.length != columnTypes.length) {
			throw new IllegalArgumentException("ColumnNames + ColumnTypes must be the same length.");
		}
		tableModelListeners = new ArrayList<TableModelListener>();
		elements = new ArrayList<T>();
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
		return elements.size();
	}
	
	/** Default implementation, returns false for all cells
	 * 
	 */
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	/** Default implementation, Does nothing as no cells are editable
	 * 
	 */
	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		
	}	
	
	/** Sets the data in the table
	 * 
	 * @param data The new list of elements to display in the table
	 */
	
	public void setData(List<T> data) {
		this.elements = new ArrayList<T>(data);
		fireTableChangeEvent(new TableModelEvent(this));
	}
	
	/** Get the element that a row represents
	 * 
	 * @param row The row the item is in
	 * @return The item in that row
	 */
	
	public T getElementAt(int row) {
		return elements.get(row);
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
