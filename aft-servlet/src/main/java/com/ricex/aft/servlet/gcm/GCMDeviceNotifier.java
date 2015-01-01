/**
 * 
 */
package com.ricex.aft.servlet.gcm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.servlet.controller.api.DeviceController;
import com.ricex.aft.servlet.notifier.DeviceNotifier;

/** The GCM implementation of Device Notifier that uses Google Cloud Messaging to send notifications to devices
 * 
 * @author Mitchell Caisse
 *
 */
public class GCMDeviceNotifier implements DeviceNotifier {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(GCMDeviceNotifier.class);
	
	/** The url to post GCM notifications / messages to */
	private static final String GCM_REQUEST_URL = "https://android.googleapis.com/gcm/send";
	
	/** The GCM API key to use when sending messages to the server */
	private final String gcmApiKey;
	
	/** The message executor to use to execute sync message's */
	private MessageExecutor messageExecutor;
	
	/**  Creates a new GCM Device notifier
	 * 
	 */
	public GCMDeviceNotifier(String gcmApiKey) {
		this.gcmApiKey = gcmApiKey;
		messageExecutor = MessageExecutor.INSTANCE;
	}
	
	/** Notifies the specified device that it has pending requests
	 * 
	 * @param device The device to notify of pending requests
	 */
	@Override
	public void notifyDevice(Device device) {
		GCMSyncMessageCommand command = new GCMSyncMessageCommand(gcmApiKey, device.getDeviceRegistrationId(), GCM_REQUEST_URL);
		messageExecutor.executeNow(command);		
	}

}
