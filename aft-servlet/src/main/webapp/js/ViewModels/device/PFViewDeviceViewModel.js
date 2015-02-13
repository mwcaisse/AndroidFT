

/** The View model for the device view
 *  
 *  @param deviceId the ID of the device that will be viewed
 */
function DeviceViewModel(deviceUID) {
	
	var self = this;
	
	/** The id of the device to view */
	self.deviceUID = deviceUID;
	
	/** The device to be displayed */
	self.device = ko.observable(new BasicDeviceModel());
	
	/** The list of requests for the device */
	self.requests = ko.observableArray([]);
	
	/** Fetches the data for this view */
	self.fetchData = function() {
		self.fetchDevice();
	};
	
	/** Fetches the device from the server */
	self.fetchDevice = function() {
		$.getJSON(requestRoot + "api/device/" + self.deviceUID, function (data) {
			self.device(new BasicDeviceModel(data));
			//fetch the requests after we fetch the device
			self.fetchRequests();
		}).fail( function(jqXHR, textStatus, error) {
			alert("Faield to fetch the requests from the servlet! " + textStatus + " : " + error);
		});
	};
	
	/** Fetches the requests from the server */
	self.fetchRequests = function() {
		$.getJSON(requestRoot + "api/request/all/" + deviceUID, function (data) {
			
			//remove all of the old devices
			self.requests.removeAll();
			
			$.each( data, function(index, value) {
				self.requests.push(new BasicRequestModel(value));
			});
			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Failed to fetch the requests from the servlet! " + textStatus + " : " + error);
		});
	};
	
	// fetch the device + requests
	self.fetchData();
	
	
}
