/**
 * 
 */
package com.ricex.aft.client.view.request.action;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.controller.FileController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.view.request.RequestView;
import com.ricex.aft.common.entity.File;

/**
 * @author Mitchell Caisse
 *
 */
public class DownloadAction extends AbstractAction implements RequestListener<File> {

	/** The logger for this class */
	private static Logger log = LoggerFactory.getLogger(DownloadAction.class);
	
	/** The request view this download action is a part of */
	private final RequestView requestView;
	
	/** The file choose to use to allow the user to select a location */
	private final JFileChooser fileChooser;
	
	/** The file to write the file contents to */
	private java.io.File toWrite;
	
	/** Creates a new DownloadAction with the specified request view
	 * 
	 * @param requestView The request view
	 */
	
	public DownloadAction(RequestView requestView) {
		this.requestView = requestView;
		fileChooser = new JFileChooser();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		int res = fileChooser.showSaveDialog(requestView);
		
		if (res == JFileChooser.APPROVE_OPTION) {
			toWrite = fileChooser.getSelectedFile();

			File requestFile = requestView.getRequest().getRequestFile();
			
			//if the file contents in the request are already set, then just use them, otherwise we fetch
			if (requestFile.getFileContents() == null) {
				FileController.getInstance().getFile(requestFile.getFileId(), this);
			}
			else {
				writeFile(toWrite, requestFile.getFileContents());
			}
		}
		
	}
	
	/** Writes the file contents to the specified file
	 * 
	 * @param destFile The destination file to write the contents to
	 * @param fileContents
	 */
	
	protected void writeFile(java.io.File destFile, byte[] fileContents) {
		try {
			Files.write(Paths.get(destFile.getPath()), fileContents, StandardOpenOption.CREATE);
			requestView.setStatusMessage("Successfully downloaded file: " + destFile.getName());
		}
		catch (IOException e) {
			log.error("Failed to download file.", e);
			requestView.setStatusMessage("Failed to write file: " + destFile.getName());
		}
	}

	/** Sets the file contents in the file in the request view, then writes the contents to file
	 * 
	 */
	
	public void onSucess(IRequest<File> request) {
		requestView.getRequest().setRequestFile(request.getResponse());
		writeFile(toWrite, request.getResponse().getFileContents());		
	}

	/** Called when the fetch file request was cancelled
	 * 
	 */
	
	public void cancelled(IRequest<File> request) {
		log.info("Fetch File request canceled");		
	}

	/** Called when the request file request failed
	 * 
	 */
	
	public void onFailure(IRequest<File> request, Exception e) {
		log.error("Fetch File request failed", e);	
		requestView.setStatusMessage("Failed to download file.");
	}

}
