
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
		var serverUrl = "http://fourfivefire.com:8080/aft-servlet/manager/request/all";		
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
		self.parent.modifyRequestViewModel.request(request);
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
		//save the request.
	};
	
	// populate the devices list
	getAllDevices(function(data) {
		//remove the old elements from the list
		self.devices.removeAll();		
		//add the elements from the web service to the list
		$.each(data, function(index, value) {
			alert("Adding device to list: " + value.deviceName);
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
	
	
}
