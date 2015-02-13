
/** The model for a device */
function DeviceModel(data) {
	var self = this;
	
	/** The id of the device */
	self.id = ko.observable(data.id);
	
	/** The name of the device */
	self.deviceName = ko.observable(data.deviceName);
	
	/** The uid of the device */
	self.deviceUid = ko.observable(data.deviceUid);
	/** The image url for the device, compued from the device id */
	self.deviceImageUrl = ko.computed(function() {
		return requestRoot + "api/device/image/" + self.id();
	});
	
	/** Whether or not the device is active */
	self.deviceActive = ko.observable(true);
}

/** The model for a Request */
function RequestModel(data) {	
	var self = this;	
	if (data) {		
		/** The id of this request */
		self.id = data.id;
		
		/** The name of this request */
		self.requestName = data.requestName;
		
		/** The status of this request */
		self.requestStatus = data.requestStatus;
		
		/** The date that this request was updated */
		self.requestUpdated = data.requestUpdated;
		
		self.requestUpdatedText = ko.computed( function() {
			return parseDate(self.requestUpdated);
		});
		
		/** The name of the device this request belongs to */
		self.requestDeviceName = data.requestDevice.deviceName;		
	}
	
}

/** The view model for the device view */

function PFDeviceViewModel() {

	var self = this;
	
	/** The list of devices to display */
	self.devices = ko.observableArray([]);
	
	/** The devices in grid format */
	self.devicesGrid = ko.computed(function() {
		var data = self.devices();
		var grid = [];
		var cols = 5;
		var rows = Math.ceil(data.length / cols);
		for (var i = 0; i < rows; i++) {
			grid.push(data.slice(i * cols, i * cols + cols));
		}
		return grid;
	});
	
	
	self.fetchDevices = function() {
		
		$.getJSON(requestRoot + "api/device/mine", function (data) {
			
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