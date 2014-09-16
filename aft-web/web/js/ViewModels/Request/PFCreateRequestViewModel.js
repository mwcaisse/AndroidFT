
/** The model for the request */
function RequestModel(data) {
	
	var self = this;
	
	//initialize the fields
	self.requestId = ko.observable(-1);
	self.requestName = ko.observable("");
	self.requestFileLocation = ko.observable("");
	self.requestDirectory = ko.observable("");
	self.requestStatus = ko.observable("NEW");
	self.requestStatusMessage = ko.observable("");
	self.requestDevice = ko.observable(new DeviceModel());
	self.requestFiles = ko.observableArray([]);
	self.requestUpdated = ko.observable(new Date().getTime());
	self.requestDeviceKey = ko.observable("");
	
	//if there is data initialize the data..
	if (data) {
		self.requestId(data.requestId);
		self.requestName(data.requestName);
		self.requestDirectory(data.requestDirectory);
		self.requestStatus(data.requestStatus);
		self.requestStatusMessage(data.requestStatusMessage);
		self.requestDevice(new DeviceModel(data.requestDevice));
		self.requestUpdated = ko.observable(data.requestUpdated);
		
		//add the files
		$.each(data.requestFiles, function( index, value) {
			self.requestFiles.push(new FileModel(value));
		});
	}
	
	/** Whether or not this request is new */
	self.isNew = ko.computed(function() {
		return self.requestId() < 0; //if the id is less than zero, it is new
	});
	
	/** The template for the status field */
	self.statusTemplate = ko.computed(function() {
		if (self.isNew()) {
			//if it is new status is non-modifiable
			return "request-status-non-modifiable";
		}
		else {
			//otherwise return modifiable
			return "request-status-modifiable";
		}
	});
	
	/** The template for the device field */
	self.deviceTemplate = ko.computed(function() {
		if (self.isNew()) {
			//if it is new device is modifiable
			return "request-device-modifiable";
		}
		else {
			//if device isn't new, it is not modifiable
			return "request-device-non-modifiable";
		}
	});
	
	/** The date string of the Updated Date
	 *
	 */
	self.updateDateString = ko.computed(function() {
		return new Date(self.requestUpdated()).toLocaleString();
	});	
	
	/** The error for the request */
	self.requestError = ko.observable(false);
	self.requestErrorText = ko.observable("");
	
	/** The error for the request files */
	self.requestFilesError = ko.observable(false);
	self.requestFilesErrorText = ko.observable("");
	
	/** Determines if the request is valid
	 * 	@return True if the request is valid, false otherwise
	 */
	self.validate = function() {
		//check if the request has a name
		if (self.requestName().length <= 0) {
			self.requestError(true);
			self.requestErrorText("A request must have a name!");
		}
		else {
			self.requestError(false);
			self.requestErrorText("");
		}
		
		//check if the request has files
		if (self.requestFiles().length < 1) {
			self.requestFilesError(true);
			self.requestFilesErrorText("A request must have atleast one file!");
		}
		else {
			self.requestFilesError(false);
			self.requestFilesErrorText("");
		}
		
		//if both errors are false, then we are valid, otherwise invalid
		return !self.requestFilesError() && !self.requestError();
	};
	
	//subscribe to the name and request files to validate the request
	self.requestName.subscribe(self.validate);
	self.requestFiles.subscribe(self.validate);
	
	/** Removes the specified file
	 * 	@param The file to remove
	 */
	self.removeRequestFile = function(file) {
		self.requestFiles.remove(file);
	};
	
	
	//validate the request
	self.validate();	

	
}

/** The model for the device */
function DeviceModel(data) {
	
	var self = this;
	
	self.deviceId = -1;
	self.deviceName = ko.observable("");
	self.deviceKey = ko.observable("");	
	
	if (data) {
		self.deviceId = data.deviceId;
		self.deviceName(data.deviceName);
	}
	
}

/** The model for a file */
function FileModel(data) {
	var self = this;
	
	self.fileId = data.fileId;
	self.fileName = data.fileName;
	
	/** The url to download the file
	 */
	self.downloadUrl = ko.computed(function() {
		if (self.fileId < 0) {
			return "#"; //no id, can't download
		}
		return "http://" + host + "/aft-servlet/manager/file/download/" + self.fileId;
	});
	
}

