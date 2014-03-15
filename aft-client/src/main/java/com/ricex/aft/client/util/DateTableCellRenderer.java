/**
 * 
 */
package com.ricex.aft.client.util;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Mitchell Caisse
 *
 */
public class DateTableCellRenderer extends DefaultTableCellRenderer {

	/** The date formatter used to format dates */
	private final SimpleDateFormat dateFormatter;
	
	/** Creates a new DateTableCellRenderer with the given date format string
	 * 
	 * @param dateFormat The string to use to format the date, same as the String passed to DateFormat
	 */
	
	public DateTableCellRenderer(String dateFormat) {
		dateFormatter = new SimpleDateFormat(dateFormat);
	}
	
	/** If the value contains a Date, formats it acording to the format string
	 * 
	 */
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
		if (value instanceof Date) {
			value = dateFormatter.format(value);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);		
	}
	
}
