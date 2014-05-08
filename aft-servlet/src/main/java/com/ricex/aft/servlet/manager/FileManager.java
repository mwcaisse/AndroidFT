/**
 * 
 */
package com.ricex.aft.servlet.manager;

import com.ricex.aft.common.entity.File;
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
