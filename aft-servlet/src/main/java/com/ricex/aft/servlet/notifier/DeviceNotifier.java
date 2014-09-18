/**
 * 
 */
package com.ricex.aft.servlet.notifier;

import com.ricex.aft.common.entity.Device;

/** Device Notifier that is used to Notify a device that it has requests available
 * 
 * @author Mitchell Caisse
 *
 */
public interface DeviceNotifier {

	/** Notifies the given device that it has requests available
	 * 
	 * @param device The device to notify
	 */
	public void notifyDevice(Device device);
	
}
