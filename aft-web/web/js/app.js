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
		requestUpdated: 1400970851 * 1000,
		requestDevice: {
			deviceName: "HTC Droid DNA"
		}			
	};
	
	var requests = [
        new Request(request),
        new Request(request),
        new Request(request)
    ];
	
	var rootViewModel = new RootViewModel();
	
	ko.applyBindings(rootViewModel.deviceTableViewModel, $('#deviceListDiv')[0]);
	ko.applyBindings(rootViewModel.requestTableViewModel, $('#requestListDiv')[0]);
	ko.applyBindings(rootViewModel.modifyRequestViewModel, $('#modifyRequestDiv')[0]);
	ko.applyBindings(rootViewModel.createRequestViewModel, $('#createRequestDiv')[0]);
	
});