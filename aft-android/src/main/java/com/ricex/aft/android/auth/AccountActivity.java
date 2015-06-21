package com.ricex.aft.android.auth;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ricex.aft.android.R;
import com.ricex.aft.android.requester.AFTResponse;
import com.ricex.aft.android.requester.RequesterCallback;
import com.ricex.aft.android.requester.UserRequester;

/** The Activity used for Logging in
 * 
 * @author Mitchell Caisse
 *
 */
public class AccountActivity extends AccountAuthenticatorActivity {

	/** Argument in the intent for the Account Type to create */
	public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
	
	/** Argument in the intent for the name of the account to update */
	public static final String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
	
	/** Argument in the intent for the Authorization type */
	public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
	
	/** Argument in the intent for whether the user is adding a new account or not */
	public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_NEW_ACCOUNT";
	
	/** Argument in the intent for the account authenticator response */
	public static final String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "ACCOUNT_AUTHENTICATOR_RESPONSE";
	
	private static final String LOG_TAG = "PushFile-Login";
	
	/** The User requester to use to verify the account */
	private final UserRequester userRequester;
	
	/** The username text box on the view */
	private EditText textUsername;
	
	/** The password text box on the view */
	private EditText textPassword;
	
	/** The Login button on the view */
	private Button butLogin;
	
	public AccountActivity() {
		userRequester = new UserRequester(this);
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
		
		//check if an account name was passed in
		String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
		if (!TextUtils.isEmpty(accountName)) {
			textUsername.setText(accountName);
		}
		
	}
	
	/** Handles the user pressing the log in button
	 */
	protected void login() {
		//fetch the values that the user has entered from the view
		final String username = textUsername.getText().toString();
		final String password = textPassword.getText().toString();
		
		//show a message telling the user what they have entered
		Toast.makeText(getApplicationContext(), "Entered: " + username + "|" + password, Toast.LENGTH_LONG).show();
		
		userRequester.fetchAuthenticationTokenAsync(username, password, new RequesterCallback<String>() {

			public void onSuccess(String results) {
				finishLogin(username, results);			
			}
				
			public void onFailure(AFTResponse<String> resp) {
				
			}
			
			public void onError(Exception e) {	
				//error, couldn't 
				Toast.makeText(getApplicationContext(), "Invalid username / password", Toast.LENGTH_LONG).show();
			}
			
		});
		
		//remove the password entered by the user after login attempt
		textPassword.getText().clear();
		
	}
	
	protected void finishLogin(String username, String authToken) {
		
	}
	
}
