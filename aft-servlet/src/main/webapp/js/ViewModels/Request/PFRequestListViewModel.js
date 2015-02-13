
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


/** The view model for displaying a list of all requests
 * 
 */
function PFRequestListViewModel() {

	var self = this;
	
	/** The list of requests to show */
	self.Requests = ko.observableArray([]);
	
	/** Fetches the requests from the server */
	self.fetchRequests = function() {
		$.getJSON(requestRoot + "api/request/mine", function(data) {	
			//remove the old requests, and add the new requests
			self.Requests.removeAll();			
			$.each(data, function(index, value) {
				self.Requests.push(new RequestModel(value));
			});			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Error fefetching requests! " + textStatus + " : " + error);
		});		
	};
	
	
	//fetch the requests
	self.fetchRequests();

	
}
