/**
 * 
 */
package com.ricex.aft.client.view.login;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.ricex.aft.client.config.AFTClientConfig;

/**
 *  The Login window
 * 
 * @author Mitchell Caisse
 *
 */

public class LoginWindow extends JFrame {

	
	/** Tests the Login Window
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
	}
	
	/** The label for the username text box */
	private JLabel lblUsername;
	
	/** The label for the password text box */
	private JLabel lblPassword;
	
	/** The label for the server address text box */
	private JLabel lblServerAddress;
	
	/** The text box for the user name */
	private JTextField txtUsername;
	
	/** The textbox for the password */
	private JPasswordField txtPassword;
	
	/** The textbox for the server address */
	private JTextField txtServerAddress;
	
	/** The layout of the form */
	private SpringLayout layout;
	
	/** The button to log in to the server */
	private JButton butLogin;	
	
	/** The content pane */
	private JPanel contentPane;
	
	/** The Close padding constants */
	private final int horizontalPaddingClose = 5;
	private final int verticalPaddingClose = 5;
	
	/** The padding constants */
	private final int horizontalPadding = 10;
	private final int verticalPadding = 10;

	
	/** Creates a new Login Window */
	
	public LoginWindow() {
		initialize();
	}
	
	/** Initializes the window
	 * 
	 */
	
	private void initialize() {
		
		contentPane = new JPanel();
		
		initializeLabels();
		initializeTextFields();
		initializeButtons();
		
		initializeLayout();
		
		addComponents();
		
		setContentPane(contentPane);
		setTitle("AFT Client Login");
		setPreferredSize(new Dimension(400,165));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null); // center the frame
	}
	
	/** Initializes the labels
	 * 
	 */
	
	private void initializeLabels() {
		lblUsername = new JLabel("Username:");
		lblPassword = new JLabel("Password:");
		lblServerAddress = new JLabel("Server Address:");
	}
	
	/** Initializes the text fields
	 * 
	 */
	
	private void initializeTextFields() {
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		txtServerAddress = new JTextField();
		
		txtUsername.setText(AFTClientConfig.INSTANCE.getClientUserName());
		txtServerAddress.setText(AFTClientConfig.INSTANCE.getServerAddress());
	}
	
	/** Initializes the buttons
	 * 
	 */
	
	private void initializeButtons() {
		butLogin = new JButton("Login");
		butLogin.setAction(new LoginAction(this));
		butLogin.setText("Login");
	}
	
	/** Initializes the layout for the form
	 * 
	 */
	
	private void initializeLayout() {
		
		layout = new SpringLayout();
		
		// the horizontal alignment of the labels
		layout.putConstraint(SpringLayout.WEST, lblServerAddress, horizontalPadding, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, lblServerAddress);
		layout.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblServerAddress);
		
		//the vertical alignment of the labels		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblServerAddress, 0, SpringLayout.VERTICAL_CENTER, txtServerAddress);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblUsername, 0, SpringLayout.VERTICAL_CENTER, txtUsername);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblPassword, 0, SpringLayout.VERTICAL_CENTER, txtPassword);
		
		// the horizontal alignment of the text fields
		layout.putConstraint(SpringLayout.WEST, txtServerAddress, horizontalPadding, SpringLayout.EAST, lblServerAddress);
		layout.putConstraint(SpringLayout.WEST, txtUsername, 0, SpringLayout.WEST, txtServerAddress);
		layout.putConstraint(SpringLayout.WEST, txtPassword, 0, SpringLayout.WEST, txtServerAddress);
		
		layout.putConstraint(SpringLayout.EAST, txtServerAddress, -horizontalPadding, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.EAST, txtUsername, 0, SpringLayout.EAST, txtServerAddress);
		layout.putConstraint(SpringLayout.EAST, txtPassword, 0, SpringLayout.EAST, txtServerAddress);
		
		//the vertical alignment of the text fields
		layout.putConstraint(SpringLayout.NORTH, txtServerAddress, verticalPadding, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, txtUsername, verticalPadding, SpringLayout.SOUTH, txtServerAddress);
		layout.putConstraint(SpringLayout.NORTH, txtPassword, verticalPadding, SpringLayout.SOUTH, txtUsername);		
		
		//the position of the button
		layout.putConstraint(SpringLayout.NORTH, butLogin, verticalPadding, SpringLayout.SOUTH, txtPassword);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, butLogin, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		
		
		
		
		contentPane.setLayout(layout);
		
	}
	
	/** Adds the components to the view */
	
	private void addComponents() {
		contentPane.add(lblUsername);
		contentPane.add(lblPassword);
		contentPane.add(lblServerAddress);
		
		contentPane.add(txtUsername);
		contentPane.add(txtPassword);
		contentPane.add(txtServerAddress);
		
		contentPane.add(butLogin);
	}
	
	/** Returns the server address that the user has entered
	 * 
	 * @return The server address entered by the user
	 */
	
	public String getServerAddress() {
		return txtServerAddress.getText();
	}
	
	/** Returns the username the user has entered
	 * 
	 * @return The username the user has entered
	 */
	public String getUsername() {
		return txtUsername.getText();
	}
	
	/** Returns the password the user has entered
	 * 
	 * @return The password the user has entered
	 */
	public String getUserPassword() {
		return txtPassword.getPassword().toString();
	}
	
}
