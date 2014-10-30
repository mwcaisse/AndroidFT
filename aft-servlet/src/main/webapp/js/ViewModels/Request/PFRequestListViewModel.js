
/** The model for a Request */
function RequestModel(data) {	
	var self = this;	
	if (data) {		
		/** The id of this request */
		self.requestId = data.requestId;
		
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
		$.getJSON("http://" + host + "/aft-servlet/manager/request/all", function(data) {	
			//remove the old requests, and add the new requests
			self.Requests.removeAll();			
			$.each(data, function(index, value) {
				self.Requests.push(new RequestModel(value));
			});			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Error fethcing requests! " + textStatus + " : " + error);
		});		
	};
	
	
	//fetch the requests
	self.fetchRequests();
	
	/** Views the specified request
	 * 
	 * @param request The request to view, if null, uses calling context
	 */
	self.viewRequest = function(request) {	
		if (!request) {
			request = this;
		}
		//remove any observables
		request = ko.toJS(request);
		
		//navigate to the view page
		window.location.href = "./createRequest.html?requestId=" + request.requestId;
		
	};
	
}
