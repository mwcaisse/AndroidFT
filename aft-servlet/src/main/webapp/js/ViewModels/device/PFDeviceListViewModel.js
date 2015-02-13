
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
	
	/** Fetch all of the users devices to show
	 * 
	 */
	self.fetchDevices = function() {
		
		$.getJSON(requestRoot + "api/device/mine", function (data) {
			
			//remove all of the old devices
			self.devices.removeAll();
			
			$.each( data, function(index, value) {
				self.devices.push(new BasicDeviceModel(value));
			});
			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Failed to fetch the devices from the servlet! " + textStatus + " : " + error);
		});
		
	};
	
	//fetch the devices
	self.fetchDevices();
	
}