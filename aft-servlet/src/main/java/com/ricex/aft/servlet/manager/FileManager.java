/**
 * 
 */
package com.ricex.aft.servlet.manager;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.servlet.mapper.FileMapper;

/**
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
		fileMapper.saveFile(file);
		return file.getFileId();
	}

	/**
	 * @return the fileMapper
	 */
	
	public FileMapper getFileMapper() {
		return fileMapper;
	}

	/**
	 * @param fileMapper the fileMapper to set
	 */
	
	public void setFileMapper(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}
	
	
	
}
