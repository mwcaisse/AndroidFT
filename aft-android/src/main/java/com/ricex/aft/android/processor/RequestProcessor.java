/**
 * 
 */
package com.ricex.aft.android.processor;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.ricex.aft.android.requester.FileRequester;
import com.ricex.aft.android.requester.RequestRequester;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.RequestStatus;

/** Processes requests
 * 
 * @author Mitchell Caisse
 *
 */

public class RequestProcessor {

	/** The log tag for this class */
	private static final String LOG_TAG = "PushFileRP";
	
	/** The context that this request processor is working in */
	private final Context context;
	
	/** The request that his processor will process */
	private final Request request;
	
	/** Creates a new Request Processor to process the specified request
	 * 
	 * @param context The context this request processor was called from
	 * @param request The request to process
	 * @param mediaScannerConnection The media scanner connection to use to scan for files, The connection must
	 * 		already be opened.
	 */
	
	public RequestProcessor(Context context, Request request) {
		this.context = context;
		this.request = request;
	}
	
	/** Processes the request.
	 * 
	 * For now this will download the file from the web service, and then save it into the downloads folder.
	 * 
	 * 
	 * @return True if completed successfully, false otherwise
	 */
	
	public boolean processRequest() {
		
		//check if we can write to external storage first
		
		if (!isExternalStorageWritable()) {
			//external storage was not writable *tear we cant do anything further
			Log.i(LOG_TAG, "External storage was not writable, aborting");
			return false;
		}
		
		//process / download each of the files
		for(File file : request.getRequestFiles()) {
			if (!downloadFile(file)) {
				updateRequest(RequestStatus.FAILED, "Failed to download file: " + file.getFileId() + " : " + file.getFileName());
			}
		}
		
		updateRequest(RequestStatus.COMPLETED);
		return true;
	}
	
	/** Downloads the specified file
	 * 
	 * @param fileInfo The information of the file to download
	 * @return True if successful, false otherwise
	 */
	
	protected boolean downloadFile(File fileInfo) {
		java.io.File localFile =  getStorageFile(fileInfo);
		byte[] fileContents = new FileRequester(context).getFileContents(fileInfo.getFileId());
		if (localFile == null) {
			Log.w(LOG_TAG, "Creating storage file failed");
			updateRequest(RequestStatus.FAILED, "Unable to create storage file");
			return false;
		}
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile));
			bos.write(fileContents);
			bos.flush();
			bos.close();
		} 
		catch (IOException e) {
			Log.e(LOG_TAG, "Error writing the file to external storage", e);
			updateRequest(RequestStatus.FAILED, "Unable to write the file to disk");
			return false;
		}	

		//scan the file
		scanFile(localFile);
		
		return true;
	}
	
	/** Scans the specified file with the Media Scanner by broadcasting an Event
	 * 
	 * @param file The file to scan
	 */
	
	private void scanFile(java.io.File file) {
		Uri contentUri = Uri.fromFile(file);
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);		
	}
	
	/** Sends an update request to the server with the specified status
	 * 
	 * @param status The new status of the request
	 * @return True if the request updated successfully, false otherwise
	 */
	
	private boolean updateRequest(RequestStatus status) {
		return updateRequest(status, "");	
	}
	
	/** Sends an update request to the server with the specified status
	 * 
	 * @param status The new status of the request
	 * @param message The message associated with the status
	 * @return True if the request updated successfully, false otherwise
	 */
	
	private boolean updateRequest(RequestStatus status, String message) {
		request.setRequestStatus(status);
		request.setRequestStatusMessage(message);
		long res = new RequestRequester(context).updateRequest(request);
		if (res < 0) {
			//TODO: In the future might want to add some sort of try again feature
			Log.e(LOG_TAG, "Failed to send the updated request to the sever");			
			return false;
		}			
		return true;	
	}
	
	/** Creates the file object to store the file in the given file
	 * 
	 * @param file The request file to create the storage file for
	 * @return The storage file, or null if failed to create the file
	 */
	
	private java.io.File getStorageFile(File file) {
		java.io.File baseDir = getBaseDirectory(request);
		
		String dirPath = baseDir.getAbsolutePath() + "/" + request.getRequestFileLocation();
		
		java.io.File path = new java.io.File(dirPath);
		path.mkdirs();
		
		if (path.isDirectory()) {
			//creating the directory was successful, return a file to the request file			
			return new java.io.File(path, file.getFileName());
		}		
		//failed to create the directory, return failure
		Log.w(LOG_TAG, "Failed to create directory: " + path.getAbsolutePath());
		return null;
	}
	
	/** Returns the base directory for the request
	 * 
	 * @param request The request to fetch the base directory for
	 * @return The file representing the base directory
	 */
	
	private java.io.File getBaseDirectory(Request request) {
		switch (request.getRequestDirectory()) {
		
		case DOCUMENTS:
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
		case DOWNLOADS:
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		case MOVIES:
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
		case MUSIC:
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		case PICTURES:
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);			
		case ROOT:		
		default:
			return Environment.getExternalStorageDirectory();
		
		}
	}
	
	/** Determines if the external storage is writable
	 * 
	 * @return True if able to write to external storage, false otherwise
	 */
	
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}
	
}
