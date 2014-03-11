/**
 * 
 */
package com.ricex.aft.client.view.tab;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Mitchell Caisse
 *
 */
public class ClosableTabComponent extends JPanel implements ActionListener {

	/**The tab controller that this tab component is being added to */
	private TabController tabController;
	
	/** The label for displaying the title of the tab */
	private JLabel lblTitle;
	
	/** The button for closing the tab */
	private JButton butClose;
	
	public ClosableTabComponent(final TabController tabController) {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		setLayout(layout);
		
		lblTitle = new JLabel() {
			
			public String getText() {
				JTabbedPane tabbedPane = tabController.getTabbedPane();
				int index = tabbedPane.indexOfTabComponent(ClosableTabComponent.this);
				if (index > -1) {
					return tabbedPane.getTitleAt(index);
				}
				return "";				
			}
		};
		
		lblTitle.setBorder(BorderFactory.createEmptyBorder(3, 0, 2, 7));
		
		butClose = new JButton("\u2716");
		butClose.setFont(butClose.getFont().deriveFont(8f));
		butClose.setMargin(new Insets(0, 0, 0, 0));
		butClose.addActionListener(this);
		
		add(lblTitle);
		add(butClose);
		
		
		
		
	}

	/** Closes the tab when the user clicks on the X button
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		//close the tab when the button is pressed
		int index = tabController.getTabbedPane().indexOfTabComponent(this);
		if (index > -1) {
			tabController.closeTab(index);
		}
		//if the index is invalid something horrible has gone wrong
		
	}
}
