/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import com.ricex.aft.common.entity.File;

/** Mapper Interface for fetching files from the database
 * 
 * @author Mitchell Caisse
 *
 */
public interface FileMapper {
	
	
	/** Retreives the file with the given ID from the database, without the FileContents
	 * @param fileId The id of the file
	 * @return The file
	 */
	
	public File getFile(long fileId);
	
	/** Retrieves the contents of the file with the specified id
	 * 
	 * @param fileId The id of the file to fetch the content of
	 * @return A byte array contanining the raw file data
	 */
	
	public byte[] getFileContents(long fileId);
	
	/** Saves the given file to the database
	 * 
	 * @param file The file to delete
	 */
	
	public void saveFile(File file);
	
	
	/** Deletes the given file from the database
	 * 
	 * @param fileId The id of the file to delete
	 */
	
	public void deleteFile(long fileId);
	

}
