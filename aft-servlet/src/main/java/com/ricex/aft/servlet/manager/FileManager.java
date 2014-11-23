/**
 * 
 */
package com.ricex.aft.servlet.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ricex.aft.common.data.FileQuota;
import com.ricex.aft.common.entity.File;
import com.ricex.aft.common.entity.Request;
import com.ricex.aft.common.entity.UserInfo;
import com.ricex.aft.servlet.entity.User;
import com.ricex.aft.servlet.mapper.FileMapper;

/**  The Manager for the File entity.
 *   Facilitates requests between the FileController and the database.
 * @author Mitchell Caisse
 *
 */
public enum FileManager {	
	
	/** The singleton instance of the FileManager */
	INSTANCE;
	
	/** The maximum file storage allowed for users */
	public static final long MAX_FILE_STORAGE = 246579200l;
	
	/** The file mapper that will be used to interact with the database */
	private FileMapper fileMapper;
	

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
	
	/** Returns the file information for all of the files the given user owns
	 * 
	 * @param userId The id of the user
	 * @return The list of files
	 */
	public List<File> getUserFiles(long userId) {
		return fileMapper.getFilesForUser(userId);
	}
	
	/** Returns the file quota for the given user
	 * 
	 * @param user The user to get the file quota for
	 * @return The file quota
	 */
	public FileQuota getFileQuotaForUser(User user) {
		FileQuota quota = new FileQuota();
		Collection<File> userFiles = getUserFiles(user.getUserId());	
		quota.setUser(user.toUserInfo());
		quota.setFileCount(userFiles.size());
		quota.setFileStorage(MAX_FILE_STORAGE);
		quota.setFileStorageUsed(calculateStorageUsed(user.getUserId()));		
		return quota;
	}
	
	/** Calculates the amount of storage the given user has used
	 * 
	 * @param user The user to check
	 * @return The amount of storage the user has remaining in bytes
	 */
	protected long calculateStorageUsed(long userId) {
		Collection<File> userFiles = getUserFiles(userId);	
		long usedStorage = 0;
		for(File file : userFiles) {
			usedStorage += file.getFileSize();
		}
		return usedStorage;
	}
	
	/** Calculates how much storage that the user has remaining
	 * 
	 * @param user The user to calculate it for
	 * @return The amount of storage they have remaining in bytes
	 */
	
	protected long calculateStorageRemaining(long userId){
		long userMaxStorage = MAX_FILE_STORAGE;
		return MAX_FILE_STORAGE - calculateStorageRemaining(userId);
	}
	
	/** Creates a new file with the specified contents
	 * 
	 * @param fileContents The contents of the file
	 * @param fileName The name of the file
	 * @param owner The owner of the file
	 * @return The id of the new file
	 */
	
	public long createFile(byte[] fileContents, String fileName, UserInfo owner) {
		File fileInfo = new File();
		fileInfo.setFileName(fileName);
		fileInfo.setRequestId(null);
		fileInfo.setFileSize(fileContents.length);
		fileInfo.setFileOwner(owner);
		fileMapper.createFile(fileInfo, fileContents);
		return fileInfo.getFileId();
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
