
/** The User Model for a user
 * 
 */
function UserModel() {
	var self = this;
	
	/** The user's user name */
	self.username = ko.observable("");
	
	/** The user's password */
	self.password = ko.observable("");
	
	/** The users confirmed password */
	self.passwordConfirm = ko.observable("");
	
	/** The users email address */
	self.emailAddress = ko.observable("");
	
	/** The users confirmed email address */
	self.emailAddressConfirm = ko.observable("");
	
	/** Whether or not there is an error with user name */
	self.usernameError = ko.observable(false);
	
	/** The username error text */
	self.usernameErrorText = ko.observable("That username is currently taken");
	
	/** Whether or not there is a password error */
	self.passwordError = ko.observable(false);
	
	/** The password error text */
	self.passwordErrorText = ko.observable("Passwords do not match!");
	
	/** Whether or not there is an email address error */
	self.emailAddressError = ko.observable(false);
	
	/** The email address error text */
	self.emailAddressErrorText = ko.observable("Email addresses do not match!");
	
	/** Verifies that the username is available */
	self.verifyUserNameAvailable = function() {
		$.getJSON(requestRoot + "api/user/isAvailable?username=" + ko.toJS(self.username), function(data) {
			if (data.value) {
				self.usernameError(false);
			}
			else {
				self.usernameError(true);
				self.usernameErrorText("This username is not available.");
			}
		});
	};
	
	/** Subscribes to the username observable, and checks if it is valid */
	self.username.subscribe(function (newValue) {
		self.verifyUserNameAvailable();
	});

};

/** Create a new Register View Model
 * 
 */
function RegisterViewModel() {
	var self = this;
	
	/** The user model */
	self.user = new UserModel();
	
	/** Handles when the user presses cancel */
	self.cancel = function() {
		window.location.href = requestRoot + "login";
	};
	
	/** Handles when the user presses the register button */
	self.register = function() {
		alert("You clicked register!");
	}
}
