
/** The view model for the device table
 * 
 * 	@param data An array of Device models
 */

function DeviceTableViewModel(data) {
	
	var self = this;
	
	self.devices = ko.observableArray(data);
	
	self.fetchFromServer = function() {
		var serverUrl = "http://fourfivefire.com:8080/aft-servlet/manager/device/all";		
		$.getJSON(serverUrl, function(data, statusMessage, jqXHR) {
			self.parseServerResponse(data);			
		}).fail( function(jqXHR, textStatus, error) {
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

function RequestTableViewModel(data) {
	
	var self = this;
	
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
	
	//fetch the elements
	self.fetchFromServer();
	
};

/** The view model for the RequestView
 * 
 * @param data The ID of the request to display
 */

function RequestViewViewModel(data) {
	var self = this;
	
	self.request = ko.observable(null);
	
	self.fetchFromServer = function() {
		var serverUrl = "http://localhost:8080/aft-servlet/manager/request/all";		
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
	
	//fetch the elements
	self.fetchFromServer();
}
