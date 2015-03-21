/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ricex.aft.common.entity.File;
import com.ricex.aft.servlet.entity.FileContent;

/** File Mapper for fetching file data
 * 
 * @author Mitchell Caisse
 *
 */
public interface FileMapper {
	
	
	/** Retrieves the information (meta-data) for the file with the given id
	 * @param fileId The id of the file to fetch
	 * @return The file without the file contents
	 */
	
	public File getFileInfo(long id);
	
	/** Retrieves the information (meta-data) about the files for the specified request
	 * 
	 * @param requestId The ID of the request to fetch the file info for
	 * @return The list of files
	 */
	
	public List<File> getRequestFiles(long requestId);
	
	/** Retrieves the information (meta-data) about the files for the specified user
	 * 
	 * @param userId The id of the user to fetch the file info for
	 * @return The list of files
	 */
	
	public List<File> getFilesForUser(long userId);
	
	/** Retrieves the contents of the file with the specified id
	 * 
	 * @param fileId The id of the file to fetch the content of
	 * @return The FileContent containing the raw bytes of the file
	 */
	
	public FileContent getFileContents(long id);
	

	/** Creates the file with the given meta-data + Content
	 * 
	 * @param fileInfo The information (meta-data) For the file
	 * @param fileContents The contents of the file
	 */
	
	public void createFile(@Param("fileInfo") File fileInfo, 
							@Param("fileContents") byte[] fileContents);
	
	/** Updates the request that the specified file belongs to
	 * 	
	 * @param file The file containing the file id, and new request id to update
	 */
	public void updateFileRequest(File file);
	
	
	/** Deletes the given file from the database
	 * 
	 * @param fileId The id of the file to delete
	 */
	
	public void deleteFile(long id);
	

}
