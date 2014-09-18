/**
 * 
 */
package com.ricex.aft.servlet.gcm;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.servlet.notifier.DeviceNotifier;

/** The GCM implementation of Device Notifier that uses Google Cloud Messaging to send notifications to devices
 * 
 * @author Mitchell Caisse
 *
 */
public class GCMDeviceNotifier implements DeviceNotifier {

	/** The url to post GCM notifications / messages to */
	private static final String GCM_REQUEST_URL = "https://android.googleapis.com/gcm/send";
	
	/** The message executor to use to execute sync message's */
	private MessageExecutor messageExecutor;
	
	/**  Creates a new GCM Device notifier
	 * 
	 */
	public GCMDeviceNotifier() {
		messageExecutor = MessageExecutor.INSTANCE;
	}
	
	/** Notifies the specified device that it has pending requests
	 * 
	 * @param device The device to notify of pending requests
	 */
	@Override
	public void notifyDevice(Device device) {
		SyncMessageCommand command = new SyncMessageCommand(device.getDeviceRegistrationId(), GCM_REQUEST_URL);
		messageExecutor.executeNow(command);		
	}

}
