
/** The basic model for a User. Just the data fields, no validation
 * 
 * @param data The User object from the server, to create the Model for
 */
function BasicUserModel(data) {
	
	var self = this;
	
	/** The username of the user */
	self.username = ko.observable("");
	
	/** The name of the suer */
	self.name = ko.observable("");
	
	if (data) {
		self.username(data.username);
		self.name(data.name);
	}
	
}

/** The Basic model for a request, Just the data fields, no validation
 * 
 */
function BasicRequestModel(data) {	
	var self = this;	
	
	/** The id of this request */
	self.id = ko.observable(-1);
	
	/** The name of this request */
	self.requestName = ko.observable("");
	
	/** The status of this request */
	self.requestStatus = ko.observable("");
	
	/** The date that this request was updated */
	self.requestUpdated = ko.observable("");	
	/** The name of the device this request belongs to */
	self.requestDeviceName = ko.observable("");
	
	/** The text string for the date */
	self.requestUpdatedText = ko.computed( function() {
		return parseDate(self.requestUpdated());
	});
	
	if (data) {
		self.id(data.id);		
		self.requestName(data.requestName);		
		self.requestStatus(data.requestStatus);		
		self.requestUpdated(data.requestUpdated);			
		self.requestDeviceName(data.requestDevice.deviceName);	
	}
}

/** The Basic model for a Device, Just the data fields, no validation
 * 
 */
function BasicDeviceModel(data) {
	var self = this;
	
	/** The id of the device */
	self.id = ko.observable("");
	
	/** The name of the device */
	self.deviceName = ko.observable("");
	
	/** The uid of the device */
	self.deviceUid = ko.observable("");
	
	/** The registration id of the device */
	self.deviceRegistrationId = ko.observable("");
	
	/** The owner of the device */
	self.deviceOwner = ko.observable(new BasicUserModel());
	
	/** Whether or not the device is active */
	self.deviceActive = ko.observable(true);
	
	/** The image url for the device, compued from the device id */
	self.deviceImageUrl = ko.computed(function() {
		return requestRoot + "api/device/image/" + self.id();
	});
	
	if (data) {
		self.id(data.id);
		self.deviceName(data.deviceName);
		self.deviceUid(data.deviceUid);	
		self.deviceRegistrationId(data.deviceRegistrationId);
		self.deviceOwner(new BasicUserModel(data.deviceOwner));
	}		
	
}