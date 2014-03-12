/**
 * 
 */
package com.ricex.aft.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ricex.aft.client.view.DeviceView;
import com.ricex.aft.client.view.tab.TabController;
import com.ricex.aft.common.entity.Device;

/**
 *  The Launcher of the AFT Client
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTClient {

	
	/** Launches the AFTClient GUI
	 * 
	 * @param args Any command line arguments.. none atm
	 */
	
	public static void main(String[] args) {
		AFTClient client = new AFTClient();
		client.setVisible(true);
	}
	
	
	/** The JFrame that will be used to display the AFTClient */
	private JFrame frame;
	
	/** The Content pane that the frame will be displaying */
	private JPanel frameContentPane;
	
	/** The tab controller */
	private TabController tabController;
	
	/** Creates an AFT Client which initializes the JFrame + GUI
	 * 
	 */
	
	public AFTClient() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
			System.out.println("Failed to set the system look and feel");
		}
		
		initializeWindow();
		
	}
	
	
	/** Initializes the window the ATF Client will use
	 * 
	 */
	
	private void initializeWindow() {
		frame = new JFrame();
		frame.setTitle("AFT Client");
		frame.setPreferredSize(new Dimension(800,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null); // center the frame
		
		frameContentPane = new JPanel();
		BorderLayout contentPaneLayout = new BorderLayout();
		frameContentPane.setLayout(contentPaneLayout);
		
		tabController = TabController.INSTANCE;
		
		Device testDevice = new Device();
		testDevice.setDeviceName("Droid DNA");
		testDevice.setDeviceUid(789456);
		testDevice.setDeviceRegistrationId("RegistrationIdTest784564afd");
		DeviceView deviceView = new DeviceView(testDevice);
		tabController.addTab(deviceView, "Device!");
		//tabController.addTab(new Tab(), "Devices");
		//tabController.addTab(new Tab(), "Requests");
		
		frameContentPane.add(tabController.getTabbedPane(), BorderLayout.CENTER);
		
		frame.setContentPane(frameContentPane);
	}
	
	/** Sets whether or not this GUI is visible
	 * 
	 * @param visible True if visible false otherwise
	 */
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
}
