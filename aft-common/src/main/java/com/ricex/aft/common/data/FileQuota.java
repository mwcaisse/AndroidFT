package com.ricex.aft.common.data;

import com.ricex.aft.common.entity.UserInfo;

/** Container class to transfer information about a users Files and Quota
 * 
 * @author Mitchell Caisse
 *
 */

public class FileQuota {

	/** The user this File Quota is for */
	private UserInfo user;
	
	/** The amount of storage the user has in bytes */
	private long fileStorage;
	
	/** The amount of file storage the user has already used in bytes */
	private long fileStorageUsed;
	
	/** The number of files the user has uploaded */
	private int fileCount;

	
	/**
	 * @return the user
	 */
	public UserInfo getUser() {
		return user;
	}

	
	/**
	 * @param user the user to set
	 */
	public void setUser(UserInfo user) {
		this.user = user;
	}

	
	/**
	 * @return the fileStorage
	 */
	public long getFileStorage() {
		return fileStorage;
	}

	
	/**
	 * @param fileStorage the fileStorage to set
	 */
	public void setFileStorage(long fileStorage) {
		this.fileStorage = fileStorage;
	}

	
	/**
	 * @return the fileStorageUsed
	 */
	public long getFileStorageUsed() {
		return fileStorageUsed;
	}

	
	/**
	 * @param fileStorageUsed the fileStorageUsed to set
	 */
	public void setFileStorageUsed(long fileStorageUsed) {
		this.fileStorageUsed = fileStorageUsed;
	}


	
	/**
	 * @return the fileCount
	 */
	public int getFileCount() {
		return fileCount;
	}


	
	/**
	 * @param fileCount the fileCount to set
	 */
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	
	
	
}
