

/** The ViewModel for the File upload Modal
 * 		Uploads files for a request to use
 */
function PFRequestFileUploadViewModel() {
	
	var self = this;
	
	self.files = ko.observableArray([]);
	
	/** Removes the calling file from the list of files */
	self.removeFile = function() {
		self.files.remove(this);
	};

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
		self.files.removeAll(); //remove all of the files
	};
	
	/** Shows the File selector from the file input field */
	self.selectFiles = function() {
		$("#fileUploadInput").click();
	};
}