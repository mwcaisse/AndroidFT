/**
 * 
 */
package com.ricex.aft.client.view.request.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.view.request.RequestView;

/**
 * @author Mitchell Caisse
 *
 */
public class BrowseAction extends AbstractAction {

	private static Logger log = LoggerFactory.getLogger(BrowseAction.class);
	
	/** The request view this browse action is for */
	private final RequestView requestView;
	
	/** The filer choose that will be used to ask the user for the file */
	private final JFileChooser fileChooser;
	
	/** Creates a new Browse Action for the specified request view 
	 * 
	 * @param requestView The request view this browse action is a part of
	 */
	
	public BrowseAction(RequestView requestView) {
		this.requestView = requestView;
		fileChooser = new JFileChooser();
	}


	/** Called when the browse button is pressed
	 * 
	 */
	
	public void actionPerformed(ActionEvent e) {
		//create the file chooser dialog
		int res = fileChooser.showOpenDialog(requestView);
		
		if (res == JFileChooser.APPROVE_OPTION) {
			//get the file the user selected to open
			File osFile = fileChooser.getSelectedFile();
			byte[] fileBytes = null;
			
			try {
				fileBytes = Files.readAllBytes(Paths.get(osFile.getPath()));
			}
			catch (IOException ex) {
				log.error("Failed to open file {}", osFile, ex);
			}
			
			if (fileBytes != null) {
				com.ricex.aft.common.entity.File requestFile = new com.ricex.aft.common.entity.File();
				requestFile.setFileContents(fileBytes);
				requestFile.setFileName(osFile.getName());
			}
			
	
			
			//we have the file. lets add it to the request
			com.ricex.aft.common.entity.File requestFile = new com.ricex.aft.common.entity.File();
			
		}
	}
	
}
