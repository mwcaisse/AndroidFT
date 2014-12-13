package com.ricex.aft.android.notifier;

public interface AFTNotification {

	/** Returns the Id of this notification */
	public int getId();
	
	/** Sets the ID of this notification
	 * 
	 * @param id The new id to set
	 */
	public void setId(int id);
	
	/** Returns the title of this notification */
	public String getTitle();
	
	/** Returns the text of this notification */
	public String getText();
	
	/** Returns the small icon to use for this notification */
	public int getSmallIcon();
	
	/** Returns the type of this notification */
	public NotificationType getType();
	
	/** Merges this notification with the given notification
	 * 
	 * @param notification The notification to merge with
	 * @return True if merge was successful, false otherwise
	 */
	public boolean merge(AFTNotification notification);
}
