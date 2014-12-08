
/** The request root */
var requestRoot = "/aft-servlet/";

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

/** Retreives the value of the URL parameter with the given name
 * 
 * @param name The name of the URL parameter to fetch
 * @returns The value of the url param, or null if it wasnt found
 */
function getURLParameter(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

/** Makes an ajax call with the given options, automatically injecting the CSRF header
 * 
 * @param options The options object to send
 * @return The jquery future object
 */
function ajaxCSRF(options) {	
	//return the ajax call
	return $.ajax(options);	
}

/** Posts the given data using ajax, to the specified url
 * 
 * @param url The url to post the content to, the requestRoot will be appended to the begining
 * @param data The data to post
 * @return The jquery future to add done, fail, etc methods to
 */

function ajaxCSRFPost(url, data) {
	return ajaxCSRF( {
		url: requestRoot + url,
		type: "POST",
		data: data,
		contentType: "application/json"
	});
}

/** Model for a file to upload
 * 
 */
function FileUploadModel(file) {
	
	var self = this;
	
	/** The id of this file */
	self.fileId = ko.observable(-1);
	
	/** The name of the file */
	self.name = ko.observable(file.name);
	
	/** The js file object */
	self.fileObj = file;	

};

/** A knockout binding for a file upload
 * 
 */

ko.bindingHandlers.fileUpload = {
	
	init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		
		//register for change events on the list of files
		$(element).change(function() {			
			var filesObj = [];

			//create an array of file upload models
			$.each(this.files, function(index, value) {
				filesObj[index] = new FileUploadModel(value);
			});
			
			//if it is an observable, put it into the value
			if (ko.isObservable(valueAccessor())) {
				valueAccessor()(filesObj);				
			}
		});
	},
	update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		var fileModels = ko.unwrap(valueAccessor());
		
		var files = [];
		$.each(fileModels, function(index, value) {
			files[index] = value.fileObj;
		});
		
		//update the files in the selection list
		this.files = files;
	}		
};


/*
ko.bindingHandlers.fileUpload = {
    init: function (element, valueAccessor) {
        $(element).change(function () {
            var file = this.files[0];
            if (ko.isObservable(valueAccessor())) {
                valueAccessor()(file);
            }
        });
    },
    update: function (element, valueAccessor, allBindingsAccessor) {
        var file = ko.utils.unwrapObservable(valueAccessor());
        var bindings = allBindingsAccessor();

        if (bindings.fileBinaryData && ko.isObservable(bindings.fileBinaryData)) {
            if (!file) {
                bindings.fileBinaryData(null);
            } 
            else {
                var reader = new window.FileReader();
                reader.onload = function (e) {
                    bindings.fileBinaryData(e.target.result);
                };
                reader.readAsBinaryString(file);
            }
        }
    }
};
*/



