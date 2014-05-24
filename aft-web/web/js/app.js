$(document).ready(function() {
	
	/** Sample device */
	var device = {
		deviceId: 5,
		deviceUid: "AEF624",
		deviceName: "HTC Droid DNA",
		deviceRegistrationId: "GCMREGISTRATIONID"			
	};
	
	/** Array of sample devices */
	var devices = [
	    new Device(device),
	    new Device(device),
	    new Device(device)	               
	];
	
	var request = {
		requestId: 6,
		requestName: "My Request",
		requestFile: {
			fileName: "HelloWorld.png"
		},
		requestFileLocation: "/srv/http/androift/",
		requestDirectory: "ROOT",
		requestStatus: "COMPLETED",
		requestStatusMessage: "",
		requestUpdated: 1400967918,
		requestDevice: {
			deviceName: "HTC Droid DNA"
		}			
	};
	
	var requests = [
        new Request(request),
        new Request(request),
        new Request(request)
    ];
	
	var deviceTableViewModel = new DeviceTableViewModel(devices);
	var requestTableViewModel = new RequestTableViewModel(requests);
	
	ko.applyBindings(deviceTableViewModel, $('#deviceListDiv')[0]);
	ko.applyBindings(requestTableViewModel, $('#requestListDiv')[0]);
	
});