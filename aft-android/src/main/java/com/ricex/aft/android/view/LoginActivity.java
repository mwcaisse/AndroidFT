package com.ricex.aft.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ricex.aft.android.R;

/** The Activity used for Logging in
 * 
 * @author Mitchell Caisse
 *
 */
public class LoginActivity extends Activity {

	/** Argument in the intent for the Account Type to create */
	public static final String ARG_ACCOUNT_TYPE = "auth-account-type";
	
	/** Argument in the intent for the name of the account to update */
	public static final String ARG_ACCOUNT_NAME = "auth-account-name";
	
	/** Argument in the intent for the Authorization type */
	public static final String ARG_AUTH_TYPE = "auth-auth-type";
	
	/** Argument in the intent for whether the user is adding a new account or not */
	public static final String ARG_IS_ADDING_NEW_ACCOUNT = "auth-add-new-account";
	
	/** Argument in the intent for the account authenticator response */
	public static final String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "auth-account-authenticator-response";
	
	private static final String LOG_TAG = "PushFile-Login";
	
	/** The username text box on the view */
	private EditText textUsername;
	
	/** The password text box on the view */
	private EditText textPassword;
	
	/** The Login button on the view */
	private Button butLogin;
	
	public LoginActivity() {
		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
		Log.i(LOG_TAG, "On Create");	
		
		//populate the view fields		
		textUsername = (EditText) findViewById(R.id.loginUsernameText);
		textPassword = (EditText) findViewById(R.id.loginPasswordText);
		butLogin = (Button) findViewById(R.id.loginButton);		
		
		//Tell the button what to do when it is clicked
		butLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				login();
			}
		});
		
	}
	
	/** Handles the user pressing the log in button
	 */
	protected void login() {
		//fetch the values that the user has entered from the view
		String username = textUsername.getText().toString();
		String password = textPassword.getText().toString();
		
		//show a message telling the user what they have entered
		Toast.makeText(getApplicationContext(), "Entered: " + username + "|" + password, Toast.LENGTH_LONG).show();
		
		//remove the password entered by the user after login attempt
		textPassword.getText().clear();
		
	}
	
}