/** View model for creating a request */
function PFCreateRequestViewModel(fileUploadModal, requestId) {
	
	var self = this;
	
	/** The request */
	self.request = ko.observable(new RequestModel());
	
	//check if a request id was passed in, if so set it
	if (requestId >= 0) {
		self.request().requestId(requestId);
	}
	
	/** The list of request statuses */
	self.requestStatuses = ko.observableArray([]);
	
	/** The list of request directories */
	self.requestDirectories = ko.observableArray([]);
	
	/** The list of devices */
	self.devices = ko.observableArray([new DeviceModel()]);
	
	/** The file upload modal */
	self.fileUploadModal = fileUploadModal;
	
	/** Fetches the available list of request statuses */
	self.fetchRequestStatuses = function() {
		$.getJSON("http://" + host + "/aft-servlet/manager/request/statuses", function (data) {
			self.requestStatuses(data);
		}).fail( function (jqXHR, textStatus, error) {
			alert("Failed to fetch the request statuses " + textStatus + " : " + error);
		});
	};
	
	/** Fetches the list of all request directories */
	self.fetchRequestDirectories = function() {
		$.getJSON("http://" + host + "/aft-servlet/manager/request/directories", function (data) {
			self.requestDirectories(data);
		}).fail( function (jqXHR, textStatus, error) {
			alert("Failed to fetch the request directories " + textStatus + " : " + error);
		});
	};
	
	/** Fetches the list of all devices */
	self.fetchDevices = function() {
		$.getJSON("http://" + host + "/aft-servlet/manager/device/all", function (data) {
			self.devices.removeAll();
			
			$.each(data, function(index, value) {				
				self.devices.push(new DeviceModel(value));
			});
		}).fail( function (jqXHR, textStatus, error) {
			alert("Failed to fetch the devices " + textStatus + " : " + error);
		});
	};
	
	/** Fetches the request */
	self.fetchRequest = function() {
		//if the request is new, clear out the current values, otherwise fetch a new request
		if (self.request().isNew()) {
			self.request(new RequestModel());
		}
		else {
			$.getJSON("http://" + host + "/aft-servlet/manager/request/" + self.request().requestId(), function (data) {
				self.request(new RequestModel(data));
			}).fail( function (jqXHR, textStatus, error) {
				alert("Failed to fetch the request: " + textStatus + " : " + error);
			});
		}
		
		
	};
	
	/** Fetches all required data from the server */
	self.fetchData = function() {
		self.fetchRequestStatuses();
		self.fetchRequestDirectories();
		self.fetchDevices();
		self.fetchRequest();
	};

	
	/** Saves the request
	 * 
	 */
	self.saveRequest = function() {
		if (self.request().validate()) {			
			//The url to make the request to
			var url = "";
			//the type of request to make
			var type= "POST";
			
			//set the url and type depending on whether it is a creation or update
			if (self.request().isNew()) {
				url = "http://" + host + "/aft-servlet/manager/request/create";
				type =  "POST";
			}
			else {
				url = "http://" + host + "/aft-servlet/manager/request/update";
				type =  "PUT";
			}
			//update the device key in the device
			self.request().requestDevice().deviceKey(self.request().requestDeviceKey());
			$.ajax( {
            	url: url,
            	type: type,
            	data: ko.toJSON(self.request()),
            	contentType: "application/json"             	
            }).done( function(data) {
            	//check if the request failed to save
            	if (data.value < 0) {
            		alert("Request was invalid. Could not save");
            	}
            	else {
	            	//update the request id
	            	self.request().requestId(data.value);
	            	
	            	//update the tab title
	            	self.updateTabTitle();   
            	}
            }).fail( function (jqXHR, textStatus, error) {
            	alert("Failed to save the request! : " + textStatus + " : " + error);
            });
		}
		else {
			alert("Please fix any issues with the request before saving");
		}
	};
	
	/** Discards / refreshes any data
	 * 
	 */
	self.discard = function() {
		self.fetchData();
	};
	
	/** The callback for uploading request files */
	self.requestFileCallback = function(newFiles) {
		$.each(newFiles, function(index, value) {
			self.request().requestFiles.push(new FileModel({
				fileId: value.fileId,
				fileName: value.name
			}));
		});
	};
	
	/** Shows the modal to add a request file 
	 * 
	 */
	self.addRequestFile = function() {
		showFileUploadModal(self.requestFileCallback);
	};
	
	/** Removes the specified file, or the calling context if no param is passed
	 * 	@param The file to remove, optional
	 */
	self.removeRequestFile = function(file) {
		if (file == null) {
			file = this;
		}
		self.request().removeRequestFile(file);
	};
	
	/** Updates the title of the tab to either Create / View depending on
	 * 		the id of the request
	 */
	self.updateTabTitle = function() {
		if (self.request().requestId() < 0) {
			$("#liViewRequest").text("Create Request");
			$("#liCreateRequest").addClass("hidden");
		}
		else {
			$("#liViewRequest").text("Modify Request");
			$("#liCreateRequest").removeClass("hidden");
		}
	};
	
	//subscribe to the reqest, whenever it changes, update the tab title
	self.request.subscribe(self.updateTabTitle);
	
	//update the tab title
	self.updateTabTitle();
	
	//fetch the data
	self.fetchData();
	
}



