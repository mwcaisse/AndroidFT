
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
 * 	@param requestQuery the query used to fetch the requests from the server, without the server root
 * 						Defaults to api/request/mine
 * 
 */
function PFRequestListViewModel(requestQuery) {

	var self = this;
	
	if (!requestQuery) {
		requestQuery = "api/request/mine";
	}
	
	/** The list of requests to show */
	self.requests = ko.observableArray([]);
	
	/** Fetches the requests from the server */
	self.fetchRequests = function() {
		$.getJSON(requestRoot + requestQuery, function(data) {	
			//remove the old requests, and add the new requests
			self.requests.removeAll();			
			$.each(data, function(index, value) {
				self.requests.push(new RequestModel(value));
			});			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Error fefetching requests! " + textStatus + " : " + error);
		});		
	};
	
	
	//fetch the requests
	self.fetchRequests();

	
}
