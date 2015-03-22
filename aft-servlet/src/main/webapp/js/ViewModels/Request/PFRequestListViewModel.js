/** The view model for displaying a list of all requests
 * 
 * 	@param requestQuery the query used to fetch the requests from the server, without the server root
 * 						Defaults to api/request/mine
 * 
 */
function PFRequestListViewModel(requestQuery) {

	var self = this;
	
	if (!requestQuery) {
		requestQuery = "api/request/mine/all";
	}
	
	/** The list of requests to show */
	self.requests = ko.observableArray([]);
	
	/** Fetches the requests from the server */
	self.fetchRequests = function() {
		$.getJSON(requestRoot + requestQuery, function(data) {	
			//remove the old requests, and add the new requests
			self.requests.removeAll();			
			$.each(data, function(index, value) {
				self.requests.push(new BasicRequestModel(value));
			});			
		}).fail( function(jqXHR, textStatus, error) {
			alert("Error fefetching requests! " + textStatus + " : " + error);
		});		
	};
	
	
	//fetch the requests
	self.fetchRequests();

	
}
