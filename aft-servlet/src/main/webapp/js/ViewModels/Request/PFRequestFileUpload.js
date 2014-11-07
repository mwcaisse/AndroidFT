

/** The ViewModel for the File upload Modal
 * 		Uploads files for a request to use
 */
function PFRequestFileUploadViewModel() {
	
	var self = this;
	
	/** The list of files to upload */
	self.files = ko.observableArray([]);
	
	/** The callback function to call when the uploading is finished */
	self.callback = function() {};
	
	/** Removes the calling file from the list of files */
	self.removeFile = function() {
		self.files.remove(this);
	};
	
	/** Uploads the file to the server */
	self.uploadFiles = function() {		
		//the count of the files
		var count = 0;
		
		$.each(self.files(), function(index, value) {
			var reader = new window.FileReader();
            reader.onload = function (e) {
            	count ++; //increase the count
            	$.ajax( {
                	url: requestRoot + "api/file/rawUpload?fileName=" + value.name(),
                	async: true,
                	type: "POST",
                	data: e.target.result,
                	cache: false,
                	contentType: "application/octet-stream",
                	processData: false                	
                }).done( function(data) {
                	//update the id of the file                	
                	value.fileId(data.value);
                }).fail( function (jqXHR, textStatus, error) {
                	alert("Failed to upload file: " + value.name() + " : " + textStatus + " : " + error);  
                }).complete( function() {
                	//check if all of the files have uploaded
                	count --;
                	if (count <= 0) {
                		self.uploadFinished();
                	}
                });
            };
            reader.readAsArrayBuffer(value.fileObj);
		});	
	};
	
	/** Called when the upload of all of the files has finished
	 */
	self.uploadFinished = function() {
		self.callback(ko.toJS(self.files()));
		self.hide();
	};

	/** Shows the File Upload pop up
	 * 
	 */
	self.show = function(callback) {
		if (callback) {
			self.callback = callback;
		}
		else {
			self.callback = function() {};
		}
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