/**
 * 
 */
package com.ricex.aft.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.ricex.aft.client.view.device.DeviceTableView;
import com.ricex.aft.client.view.request.RequestTableView;
import com.ricex.aft.client.view.tab.TabController;
import com.ricex.aft.client.view.toolbar.Toolbar;

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
	
	/** The table view for devices */
	private DeviceTableView deviceTableView;
	
	/** The table view for requests */
	private RequestTableView requestTableView;
	
	/** The toolbar to add to the display */
	private Toolbar toolbar;
	
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
		
		toolbar = new Toolbar();
		
		tabController = TabController.INSTANCE;		
		
		deviceTableView = new DeviceTableView();
		requestTableView = new RequestTableView();
		
		tabController.addTab(deviceTableView, "Devices");
		tabController.addTab(requestTableView, "Requests");
		
		frameContentPane.add(tabController.getTabbedPane(), BorderLayout.CENTER);
		frameContentPane.add(toolbar, BorderLayout.NORTH);
		
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
