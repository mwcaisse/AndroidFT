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
		
		//fetch the file for this request
		File reqFile = new FileRequester(context).getCompleteFile(request);
		java.io.File file =  getStorageFile(reqFile.getFileName());
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(reqFile.getFileContents());
			bos.flush();
			bos.close();
		} 
		catch (IOException e) {
			Log.e(LOG_TAG, "Error writing the file to external storage", e);
			updateRequest(RequestStatus.FAILED);
			return false;
		}	

		//scan the file
		scanFile(file);
		
		updateRequest(RequestStatus.COMPLETED);
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
		request.setRequestStatus(status);
		long res = new RequestRequester(context).updateRequest(request);
		if (res < 0) {
			//TODO: In the future might want to add some sort of try again feature
			Log.e(LOG_TAG, "Failed to send the updated request to the sever");			
			return false;
		}			
		return true;		
	}
	
	/** Creates the file object for the file in the Downloads section of External Storage
	 * 
	 * @param fileName The name of the file to create
	 * @return The storage file
	 */
	
	private java.io.File getStorageFile(String fileName) {
		java.io.File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		
		java.io.File file = new java.io.File(downloadsDir, fileName);
		return file;
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
