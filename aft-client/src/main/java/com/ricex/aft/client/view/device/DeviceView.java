/**
 * 
 */
package com.ricex.aft.client.view.device;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.controller.RequestController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.view.request.RequestTable;
import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.Request;

/**
 *  The view to be used for displaying a list of all the current known devices
 *  
 * 
 * @author Mitchell Caisse
 *
 */
public class DeviceView extends Tab implements RequestListener<List<Request>> {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(DeviceView.class);
	
	/** Enum representing the display mode for this tab
	 * 		create - Create a new device
	 * 		view - Read only view of an existing device
	 * 		edit - Modifiable view of an existing device
	 * 
	 * @author Mitchell Caisse
	 *
	 */
	public enum Mode { CREATE, VIEW, EDIT };
	
	
	/** Vertical padding constant for the SpringLayout */
	private static final int PADDING_VERTICAL = 5;
	
	/** Vertical padding constant for related, or close, elements in the SpringLayout */
	private static final int PADDING_VERTICAL_CLOSE = 10;
	
	/** Horizontal padding constant for the Spring Layout */
	private static final int PADDING_HORIZONTAL = 10;
	
	/** Horizontal padding constant for related, or close, elements in the SpringLayout */
	private static final int PADDING_HORIZONTAL_CLOSE = 5;
	
	/** The device that this device view is operating on */
	private Device device;
	
	/** The mode of this device view */
	private Mode mode;
	
	/** Label for displaying the name field indicator */
	private JLabel lblName;
	
	/** Label for displaying the UUID field indicator */
	private JLabel lblUuid;
	
	/** Label for displaying the registration id field indicator */
	private JLabel lblRegistrationId;
	
	/** Label for displaying the active field indicator */
	private JLabel lblActive;
	
	/** Text Field for displaying the name of the device */
	private JTextField txtName;
	
	/** Text Field for displaying the UUID of the device */
	private JTextField txtUuid;
	
	/** TextField for displaying the registration id of the device */
	private JTextField txtRegistrationId;
	
	/** Check box for displaying the active status of the device */
	private JCheckBox cbxActive;
	
	/** The request table that will show the list of requests for this device */
	private RequestTable requestTable;
	
	/** Creates a new Device View that will be setup to create a new Device
	 * 
	 */
	
	public DeviceView() {
		this(new Device(), Mode.CREATE);
	}
	
	/** Creates a device view for viewing the given device
	 * 
	 * @param device THe device to view
	 */
	
	public DeviceView(Device device) {
		this(device, Mode.VIEW);
	}
	
	/** Creates a new device mode with the specified device, and the specified mode
	 * 
	 * @param device The device to display/edit
	 * @param mode The mode
	 */
	
	public DeviceView(Device device, Mode mode) {
		this.device = device;
		this.mode = mode;
		
		log.debug("Creating DeviceView for Device with ID {} and Name: {}", device.getDeviceId(), device.getDeviceName());
		
		initializeView();
	}
	
	/** Initializes the whole view for the device and adds the components
	 * 
	 */
	
	protected void initializeView() {
		initializeLabels();
		initializeFields();
		initializeRequestTable();
		
		initializeLayout();
		addComponents();
	}
	
	/** Initializes the values of the labels
	 * 
	 */
	
	protected void initializeLabels() {
		lblName = new JLabel("Name: ");
		lblUuid = new JLabel("UUID: ");
		lblRegistrationId = new JLabel("Registration ID: ");
		lblActive = new JLabel("Active: ");
	}
	
	/** Initializes the Fields, textFields + check box for the passed in device
	 * 
	 */
	
	protected void initializeFields() {
		txtName = new JTextField();
		txtUuid = new JTextField();
		txtRegistrationId = new JTextField();
		cbxActive = new JCheckBox();
		
		if (mode == Mode.VIEW) {
			txtName.setText(device.getDeviceName());
			txtUuid.setText(device.getDeviceUid() + "");
			txtRegistrationId.setText(device.getDeviceRegistrationId());
			cbxActive.setSelected(true);
			
			txtName.setEnabled(false);
			txtUuid.setEnabled(false);
			txtRegistrationId.setEnabled(false);
			cbxActive.setEnabled(false);
		}
	}
	
