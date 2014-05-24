
/** Creates a new Device Object from a data object returned from the web service
 * 
 */
function Device(data) {
	
	var self = this;
	
	self.deviceId = ko.observable(data.deviceId);
	self.deviceUid = ko.observable(data.deviceUid);
	self.deviceName = ko.observable(data.deviceName);
	self.deviceRegistrationId = ko.observable(data.deviceRegistrationId);

};

/** Creates a new Request Object from a data object returend from the web service
 * 
 */

function Request(data) {
	
	var self = this;
	
	self.requestId = ko.observable(data.requestId);
	self.requestName = ko.observable(data.requestName);
	self.requestFileName = ko.observable(data.requestFile.fileName);
	self.requestFileLocation = ko.observable(data.requestFileLocation);
	self.requestDirectory = ko.observable(data.requestDirectory);
	self.requestStatus = ko.observable(data.requestStatus);
	self.requestStatusMessage = ko.observable(data.requestStatusMessage);
	self.requestUpdated = ko.observable(data.requestUpdated);
	
	//TODO: possibly link this to an actual device object
	self.requestDeviceName = ko.observable(data.requestDevice.deviceName);
	
};