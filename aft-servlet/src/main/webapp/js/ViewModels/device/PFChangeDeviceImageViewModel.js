
/** The View Model for changing the image for a specified device */
function PFChangeDeviceImageViewModel() {
	
	var self = this;
	
	/** The device the modal is changing the image of */
	self.device = ko.observable(new BasicDeviceModel());
	
	/** The file that the user has selected */
	self.file = ko.observable({});
	
	/** The url to the proposed file */
	self.proposedFileUrl = ko.observable(null);
	
	/** Initialize the callback function */
	self.callback = function() {};
	
	
	/** Subscript to the file to update the new image thumbnail when a new image is selected
	 */
	self.file.subscribe(function(newValue) {
		if (newValue.length > 0) {
			var fileObj = newValue[0].fileObj;
			console.log("Obj??");
			console.log(newValue[0]);
			console.log(newValue[0].fileObj);
			//console.log(fileObj);
			
			if (fileObj) {			
				//check if the file obj is an image type
				if (fileObj.type.match("image.*")) {	
					console.log("Is Image");
					console.log(fileObj);
					//craete the file reader to read in the file
					var reader = new FileReader();
				
					//create the function to set the file url once it has been loaded
					reader.onload = function(e) {
						self.proposedFileUrl(e.target.result);
					};
					//read in the file
					reader.readAsDataURL(fileObj);
				}
				else {
					alert("Please upload an image!");
					self.file({});
				}
				
			}
		}
	});
	
	/** Shows the modal
	 * 
	 * @param device The DeviceModel that the modal is for
	 * @param callback The function to call when the modal is closed / image has been closed
	 */
	self.show = function(device, callback) {
		self.device(device);
		if (callback) {
			self.callback = callback;
		}
		else {
			self.callback = function() {};
		}
		
		$("#divChangeDeviceImageModal").modal("show");	
		
	};
	
	/** Hides the modal
	 * 
	 */
	self.hide = function() {
		$("#divChangeDeviceImageModal").modal("hide");
		self.file({});
	};
	
	/** Changes the image */
	self.changeImage = function() {
		
		//validate that the use has actually selected an image file
		if (self.file()) {
			var fileObj = self.file()[0].fileObj;
			if (fileObj && fileObj.type.match("image.*")) {
				//we're good, start the upload
				
				//pull the id from the device 
				var deviceId = ko.toJS(self.device).id;				
				var reader = new window.FileReader();
		        reader.onload = function (e) {
		        	ajaxCSRF( {
		            	url: requestRoot + "api/device/image/rawUpload?deviceId=" + deviceId,
		            	async: true,
		            	type: "POST",
		            	data: e.target.result,
		            	cache: false,
		            	contentType: "application/octet-stream",
		            	processData: false                	
		            }).done( function(data) {
		       
		            }).fail( function (jqXHR, textStatus, error) {
		            	alert("Failed to change device image: " + textStatus + " : " + error);  
		            }).complete( function() {
		            	alert("Changed the device image!");
		            	self.hide();
		            });
		        };
		        reader.readAsArrayBuffer(self.file()[0].fileObj);
			}
			else {
				alert("Please select an image file to upload!");
			}
		}
		
	};
	
	/** Prompts the user to select a new image for the device 
	 */
	self.selectImage = function() {
		$("#fileUploadInput").click();	
	}
	
}