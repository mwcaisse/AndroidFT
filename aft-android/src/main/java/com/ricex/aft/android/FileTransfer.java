package com.ricex.aft.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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

}
