
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
	
	/** Verifies that the passwords the user entered match
	 * 
	 */
	self.verifyPasswordsMatch = function() {
		if (self.password() && self.passwordConfirm()) {
			if (self.password() != self.passwordConfirm()) {
				self.passwordError(true);
				self.passwordErrorText("Passwords do not match!");
			}
			else {
				self.passwordError(false);
			}
		}
	};
	
	/** Verifies that the email addresses the user entered match
	 * 
	 */
	self.verifyEmailAddressesMatch = function() {
		if (self.emailAddress() && self.emailAddressConfirm()) {
			if (self.emailAddress() != self.emailAddressConfirm()) {
				self.emailAddressError(true);
				self.emailAddressErrorText("Email Addresses do not match!");
			}
			else {
				self.emailAddressError(false);
			}
		}
	};
	
	/** Subscribes to the username observable, and checks if it is valid */
	self.username.subscribe(function (newValue) {
		self.verifyUserNameAvailable();
	});
	
	/** Subscribe to the password fields */
	self.password.subscribe(self.verifyPasswordsMatch);
	self.passwordConfirm.subscribe(self.verifyPasswordsMatch);
	
	/** Subscribe to the email fields */
	self.emailAddress.subscribe(self.verifyEmailAddressesMatch);
	self.emailAddressConfirm.subscribe(self.verifyEmailAddressesMatch);
	
	/** Determines if this model is valid or not
	 * 
	 */
	
	self.isValid = function() {
		return !self.usernameError() && !self.passwordError() && !self.emailAddressError();
	}

};

/** Create a new Register View Model
 * 
 */
function RegisterViewModel() {
	var self = this;
	
	/** The user model */
	self.user = ko.observable(new UserModel());
	
	/** Handles when the user presses cancel */
	self.cancel = function() {
		window.location.href = requestRoot + "login";
	};
	
	/** Handles when the user presses the register button */
	self.register = function() {
		if (self.user().isValid()) {
			self.registerUser();
		}
		else {
			alert("Please fix errors before proceding!");
		}
	};
	
	/** Sends the registration request to the server
	 * 
	 */
	self.registerUser = function() {		
		//post the user registration
		ajaxCSRFPost("api/user/register", ko.toJSON(self.user())).done (function (data) {
			window.location.href = requestRoot + "login?registered";
		}).fail( function (jqXHR, textStatus, error) {
			alert("Failed to register: " + error + " : " + jqXHR.responseText);
		});
	};	 
}
