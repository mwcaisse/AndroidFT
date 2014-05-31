/** The view model for the device table
 * 
 * 	@param data An array of Device models
 */

function DeviceTableViewModel(parent, data) {
	
	var self = this;
	
	self.parent = parent;
	self.devices = ko.observableArray(data);
	
	self.fetchFromServer = function() {	
		getAllDevices(function(data, statusMessage, jqXHR) {
			self.parseServerResponse(data);			
		},function(jqXHR, textStatus, error) {
			alert("Failed to fetch all devices: " + textStatus + " : " + error);
		});
	};
	
	self.parseServerResponse = function(data) {
		//remove the old elements from the list
		self.devices.removeAll();
		
		//add the elements from the web service to the list
		$.each(data, function(index, value) {
			self.devices.push(new Device(value));
		});	
	};
	
	//fetch the elements
	self.fetchFromServer();
	
};


/** The view model for the request table
 * 
 * 	@param data An array of Request models
 * 
 */

function RequestTableViewModel(parent, data) {
	
	var self = this;
	
	self.parent = parent;
	self.requests = ko.observableArray(data);
	
	self.fetchFromServer = function() {
		var serverUrl = "http://" + host + "/aft-servlet/manager/request/all";		
		$.getJSON(serverUrl, function(data, statusMessage, jqXHR) {
			self.parseServerResponse(data);			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Failed to fetch all requests: " + textStatus + " : " + error);
		});
	};
	
	self.parseServerResponse = function(data) {
		//remove the old elements from the list
		self.requests.removeAll();
		
		//add the elements from the web service to the list
		$.each(data, function(index, value) {
			self.requests.push(new Request(value));
		});	
	};
	
	self.viewRequest = function(request) {
		//alert("View Request!: " + request.requestId());
		//clone the request first
		self.parent.modifyRequestViewModel.request(new Request(ko.toJS(request)));
	};
	
	//fetch the elements
	self.fetchFromServer();
	
};


/** The view for modifying / viewing a request
 * 
 */

function ModifyRequestViewModel(parent, data) {
	
	var self = this;
	
	self.parent = parent;
	
	/** The request object this view model is displaying */
	self.request = ko.observable(data);
	
	/** List of all devices on the web service */
	self.devices = ko.observableArray([]);
	
	/** List of all request directory options */
	self.requestDirectoryOptions = requestDirectoryOptions;	
	
	self.cancel = function() {
		self.request(null);
	};
	
	self.save = function() {
	
		console.log("Saving request: " + ko.toJSON(self.request));
		
		var serverUrl = "http://" + host + "/aft-servlet/manager/request/update";		
		
		$.ajax( {
			type: "PUT",
			dataType: "json",
			url: serverUrl,
			data: ko.toJSON(self.request),
			contentType: "application/json"				
		}).done(function() {
			console.log("Successfully saved the request");			
			//update the requests in the table
			self.parent.requestTableViewModel.fetchFromServer();
		}).fail(function(jqXHR, statusMessage, error) {
			alert("Failed to save the request");
			console.log("Failed to save the request, " + statusMessage + " : " + error);
		});
	};
	
	self.isModifiable = function() {
		var res = self.request().requestStatus() != "COMPLETED" && self.request().requestStatus() != "IN_PROGRESS";
		console.log("isModifiable returned: " + res);		
		return res;
	};
	
	// populate the devices list
	getAllDevices(function(data) {
		//remove the old elements from the list
		self.devices.removeAll();		
		//add the elements from the web service to the list
		$.each(data, function(index, value) {
			self.devices.push(new Device(value));
		});	
	}, function(jqXHR, textStatus, error) {
		alert("Failed to fetch all devices: " + textStatus + " : " + error);
	});
	
};

/** The view for creating a request
 * 
 */

function CreateRequestViewModel(parent) {
	
	var self = this;
	
	self.parent = parent;
	
	/** The request object this view model is displaying */
	self.request = ko.observable(new Request());
	
	/** List of all devices on the web service */
	self.devices = ko.observableArray([]);
	
	/** List of all request directory options */
	self.requestDirectoryOptions = requestDirectoryOptions;
	
	/** Whether or not the file has been uploaded */
	self.fileUploaded = ko.observable(false);
	
	/** initialize the form upload */
	
	var options = {
			uploadProgress: function(event, position, total, percentComplete) {
				$("#bar").width(percentComplete + "%");
				$("#percent").html(percentComplete+"%");
			},
			success: function() {
				console.log("File upload completed successfully!");
				self.fileUploaded(true);
			},
			complete: function(response) {
				console.log("File ID: " + JSON.stringify(response));
				var value = response.responseJSON.value;
				console.log("File ID: " + value); 
			},
			error: function() {
				console.log("Failed to upload file");
			}
	};
	
	$("#fileUploadForm").ajaxForm(options);
	
	self.cancel = function() {
		self.request(null);
	};
	
	self.create = function() {
	
		console.log("Creating request: " + ko.toJSON(self.request));
		
		var serverUrl = "http://" + host + "/aft-servlet/manager/request/create";		
		
		$.ajax( {
			type: "POST",
			dataType: "json",
			url: serverUrl,
			data: ko.toJSON(self.request),
			contentType: "application/json"				
		}).done(function() {
			console.log("Successfully created the request");			
			//update the requests in the table
			self.parent.requestTableViewModel.fetchFromServer();
		}).fail(function(jqXHR, statusMessage, error) {
			alert("Failed to create the request");
			console.log("Failed to save the request, " + statusMessage + " : " + error);
		});
	};	
	
	// populate the devices list
	getAllDevices(function(data) {
		//remove the old elements from the list
		self.devices.removeAll();		
		//add the elements from the web service to the list
		$.each(data, function(index, value) {
			console.log("CreateView: Adding Device");
			self.devices.push(new Device(value));
		});	
	}, function(jqXHR, textStatus, error) {
		alert("Failed to fetch all devices: " + textStatus + " : " + error);
	});
	
}


/** Root view model that holds the other view modesl
 * 
 */

function RootViewModel() {
	var self = this;
	
	self.requestTableViewModel = new RequestTableViewModel(self, []);
	self.deviceTableViewModel = new DeviceTableViewModel(self, []);
	self.modifyRequestViewModel = new ModifyRequestViewModel(self, null);
	self.createRequestViewModel = new CreateRequestViewModel(self);
	
	
}
