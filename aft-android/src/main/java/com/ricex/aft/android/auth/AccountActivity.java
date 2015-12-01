package com.ricex.aft.android.auth;

import com.ricex.aft.android.request.AbstractRequestCallback;
import com.ricex.aft.android.request.exception.InvalidCredentialsException;
import com.ricex.aft.android.request.user.AuthenticationTokenRequest;
import com.ricex.aft.android.request.user.LoginPasswordRequest;
import com.ricex.aft.common.response.BooleanResponse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** The Activity used for Logging in
 * 
 * @author Mitchell Caisse
 *
 */
public class AccountActivity extends Activity {

	/** The key for passing in an account name to the activity */
	public static final String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
	
	/** The key for retrieving the account name result */
	public static final String RES_ACCOUNT_NAME = "com.ricex.aft.android.auth.account_name";
	
	/** The key for retrieving the auth token result */
	public static final String RES_AUTH_TOKEN = "com.ricex.aft.android.auth.auth_token";
	
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
		
		TextInputLayout t = new TextInputLayout(this);
		
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
		
		new LoginPasswordRequest(username, password).executeAsync(new AbstractRequestCallback<BooleanResponse>() {
			public void onSuccess(BooleanResponse results) {
				if (results.getValue()) {					
					new AuthenticationTokenRequest().executeAsync(new AbstractRequestCallback<String>() {
						public void onSuccess(String authenticationToken) {
							finishLogin(username, authenticationToken);	
						}
						public void onError(Exception e) {
							Looper.prepare();
							Toast.makeText(getApplicationContext(), "Couldn't get authentication Token!", Toast.LENGTH_LONG).show();
							Looper.loop();
						}
					});					
				}
			}	
			public void onError(Exception e) {
				Looper.prepare();
				if (e instanceof InvalidCredentialsException) {
					Toast.makeText(getApplicationContext(), "Invalid username / password", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getApplicationContext(), "Error communicating with the server. Please try again later.", Toast.LENGTH_LONG).show();
				}
				
				Log.w(LOG_TAG, "Error Loggining to server: " + e.getMessage(), e);				
				
				Looper.loop();
			}
		});		
		
	
		
		//remove the password entered by the user after login attempt
		textPassword.getText().clear();
		
	}
	
	/** User has finished logging in. Add the username + authtoken as the result of the activity
	 * 	and finish.
	 * 
	 * @param username The username the user provided
	 * @param authToken The authtoken returned from the server upon successful login
	 */
	protected void finishLogin(String username, String authToken) {		
		Intent result = new Intent();
		
		result.putExtra(RES_ACCOUNT_NAME, username);
		result.putExtra(RES_AUTH_TOKEN, authToken);
		
		setResult(RESULT_OK, result);
		
		finish();
	}
	
}
