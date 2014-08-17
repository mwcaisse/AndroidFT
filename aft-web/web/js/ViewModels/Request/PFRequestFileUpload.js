

/** The ViewModel for the File upload Modal
 * 		Uploads files for a request to use
 */
function PFRequestFileUploadViewModel() {
	
	var self = this;
	
	/** The name of the file */
	self.fileName = ko.observable("");
	
	/** The id of the file */
	self.fileId = ko.observable(-1);
	

	/** Shows the File Upload pop up
	 * 
	 */
	self.show = function() {
		$("#divFileUploadModal").modal("show");
	};
	
	/** Hides the File Upload pop up
	 * 
	 */
	self.hide = function() {
		$("#divFileUploadModal").modal("hide");
	}
}