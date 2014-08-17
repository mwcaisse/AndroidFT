
//var host = "localhost:8080";
var host = "192.168.1.160:8080";
//var host = "fourfivefire.com:8080";

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



