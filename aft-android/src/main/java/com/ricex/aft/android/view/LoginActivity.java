package com.ricex.aft.android.view;

import com.ricex.aft.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/** The Activity used for Logging in
 * 
 * @author Mitchell Caisse
 *
 */
public class LoginActivity extends Activity {

	private static final String LOG_TAG = "PushFile-Login";
	
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

	}
	
}
