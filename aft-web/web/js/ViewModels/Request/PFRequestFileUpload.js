

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

		var defereds = [];
		
		$.each(self.files(), function(index, value) {
			var reader = new window.FileReader();
            reader.onload = function (e) {
            	defereds[index] = $.ajax( {
                	url: "http://" + host + "/aft-servlet/manager/file/rawUpload?fileName=" + value.name(),
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
                });
            };
            reader.readAsArrayBuffer(value.fileObj);
		});
		
		//when all of the ajax requests are done
		$.when.apply(defereds).then( function() {
			//call the callback with the files, in plain JS
			self.callback(ko.toJS(self.files()));
			self.hide();	
		});
	
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