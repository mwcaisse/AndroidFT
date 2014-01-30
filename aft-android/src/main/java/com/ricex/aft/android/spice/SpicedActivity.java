package com.ricex.aft.android.spice;

import android.app.Activity;

import com.octo.android.robospice.SpiceManager;

/** Base class that sts up the spice manager
 * 
 * @author Mitchell
 *
 */

public abstract class SpicedActivity extends Activity {
	
	protected SpiceManager spiceManager = new SpiceManager(JsonSpiceService.class);
	
	@Override
	public void onStart() {
		super.onStart();
		spiceManager.start(this);
	}
	
	@Override
	public void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}

}
