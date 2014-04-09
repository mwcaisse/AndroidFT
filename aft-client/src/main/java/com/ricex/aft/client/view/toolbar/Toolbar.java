/**
 * 
 */
package com.ricex.aft.client.view.toolbar;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ricex.aft.client.view.request.RequestView;
import com.ricex.aft.client.view.tab.TabController;
import com.ricex.aft.common.entity.Request;

/** The toolbar to display at the top
 * 
 * @author Mitchell Caisse
 *
 */
public class Toolbar extends JPanel implements ActionListener {	
	/**JButton to create a new request */
	private JButton butNewRequest;
	
	/** Creates a new toolbar
	 * 
	 */
	
	public Toolbar() {		
		butNewRequest = new JButton("New Request");		
		butNewRequest.addActionListener(this);
		
		setLayout(new FlowLayout());
		
		add(butNewRequest);
	}

	/** called when the user clicks on the new request button
	 * 
	 */
	
	public void actionPerformed(ActionEvent e) {
		TabController.INSTANCE.addRequestTab(new Request(), RequestView.Mode.CREATE);		
	}
	
}
