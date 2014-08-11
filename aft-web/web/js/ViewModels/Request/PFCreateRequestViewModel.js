
/** The model for the request */
function RequestModel(data) {
	
	var self = this;
	
	//initialize the fields
	self.requestId = -1;
	self.requestName = ko.observable("");
	self.requestFileLocation = ko.observable("");
	self.requestDirectory = ko.observable("");
	self.requestStatus = ko.observable("NEW");
	self.requestStatusMessage = ko.observable("");
	self.requestDevice = ko.observable(new DeviceModel());
	self.requestFiles = ko.observableArray([]);
	
	//if there is data initialize the data..
	if (data) {
		self.requestId = data.requestId;
		self.requestName(data.requestName);
		self.requestFileDirectory(data.requestDirectory);
		self.requestStatus(data.requestStatus);
		self.requestStatusMessage(data.requestStatusMessage);
		self.requestDevice(new DeviceModel(data.requestDevice));
		
	}
	
}

/** The model for the device */
function DeviceModel(data) {
	
	var self = this;
	
	self.deviceId = -1;
	self.deviceName = ko.observable("");;
	
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
	
}

/** View model for creating a request */
function PFCreateRequestViewModel() {
	
	var self = this;
	
	/** The request */
	self.request = ko.observable(new RequestModel());
	
	/** The list of request statuses */
	self.requestStatuses = ko.observableArray([]);
	
	/** The list of request directories */
	self.requestDirectories = ko.observableArray([]);
	
	/** The list of devices */
	self.devices = ko.observableArray([]);
	
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
	
	/** Fetches all required data from the server */
	self.fetchData = function() {
		self.fetchRequestStatuses();
		self.fetchRequestDirectories();
		self.fetchDevices();
	};
	
	self.fetchData();
	
	
}



