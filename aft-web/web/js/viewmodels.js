
/** The view model for the device table
 * 
 * 	@param data An array of Device models
 */

function DeviceTableViewModel(data) {
	
	var self = this;
	
	self.devices = ko.observableArray(data);
	
};


/** The view model for the request table
 * 
 * 	@param data An array of Request models
 * 
 */

function RequestTableViewModel(data) {
	
	var self = this;
	
	self.requests = ko.observableArray(data);
	
};