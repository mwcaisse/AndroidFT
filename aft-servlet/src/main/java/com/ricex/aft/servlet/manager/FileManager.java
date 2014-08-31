/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.ArrayList;
import java.util.List;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.servlet.mapper.FileMapper;

/**  The Manager for the File entity.
 *   Facilitates requests between the FileController and the database.
 * @author Mitchell Caisse
 *
 */
public enum FileManager {

	/** The singleton instance of the FileManager */
	INSTANCE;
	
	/** The file mapper that will be used to interact with the database */
	private FileMapper fileMapper;
	
	
	/** Returns the file with the given file id
	 * 
	 * @param fileId The id of the file to fetch
	 * @return The file 
	 */
	
	public File getFile(long fileId) {
		return fileMapper.getFile(fileId);
	}
	
	/** Returns the information about the given file. The file class without the FileContents field
	 * 
	 * @param fileId The id of the file to fetch
	 * @return The file information about the specified file
	 */
	
	public File getFileInfo(long fileId) {
		return fileMapper.getFileInfo(fileId);
	}
	
	/** Returns the contents of the file with the given id
	 * 
	 * @param fileId The id of the file contents to fetch
	 * @return The raw bytes of the file
	 */
	
	public byte[] getFileContents(long fileId) {
		return fileMapper.getFileContents(fileId).getFileContents();
	}
	
	/** Returns the file information for all of the files associated with the given request
	 * 	
	 * @param requestId The id of the request to fetch the files for
	 * @return The list of file information for the request's files
	 */
	public List<File> getRequestFiles(long requestId) {
		return fileMapper.getRequestFiles(requestId);
	}
	
	/** Creates a new file with the specified contents
	 * 
	 * @param fileContents The contents of the file
	 * @param fileName The name of the file
	 * @return The id of the new file
	 */
	
	public long createFile(byte[] fileContents, String fileName) {
		File file = new File();
		file.setFileContents(fileContents);
		file.setFileName(fileName);
		file.setRequestId(null);
		fileMapper.saveFile(file);
		return file.getFileId();
	}
	
	/** Updates the files for the given request
	 * 
	 * 	Will diff the current files for the request, with the files in the param, and
	 * 		remove / add files as appropriate
	 * 
	 * @param request The request to update the files for
	 * @return True if successful, false otherwise
	 */
	
	public boolean updateFilesForRequest(Request request) {
		List<File> currentFiles = getRequestFiles(request.getRequestId());
		List<File> requestFiles = new ArrayList<File>(request.getRequestFiles());
		
		for (File file : currentFiles) {
			if (requestFiles.contains(file)) {
				requestFiles.remove(file); // this  file currently exist, remove it from requestFiles list
			}
			else {
				//this file isn't in the request files list anymore
				deleteFile(file);
			}
		}
		
		//add the remaning files
		for (File file : requestFiles) {
			file.setRequestId(request.getRequestId());
			fileMapper.updateFileRequest(file);
		}
		
		return false;
	}
	
	/** Deletes the given file
	 * 
	 * @param file The file to delete
	 */
	public void deleteFile(File file) {
		deleteFile(file.getFileId());
	}
	
	/** Deletes the file with the given id
	 * 
	 * @param fileId The id of the file to delete
	 */
	
	public void deleteFile(long fileId) {
		fileMapper.deleteFile(fileId);
	}

	/** Returns the FileMapper that this FileManager is using to interact with the data store
	 * @return the fileMapper 
	 */
	
	public FileMapper getFileMapper() {
		return fileMapper;
	}

	/** Sets the FileMapper that this Filemanager will use to interact with the data store
	 * @param fileMapper the fileMapper to set
	 */
	
	public void setFileMapper(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}
	
	
	
}
