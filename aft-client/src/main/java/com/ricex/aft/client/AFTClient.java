/**
 * 
 */
package com.ricex.aft.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
	
	/** The tabbed pane */
	private JTabbedPane tabbedPane;
	
	/** Creates an AFT Client which initializes the JFrame + GUI
	 * 
	 */
	
	public AFTClient() {
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
		
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Devices", new JPanel());
		
		frameContentPane.add(tabbedPane, BorderLayout.CENTER);
		
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