	/** Initializes the request table, and makes the request to fetch the data
	 *   for the requests
	 */
	
	protected void initializeRequestTable() {
		if (mode != Mode.CREATE) {
			//create the request table
			requestTable = new RequestTable();			
			//fetch all of the requests from the server that belong to this device
			RequestController.getInstance().getAllForDevice(device.getDeviceUid(), this);
		}
	}
	
	/** Initializes the layout for the view
	 * 
	 */
	
	protected void initializeLayout() {
		SpringLayout layout = new SpringLayout();
		JPanel panel = this;
		
		
		layout.putConstraint(SpringLayout.WEST, lblName, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblUuid, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblRegistrationId, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, lblActive, PADDING_HORIZONTAL, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, txtName, PADDING_VERTICAL, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.NORTH, txtUuid, PADDING_VERTICAL, SpringLayout.SOUTH, txtName);
		layout.putConstraint(SpringLayout.NORTH, txtRegistrationId, PADDING_VERTICAL, SpringLayout.SOUTH, txtUuid);
		layout.putConstraint(SpringLayout.NORTH, cbxActive, PADDING_VERTICAL, SpringLayout.SOUTH, txtRegistrationId);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblName, 0, SpringLayout.VERTICAL_CENTER, txtName);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblUuid, 0, SpringLayout.VERTICAL_CENTER, txtUuid);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblRegistrationId, 0, SpringLayout.VERTICAL_CENTER, txtRegistrationId);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblActive, 0, SpringLayout.VERTICAL_CENTER, cbxActive);
		
		
		layout.putConstraint(SpringLayout.WEST, txtRegistrationId, PADDING_HORIZONTAL_CLOSE, SpringLayout.EAST, lblRegistrationId);
		
		layout.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, txtRegistrationId);
		layout.putConstraint(SpringLayout.WEST, txtUuid, 0, SpringLayout.WEST, txtRegistrationId);
		layout.putConstraint(SpringLayout.WEST, cbxActive, 0, SpringLayout.WEST, txtRegistrationId);
		
		layout.putConstraint(SpringLayout.EAST, txtName, -PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, txtUuid, -PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, txtRegistrationId, -PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, cbxActive, -PADDING_HORIZONTAL, SpringLayout.EAST, panel);
		
		//add the request table
		layout.putConstraint(SpringLayout.EAST, panel, PADDING_HORIZONTAL_CLOSE, SpringLayout.EAST, requestTable);
		layout.putConstraint(SpringLayout.WEST, panel, -PADDING_HORIZONTAL_CLOSE, SpringLayout.WEST, requestTable);
		layout.putConstraint(SpringLayout.SOUTH, panel, -PADDING_VERTICAL_CLOSE, SpringLayout.SOUTH, requestTable);
		layout.putConstraint(SpringLayout.NORTH, requestTable, PADDING_VERTICAL, SpringLayout.SOUTH, cbxActive);
		
		setLayout(layout);
		
	}
	
	/** Adds the components to the view
	 * 
	 */
	
	protected void addComponents() {
		
		//add the labels
		add(lblName);
		add(lblUuid);
		add(lblRegistrationId);
		add(lblActive);
		
		//add the fields
		add(txtName);
		add(txtUuid);
		add(txtRegistrationId);
		add(cbxActive);
		
		//add the request table
		add(requestTable);
		
	}

	/** Called when the request to fetch all of this devices requests has returned successfully, puts the data
	 * 		into the request table
	 */
	
	public void onSucess(IRequest<List<Request>> request) {
		requestTable.setTableData(request.getResponse());
	}

	/** Called when the request to fetch all of this devices requests has been cancelled
	 * 
	 */
	
	public void cancelled(IRequest<List<Request>> request) {
		log.warn("Request to fetch all requests for device {} was canceled", device.getDeviceId());
	}

	/** Called when the request to fetch all of this devices requests has failed and logs the exception
	 * 
	 */
	
	public void onFailure(IRequest<List<Request>> request, Exception e) {
		log.error("Request to fetch all requests for device {} was canceled", device.getDeviceId(), e);
	}
	
	
	
	
}
