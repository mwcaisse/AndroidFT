/**
 * 
 */
package com.ricex.aft.client.view.tab;

import javax.swing.JTabbedPane;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public enum TabController {

	INSTANCE;
	
	/** The tabbed pane that this controller handles */
	private JTabbedPane tabbedPane;
	
	
	/** Creates a new instance of a tab controller
	 * 
	 */
	
	private TabController() {
		tabbedPane = new JTabbedPane();
	}
	
	/** Adds the given tab with the specified title
	 * 
	 * @param tab The tab component to add
	 * @param title The title of the tab
	 */
	
	public void addTab(Tab tab, String title) {
		if (tab.isClosable()) {
			addClosableTab(tab, title);
		}
		else {
			addUnclosableTab(tab, title);
		}
	}
	
	/** Convience function that adds a new tab for the specified request
	 * 
	 * @param request The request to create the tab for
	 */
	
	public void addRequestTab(Request request) {
		
	}
	
	/** Convience function that adds a new tab for the specified device
	 * 
	 * @param device The device to add
	 */
	
	public void addDeviceTab(Device device) {
		
	}
	
	/** Adds a tab and sets its tab component to a closable component
	 * 
	 * @param tab The tab to add
	 * @param title The title of the tab to add
	 */
	
	protected void addClosableTab(Tab tab, String title) {
		tabbedPane.addTab(title, tab);
		int index = tabbedPane.indexOfComponent(tab);
		tabbedPane.setTabComponentAt(index, new ClosableTabComponent(this));
	}
	
	/** Adds a tab that is unclosable
	 * 
	 * @param tab The tab to add
	 * @param title The title of the tab
	 */
	
	protected void addUnclosableTab(Tab tab, String title) {
		tabbedPane.addTab(title, tab);
	}
	
	
	/** Attempts to close the tab at the given index, successful closure depends on the return value of
	 * 		Tab.onTabClose()
	 * 
	 * @param index The index of the tab to close
	 */
	
	public void closeTab(int index) {
		if (index < 0 || index >= tabbedPane.getTabCount()) {
			return; // Invalid index
		}
		Tab toClose = (Tab)tabbedPane.getTabComponentAt(index);
		if (toClose.onTabClose()) {
			tabbedPane.remove(toClose);
		}
	}

	
	
	/** Returns the tabbed pane that this controller manages
	 * 
	 * @return The tabbed pane
	 */
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
}
