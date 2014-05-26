
var requestDirectoryOptions = [
    "DOCUMENTS",
    "DOWNLOADS",
    "MOVIES",
    "MUSIC",
    "PICTURES",
    "ROOT"
];
                               

/** Creates a new File Object from a data object returned from the web service
 * 
 * @param data 
 */

function File(data) {
	
	var self = this;
	
	self.fileId = ko.observable(data.fileId);
	self.fileName = ko.observable(data.fileName);
}


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

/** Creates a new Request Object from a data object returned from the web service
 * 
 */

function Request(data) {
	
	var self = this;
	
	self.requestId = ko.observable(data.requestId);
	self.requestName = ko.observable(data.requestName);
	self.requestFile = ko.observable(new File(data.requestFile));
	self.requestFileLocation = ko.observable(data.requestFileLocation);
	self.requestDirectory = ko.observable(data.requestDirectory);
	self.requestStatus = ko.observable(data.requestStatus);
	self.requestStatusMessage = ko.observable(data.requestStatusMessage);
	self.requestUpdated = ko.observable(data.requestUpdated);
	self.requestDevice = ko.observable(new Device(data.requestDevice));
	
	/** Computed to show the request update as a string rather than a long */
	self.requestUpdatedString = ko.computed(function() {
		return parseDate(self.requestUpdated());
	});
		
};