/**
 * 
 */
package com.ricex.aft.client.view;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Device;

/**
 *  The view to be used for displaying a list of all the current known devices
 *  
 * 
 * @author Mitchell Caisse
 *
 */
public class DeviceView extends Tab {

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
	private static final int PADDING_VERTICAL = 10;
	
	/** Vertical padding constant for related, or close, elements in the SpringLayout */
	private static final int PADDING_VERTICAL_CLOSE = 5;
	
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
	
	/** Checkbox for displaying the active status of the device */
	private JCheckBox cbxActive;
	
	/** Creates a new Device View that will be setup to create a new Device
	 * 
	 */
	
	public DeviceView() {
		this(new Device(), Mode.CREATE);
	}
	
	/** Creates a new device mode with the specified device, and the specified mode
	 * 
	 * @param device The device to display/edit
	 * @param mode The mode
	 */
	
	public DeviceView(Device device, Mode mode) {
		this.device = device;
		this.mode = mode;
	}
	
	/** Initializes the values of the labels
	 * 
	 */
	
	private void initializeLabels() {
		lblName = new JLabel("Name: ");
		lblUuid = new JLabel("UUID: ");
		lblRegistrationId = new JLabel("Registration ID: ");
		lblActive = new JLabel("Active: ");
	}
	
	/** Initializes the Fields, textFields + check box for the passed in device
	 * 
	 */
	
	private void initializeFields() {
		txtName = new JTextField();
		txtUuid = new JTextField();
		txtRegistrationId = new JTextField();
		cbxActive = new JCheckBox();
		
		if (mode != Mode.CREATE) {
			txtName.setText(device.getDeviceName());
			txtUuid.setText(device.getDeviceUid() + "");
			txtRegistrationId.setText(device.getDeviceRegistrationId());
			cbxActive.setSelected(true);
		}
	}
	
	/** Initializes the layout for the view
	 * 
	 */
	
	private void initialize() {
		SpringLayout layout = new SpringLayout();
		JPanel panel = this;
		
		
		layout.putConstraint(SpringLayout.WEST, panel, PADDING_HORIZONTAL, SpringLayout.EAST, lblName);
		layout.putConstraint(SpringLayout.WEST, panel, PADDING_HORIZONTAL, SpringLayout.EAST, lblUuid);
		layout.putConstraint(SpringLayout.WEST, panel, PADDING_HORIZONTAL, SpringLayout.EAST, lblRegistrationId);
		layout.putConstraint(SpringLayout.WEST, panel, PADDING_HORIZONTAL, SpringLayout.EAST, lblActive);
		
		layout.putConstraint(SpringLayout.NORTH, panel, PADDING_VERTICAL, SpringLayout.NORTH, txtName);
		layout.putConstraint(SpringLayout.SOUTH, txtName, PADDING_VERTICAL, SpringLayout.NORTH, txtUuid);
		layout.putConstraint(SpringLayout.SOUTH, txtUuid, PADDING_VERTICAL, SpringLayout.NORTH, txtRegistrationId);
		layout.putConstraint(SpringLayout.SOUTH, txtRegistrationId, PADDING_VERTICAL, SpringLayout.NORTH, cbxActive);
		
	}
	
	
	
	
}
