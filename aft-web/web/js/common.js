
//var host = "localhost:8080";
var host = "fourfivefire.com:8080";

/** A set of common / helper functions
 * 
 */

/** Parses a date in the form of milliseconds since epoch  into a locale date/time string representing the date
 * 
 * @param dateLong The date in milliseconds since epoch
 * @return Locale date/time string
 */

function parseDate(dateLong) {
	var date = new Date(dateLong);
	return date.toLocaleString();
};

/** Function to fetch all devices from the web service
 * 
 * @param success The function to call upon success
 * @param fail The function to call upon failure
 */

function getAllDevices(success, fail) {	
	var serverUrl = "http://" + host + "/aft-servlet/manager/device/all";		
	$.getJSON(serverUrl, success).fail(fail);
};



