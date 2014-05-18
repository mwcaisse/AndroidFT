/**
 * 
 */
package com.ricex.aft.client.view.tab;

import javax.swing.JTabbedPane;

import com.ricex.aft.client.view.device.DeviceView;
import com.ricex.aft.client.view.request.RequestView;
import com.ricex.aft.common.entity.Device;
import com.ricex.aft.common.entity.Request;

/**
 * @author Mitchell Caisse
 *
 */
public enum TabController {

	//TODO: Possibly use a hashmap to stop adding duplicate tabs
	
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
	
	/** Convenience function that adds a new tab for the specified request
	 * 
	 * @param request The request to create the tab for
	 */
	
	public void addRequestTab(Request request, RequestView.Mode mode) {
		RequestView requestView = new RequestView(request, mode);
		
		String tabName = "New Request";
		if (mode != RequestView.Mode.CREATE) {
			tabName = "Request: " + request.getRequestName();
		}
		
		addTab(requestView, tabName);
	}
	
	/** Convenience function that adds a new tab for the specified device
	 * 
	 * @param device The device to add
	 */
	
	public void addDeviceTab(Device device, DeviceView.Mode mode) {
		DeviceView deviceView = new DeviceView(device, mode);
		addTab(deviceView, device.getDeviceName());
	}

	/** Switches to the given tab
	 * 
	 * @param tab THe tab to switch to
	 */
	
	public void switchToTab(Tab tab) {
		int index = tabbedPane.indexOfComponent(tab);
		tabbedPane.setSelectedIndex(index);
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
		switchToTab(tab);
	}
	
	/** Adds a tab that is unclosable
	 * 
	 * @param tab The tab to add
	 * @param title The title of the tab
	 */
	
	protected void addUnclosableTab(Tab tab, String title) {
		tabbedPane.addTab(title, tab);
		switchToTab(tab);
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
		Tab toClose = (Tab)tabbedPane.getComponentAt(index);
		if (toClose.onTabClose()) {
			tabbedPane.remove(toClose);
		}
	}
	
	/** Attempts to close the currently open tab. Will fail if the currently open tab is marked unclosable
	 * 
	 */
	
	public void closeCurrentTab() {
		closeTab(tabbedPane.getSelectedIndex());
	}

	
	
	/** Returns the tabbed pane that this controller manages
	 * 
	 * @return The tabbed pane
	 */
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
}
