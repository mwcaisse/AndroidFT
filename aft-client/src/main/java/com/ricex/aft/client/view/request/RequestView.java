/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.view.request.action.BrowseAction;
import com.ricex.aft.client.view.request.action.CreateAction;
import com.ricex.aft.client.view.request.action.DownloadAction;
import com.ricex.aft.client.view.request.action.SaveAction;
import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;

/**
 *  The view for displaying a request.
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestView extends Tab {	

	private static final long serialVersionUID = -872056187324454493L;

	/** The logger */
	private static Logger log = LoggerFactory.getLogger(RequestView.class);

	/** Enum representing the Mode of this RequestView
	 * 
	 * @author Mitchell Caisse
	 *
	 */	
	public enum Mode { CREATE, EDIT, VIEW };
	
	/** Vertical padding constant for the SpringLayout */
	private static final int PADDING_VERTICAL = 5;
	
	/** Vertical padding constant for related, or close, elements in the SpringLayout */
	private static final int PADDING_VERTICAL_CLOSE = 10;
	
	/** Horizontal padding constant for the Spring Layout */
	private static final int PADDING_HORIZONTAL = 10;
	
	/** Horizontal padding constant for related, or close, elements in the SpringLayout */
	private static final int PADDING_HORIZONTAL_CLOSE = 5;
	
	/** The request that this request view will be displaying */
	private final Request request;
	
	/** The mode of this request view */
	private final Mode mode;
	
	/** The combobox for the device this request belongs to */
	private JComboBox<Device> cbxDevice;
	
	/** The combobox for the current status of this request */
	private JComboBox<RequestStatus> cbxStatus;
	
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
	
	/** The label for displaying the status message of operations */
	private JLabel lblStatusMessage;
	
	/** The ComboBox model for the Status combobox */
	private DefaultComboBoxModel<RequestStatus> cbxStatusModel;
	
	/** The ComboBox model for the device combobox */
	private DeviceComboBoxModel cbxDeviceModel;
	
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
		
		initializeRequest();
		initializeLabels();
		initializeInputComponents();
		initializeButtons();
		initializeLayout();
		
		addComponents();
		
		populateFields();
		disableFields();
	
	}
	
	/** Initializes the request that will be displayed in this view
	 *
	 */
	
	protected void initializeRequest() {
		if (mode == Mode.CREATE) {
			request.setRequestUpdated(new Date());
			request.setRequestStatus(RequestStatus.NEW);
		}
	}
	
	/** Initializes the components used for inputting data into the request view
	 * 
	 */
	
	protected void initializeInputComponents() {
		txtLastUpdated = new JTextField();
		txtLastUpdated.setEnabled(false);
		
		txtFileName = new JTextField();
		txtFileLocation = new JTextField();
		
		cbxDeviceModel = new DeviceComboBoxModel();
		cbxDevice = new JComboBox<Device>(cbxDeviceModel);
		
		cbxStatusModel = new DefaultComboBoxModel<RequestStatus>(RequestStatus.values());
		cbxStatus = new JComboBox<RequestStatus>(cbxStatusModel);
		
		
		
	}
	
	/** Initializes the buttons 
	 * 
	 */
	
	protected void initializeButtons() {
		
		butCancel = new JButton("Discard");
		
		switch (mode) {
		case CREATE:			
			butBrowseFile = new JButton("Browse");
			butBrowseFile.setAction(new BrowseAction(this));
			butBrowseFile.setText("Browse");
			
			butSave = new JButton("Create");		
			butSave.setAction(new CreateAction(this));
			butSave.setText("Create");
			break;
		case EDIT:			
			butBrowseFile = new JButton("Download");
			butBrowseFile.setAction(new DownloadAction(this));
			butBrowseFile.setText("Download");
			
			butSave = new JButton("Save");	
			butSave.setAction(new SaveAction(this));
			butSave.setText("Save");
			break;
		case VIEW:
			butBrowseFile = new JButton("Download");
			butBrowseFile.setAction(new DownloadAction(this));
			butBrowseFile.setText("Download");
			
			butSave = new JButton("Save");	
			butSave.setAction(new SaveAction(this));
			butSave.setText("Save");
			break;		
		}
		
		

	}
	
	/** Initializes the labels used to label the input components
	 * 
	 */
	
	protected void initializeLabels() {
		lblDevice = new JLabel("Device: ");
		lblStatus = new JLabel("Status: ");
		lblLastUpdated = new JLabel("Last Updated: ");
		lblFileName = new JLabel("File: ");
		lblFileLocation = new JLabel("File Location: ");
		lblStatusMessage = new JLabel("");
	}
	
	/** Populates the fields based upon the current mode
	 * 
	 */
	
	protected void populateFields() {
		if (mode == Mode.CREATE) {
			txtLastUpdated.setText(request.getRequestUpdated().toString());
		}
		else if (mode == Mode.VIEW || mode == Mode.EDIT) {
			txtFileName.setText(request.getRequestFile().getFileName());
			txtFileLocation.setText(request.getRequestFileLocation());
			cbxStatus.setSelectedItem(request.getRequestStatus());
			txtLastUpdated.setText(request.getRequestUpdated().toString());	
		}
	}
	
	/** Updates the fields in the Request View
	 * 
	 */
	
	public void updateFields() {
		txtFileName.setText(request.getRequestFile().getFileName());
		txtFileLocation.setText(request.getRequestFileLocation());
		cbxStatus.setSelectedItem(request.getRequestStatus());
		txtLastUpdated.setText(request.getRequestUpdated().toString());	
	}
	
	/** Disables fields depending on the mode
	 * 
	 */
	
	protected void disableFields() {
		if (mode == Mode.VIEW) {
			txtFileName.setEnabled(false);
			txtFileLocation.setEnabled(false);
			cbxStatus.setEnabled(false);
			cbxDevice.setEnabled(false);
			txtLastUpdated.setEnabled(false);
			butSave.setEnabled(false);
			butCancel.setEnabled(false);
		}
		else if (mode == Mode.CREATE) {
			txtLastUpdated.setEnabled(false);
			cbxStatus.setEnabled(false);
		}
	}
	
	/** Initializes the layout to be used
	 * 
	 */
	
	protected void initializeLayout() {
		
		SpringLayout layout = new SpringLayout();
		JPanel panel = this;
		
		layout.putConstraint(SpringLayout.WEST, lblFileName, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblFileLocation, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblDevice, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblStatus, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblLastUpdated, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, txtFileName, PADDING_VERTICAL, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.NORTH, txtFileLocation, PADDING_VERTICAL, SpringLayout.SOUTH, txtFileName);
		layout.putConstraint(SpringLayout.NORTH, cbxDevice, PADDING_VERTICAL, SpringLayout.SOUTH, txtFileLocation);
		layout.putConstraint(SpringLayout.NORTH, cbxStatus, PADDING_VERTICAL, SpringLayout.SOUTH, cbxDevice);
		layout.putConstraint(SpringLayout.NORTH, txtLastUpdated, PADDING_VERTICAL, SpringLayout.SOUTH, cbxStatus);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblFileName, 0, SpringLayout.VERTICAL_CENTER, txtFileName);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblFileLocation, 0, SpringLayout.VERTICAL_CENTER, txtFileLocation);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblDevice, 0, SpringLayout.VERTICAL_CENTER, cbxDevice);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblStatus, 0, SpringLayout.VERTICAL_CENTER, cbxStatus);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblLastUpdated, 0, SpringLayout.VERTICAL_CENTER, txtLastUpdated);
		
		layout.putConstraint(SpringLayout.WEST, txtFileLocation, PADDING_HORIZONTAL_CLOSE, SpringLayout.EAST, lblFileLocation);
		
		layout.putConstraint(SpringLayout.WEST, txtFileName, 0, SpringLayout.WEST, txtFileLocation);
		layout.putConstraint(SpringLayout.WEST, cbxDevice, 0, SpringLayout.WEST, txtFileLocation);
		layout.putConstraint(SpringLayout.WEST, cbxStatus, 0, SpringLayout.WEST, txtFileLocation);
		layout.putConstraint(SpringLayout.WEST, txtLastUpdated, 0, SpringLayout.WEST, txtFileLocation);
		
		layout.putConstraint(SpringLayout.EAST, txtFileLocation, - PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, cbxDevice, - PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, cbxStatus, - PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, txtLastUpdated, - PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		
		
		layout.putConstraint(SpringLayout.EAST, butBrowseFile, -PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, butBrowseFile, 0, SpringLayout.VERTICAL_CENTER, txtFileName);
		layout.putConstraint(SpringLayout.EAST, txtFileName, - PADDING_HORIZONTAL, SpringLayout.WEST, butBrowseFile);
		
		
		layout.putConstraint(SpringLayout.EAST, butCancel, -PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, butCancel, -PADDING_VERTICAL, SpringLayout.SOUTH, panel);
		
		layout.putConstraint(SpringLayout.EAST, butSave, -PADDING_HORIZONTAL, SpringLayout.WEST, butCancel);
		layout.putConstraint(SpringLayout.SOUTH, butSave, -PADDING_VERTICAL, SpringLayout.SOUTH, panel);
		
		layout.putConstraint(SpringLayout.WEST, lblStatusMessage, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblStatusMessage, 0, SpringLayout.VERTICAL_CENTER, butSave);
		
		setLayout(layout);	
		
		
	}
	
	/** Adds the components to the view
	 * 
	 */
	
	protected void addComponents() {
		//add the labels
		add(lblFileName);
		add(lblFileLocation);
		add(lblDevice);
		add(lblStatus);
		add(lblLastUpdated);
		add(lblStatusMessage);
		
		//add the input components
		add(txtFileName);
		add(txtFileLocation);
		add(cbxDevice);
		add(cbxStatus);
		add(txtLastUpdated);
		
		//add the buttons
		add(butBrowseFile);
		add(butCancel);
		add(butSave);
	}	
	
	/** Populates the request from the fields in the RequestView
	 * 
	 */
	
	public void populateRequest() {
		request.setRequestDevice(cbxDeviceModel.getSelectedItem());
		request.setRequestStatus((RequestStatus)cbxStatusModel.getSelectedItem());
		request.setRequestFileLocation(txtFileLocation.getText());
	}
	
	/** Sets the status message displayed
	 * 
	 * @param msg The new status message
	 */
	
	public void setStatusMessage(String msg) {
		lblStatusMessage.setText(msg);
	}
	
	/** Clears the status message displayed
	 * 
	 */
	
	public void clearStatusMessage() {
		setStatusMessage("");
	}
	
	/** Retreives the request that this request view is displaying
	 * 
	 * @return The request
	 */
	
	public Request getRequest() {
		return request;
	}
}
