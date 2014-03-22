/**
 * 
 */
package com.ricex.aft.client.view.request;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.view.device.DeviceView;
import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;

/**
 *  The view for displaying a request.
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestView extends Tab {
	
	/** The logger */
	private static Logger log = LoggerFactory.getLogger(RequestView.class);

	/** Enum representing the Mode of this RequestView
	 * 
	 * @author Mitchell Caisse
	 *
	 */
	
	public enum Mode { CREATE, EDIT, VIEW };
	
	/** The request that this request view will be displaying */
	private final Request request;
	
	/** The mode of this request view */
	private final Mode mode;
	
	/** The combobox for the device this request belongs to */
	private JComboBox cbxDevice;
	
	/** The combobox for the current status of this request */
	private JComboBox cbxStatus;
	
	/** The textfield containing the last updated date */
	private JTextField txtLastUpdated;
	
	/** The TextField containing the name of the file, or upload file box if it is being created/modified */
	private JTextField txtFileName;
	
	/** The TextField containing the location to copy the file to */
	private JTextField txtFileLocation;
	
	/** Button to browse for a file to attach to a request */
	private JButton butBrowseFile;
	
	/** Button to save any changes made to the request, or to create a new request */
	private JButton butSave;
	
	/** Button to cancel any changes made to the request */
	private JButton butCancel;
	
	/** The label for the device field */
	private JLabel lblDevice;
	
	/** The label for the status field */
	private JLabel lblStatus;
	
	/** The label for the last updated field */
	private JLabel lblLastUpdated;
	
	/** The label for the file name field */
	private JLabel lblFileName;

	/** The label for the file location field */
	private JLabel lblFileLocation;
	
	/** The ComboBox model for the Status combobox */
	private DefaultComboBoxModel cbxStatusModel;
	

	
	/** Creates a new RequestView to create a Request
	 * 
	 */
	
	public RequestView() {
		this(new Request(), Mode.CREATE);
	}
	
	/** Creates a new request view to display the specified request
	 * 
	 * @param request The request to display
	 */
	
	public RequestView(Request request) {
		this(request, Mode.VIEW);
	}
	
	/** Creates a new RequestView with the specified request and the mode
	 * 
	 * @param request The request to Edit/View
	 * @param mode The Mode, Create, Edit, View, of this view
	 */
	
	public RequestView(Request request, Mode mode) {
		this.request = request;
		this.mode = mode;
		
		initializeLabels();
		initializeInputComponents();
	}
	
	/** Initializes the components used for inputting data into the request view
	 * 
	 */
	
	private void initializeInputComponents() {
		txtLastUpdated = new JTextField();
		txtFileName = new JTextField();
		txtFileLocation = new JTextField();
		
		cbxDevice = new JComboBox();
		
		cbxStatusModel = new DefaultComboBoxModel(RequestStatus.values());
		cbxStatus = new JComboBox(cbxStatusModel);
		
		
		
	}
	
	/** Initializes the labels used to label the input components
	 * 
	 */
	
	private void initializeLabels() {
		lblDevice = new JLabel("Device: ");
		lblStatus = new JLabel("Status: ");
		lblLastUpdated = new JLabel("Last Updated: ");
		lblFileName = new JLabel("File: ");
		lblFileLocation = new JLabel("File Location: ");
	}
	
}
