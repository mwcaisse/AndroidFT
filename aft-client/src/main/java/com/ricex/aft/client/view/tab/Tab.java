/**
 * 
 */
package com.ricex.aft.client.view.tab;

import javax.swing.JPanel;

/**
 * @author Mitchell Caisse
 *
 */
public abstract class Tab extends JPanel {


	/** Called when the tab has gained focus
	 * 
	 */
	
	public void onGainedFocus() {
		
	}

	/** Called when the tab has lost focus
	 * 
	 */
	public void onLostFocus() {
		
	}

	/** Called when the user has asked to close the tab. 
	 * 
	 * @return True if it is okay to close the tab, False if the tab should remain open
	 */
		
	public boolean onTabClose() {
		return true;
	}
	
	/** Whether or not the tab is closable
	 * 
	 * @return True if the tab should be able to be closed, false otherwise
	 */
	
	public boolean isClosable() {
		return true;
	}

}
