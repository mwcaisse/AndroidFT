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
import com.ricex.aft.android.request.AbstractRequestCallback;
import com.ricex.aft.android.request.user.AuthenticationTokenRequest;
import com.ricex.aft.android.request.user.LoginPasswordRequest;
import com.ricex.aft.common.response.BooleanResponse;

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
	
	/** The username text box on the view */
	private EditText textUsername;
	
	/** The password text box on the view */
	private EditText textPassword;
	
	/** The Login button on the view */
	private Button butLogin;
	
	public AccountActivity() {

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
		
		new LoginPasswordRequest(username, password).executeAsync(new AbstractRequestCallback<BooleanResponse>() {
			public void onSuccess(BooleanResponse results) {
				if (results.getValue()) {					
					new AuthenticationTokenRequest().executeAsync(new AbstractRequestCallback<String>() {
						public void onSuccess(String authenticationToken) {
							finishLogin(username, authenticationToken);	
						}
						public void onError(Exception e) {
							Toast.makeText(getApplicationContext(), "Couldn't get authentication Token!", Toast.LENGTH_LONG).show();
						}
					});					
				}
			}	
			public void onError(Exception e) {
				Toast.makeText(getApplicationContext(), "Invalid username / password", Toast.LENGTH_LONG).show();
			}
		});		
		
	
		
		//remove the password entered by the user after login attempt
		textPassword.getText().clear();
		
	}
	
	protected void finishLogin(String username, String authToken) {
		
	}
	
}
