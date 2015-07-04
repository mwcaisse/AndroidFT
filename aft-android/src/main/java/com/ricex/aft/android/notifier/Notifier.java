package com.ricex.aft.android.notifier;

import java.util.HashMap;
import java.util.Map;

import com.ricex.aft.android.PushFileApplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/** Controls the notifications for this application
 * 
 * @author Mitchell Caisse
 *
 */

public class Notifier {

	/** The singleton instance */
	private static Notifier _instance;
	
	/** Gets the singleton instance
	 * 
	 * @return The singleton instance
	 */
	public static Notifier getInstance() {
		if (_instance == null) {
			_instance = new Notifier();
		}
		return _instance;
	}
	
	/** The notification id counter */
	private int notificationIdCounter;
	
	/** Mapping of all notifications currently being displayed */
	private Map<NotificationType, AFTNotification> notifications;
	
	/** The Notification manager to use to add notifications */
	private NotificationManager notificationManager;
	
	/** Creates a new Notifier */
	private Notifier() {
		notifications = new HashMap<NotificationType, AFTNotification>();
		notificationIdCounter = 0;
		
		notificationManager = (NotificationManager) PushFileApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
	}	
	
	/** Displays the given notification to the user, or updates an already showing notification if one exists
	 * 
	 * @param notification The notification to display
	 * @return True if successful, false otherwise
	 */
	public void displayNotification(AFTNotification notification) {
		AFTNotification existing = notifications.get(notification.getType());
		if (existing == null) {
			notification.setId(getNextNotificationId());
			notifications.put(notification.getType(), notification);
			existing = notification;
		}
		else {
			existing.merge(notification);
		}		
		notificationManager.notify(existing.getId(), buildNotification(existing));		
	}
	
	/** Builds the android notification from the AFT notification
	 * 
	 * @param notification The aft notification
	 * @return The android notification
	 */
	private Notification buildNotification(AFTNotification notification) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(PushFileApplication.getAppContext());
		
		builder.setSmallIcon(notification.getSmallIcon());
		builder.setContentTitle(notification.getTitle());
		builder.setContentText(notification.getText());
		
		return builder.build();
		
	}

	
	/** Returns the next available notification id
	 * 
	 * @return the next notification id
	 */
	protected int getNextNotificationId() {
		return notificationIdCounter++;
	}
}
