package com.ricex.aft.android.notifier;

import com.google.android.gms.internal.no;

import android.R;

/** The notification for Request Processed
 * 
 * @author Mitchell Caisse
 *
 */
public class RequestProcessedNotification extends AbstractNotification {

	/** The number of requests that errored during processing */
	private int numError;
	
	/** The number of successful requests processed */
	private int numSuccess;
	
	public RequestProcessedNotification(int numSuccess, int numError) {
		super(NotificationType.REQUEST_PROCESSED);
		this.numSuccess = numSuccess;
		this.numError = numError;
		smallIcon = R.drawable.stat_notify_sync;
		title = "PushFile Request";

	}

	@Override
	public String getText() {
		String message = "";
		if (numSuccess > 0) {
			message += "Successfuly processed " + numSuccess + " requests. \n";
		}
		if (numError > 0) {
			message += "Error occured processing " + numError + " requests.";
		}
		return message;
	}

	/** Merges the given notification into this notification
	 * 		Adds the number of successes and errors to this notification
	 */
	
	@Override
	public boolean merge(AFTNotification notification) {
		if (notification instanceof RequestProcessedNotification) {
			RequestProcessedNotification other = (RequestProcessedNotification) notification;
			this.numError += other.numError;
			this.numSuccess += other.numSuccess;			
			return true;
		}
		return false;
	}

}
