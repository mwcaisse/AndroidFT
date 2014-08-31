/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.List;

import com.ricex.aft.common.entity.File;

/** MyBatis Mapper Interface for fetching files from the database
 * 
 * @author Mitchell Caisse
 *
 */
public interface FileMapper {
	
	
	/** Retrieves the file with the given ID from the database
	 * @param fileId The id of the file
	 * @return The file
	 */
	
	public File getFile(long fileId);
	
	/** Retreives the information about the file with the given ID from the database.
	 * 		The file without the file contents.
	 * @param fileId The id of the file to fetch
	 * @return The file without the file contents
	 */
	
	public File getFileInfo(long fileId);
	
	/** Retrieves the information about the files for the specified request
	 * 
	 * @param requestId The ID of the request to fetch the file info for
	 * @return The list of files
	 */
	
	public List<File> getRequestFiles(long requestId);
	
	/** Retrieves the contents of the file with the specified id
	 * 
	 * @param fileId The id of the file to fetch the content of
	 * @return A byte array containing the raw file data
	 */
	
	public File getFileContents(long fileId);
	
	/** Saves the given file to the database
	 * 
	 * @param file The file to delete
	 */
	
	public void saveFile(File file);
	
	/** Updates the request that the specified file belongs to
	 * 	
	 * @param file The file containing the file id, and new request id to update
	 */
	public void updateFileRequest(File file);
	
	
	/** Deletes the given file from the database
	 * 
	 * @param fileId The id of the file to delete
	 */
	
	public void deleteFile(long fileId);
	

}
