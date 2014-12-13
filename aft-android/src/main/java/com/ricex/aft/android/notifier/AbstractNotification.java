package com.ricex.aft.android.notifier;

import android.R;

public abstract class AbstractNotification implements AFTNotification {

	/** The id of this notification */
	protected int id;
	
	/** The title of this notification */
	protected String title;
	
	/** The body text of this notification */
	protected String text;
	
	/** The type of this notification */
	protected NotificationType type;
	
	/** The small icon of this notification */
	protected int smallIcon;
	
	public AbstractNotification(NotificationType type) {
		this.type = type;
		title = "";
		text = "";
		smallIcon = R.drawable.stat_notify_more;
	
	}
	
	/** Creates a new Abstract Notification
	 * 
	 * @param title The title
	 * @param text The text / body
	 * @param type The type
	 * @param smallIcon The icon
	 */
	public AbstractNotification(String title, String text,	NotificationType type, int smallIcon) {
		this.title = title;
		this.text = text;
		this.type = type;
		this.smallIcon = smallIcon;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public int getSmallIcon() {
		return smallIcon;
	}

	@Override
	public NotificationType getType() {
		return type;
	}


}
