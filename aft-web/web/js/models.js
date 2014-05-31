
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
	
	self.fileId = ko.observable(0);
	self.fileName = ko.observable("");
	
	if (arguments.length == 1) {
		self.fileId(data.fileId);
		self.fileName(data.fileName);
	}

}


/** Creates a new Device Object from a data object returned from the web service
 * 
 */
function Device(data) {
	
	var self = this;
	
	self.deviceId = ko.observable(0);
	self.deviceUid = ko.observable("");
	self.deviceName = ko.observable("");
	self.deviceRegistrationId = ko.observable("");
	
	if (arguments.length == 1) {	
		self.deviceId(data.deviceId);
		self.deviceUid(data.deviceUid);
		self.deviceName(data.deviceName);
		self.deviceRegistrationId(data.deviceRegistrationId);
	}

};

/** Creates a new Request Object from a data object returned from the web service
 * 
 */

function Request(data) {
	
	var self = this;
	
	self.requestId = ko.observable(0);
	self.requestName = ko.observable("");
	self.requestFile = ko.observable(new File());
	self.requestFileLocation = ko.observable("");
	self.requestDirectory = ko.observable("ROOT");
	self.requestStatus = ko.observable("NEW");
	self.requestStatusMessage = ko.observable("");
	self.requestUpdated = ko.observable(Date.now());
	self.requestDevice = ko.observable(new Device());		
	
	
	if (arguments.length == 1) {
		self.requestId(data.requestId);
		self.requestName(data.requestName);
		self.requestFile(new File(data.requestFile));
		self.requestFileLocation(data.requestFileLocation);
		self.requestDirectory(data.requestDirectory);
		self.requestStatus(data.requestStatus);
		self.requestStatusMessage(data.requestStatusMessage);
		self.requestUpdated(data.requestUpdated);
		self.requestDevice(new Device(data.requestDevice));		
	}
	
	/** Computed to show the request update as a string rather than a long */
	self.requestUpdatedString = ko.computed(function() {
		return parseDate(self.requestUpdated());
	});
		
};