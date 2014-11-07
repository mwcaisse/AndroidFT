
/** The model for a device */
function DeviceModel(data) {
	var self = this;
	
	self.deviceName = ko.observable(data.deviceName);
	self.deviceUid = ko.observable(data.deviceUid);
	
	self.deviceActive = ko.observable(true);
}

/** The view model for the device view */

function PFDeviceViewModel() {

	var self = this;
	
	/** The list of devices to display */
	self.devices = ko.observableArray([]);
	
	
	self.fetchDevices = function() {
		
		$.getJSON(requestRoot + "api/device/all", function (data) {
			
			//remove all of the old devices
			self.devices.removeAll();
			
			$.each( data, function(index, value) {
				self.devices.push(new DeviceModel(value));
			});
			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Failed to fetch the devices from the servlet! " + textStatus + " : " + error);
		});
		
	};
	
	self.fetchDevices();
	
}