package com.ricex.aft.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.common.GooglePlayServicesUtil;


public class FileTransfer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_transfer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_transfer, menu);
		return true;
	}
	
	/** Check if the device currently has google play services installed
	 * 
	 * @return
	 */
	
	public boolean checkGooglePlayServices() {
		int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		return true;
	}

}
