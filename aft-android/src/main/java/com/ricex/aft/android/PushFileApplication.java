package com.ricex.aft.android;

import android.app.Application;
import android.content.Context;

/** Static class for storing the context for the Application
 * 
 * @author Mitchell Caisse
 *
 */

public class PushFileApplication extends Application {

	private static Context context;
	
	/** Sets the context on create
	 * 
	 */
	public void onCreate() {
		super.onCreate();
		PushFileApplication.context = getApplicationContext();
	}
	
	/** Returns the context for this application
	 * 
	 * @return the context
	 */
	public static Context getAppContext() {
		return context;
	}

}
