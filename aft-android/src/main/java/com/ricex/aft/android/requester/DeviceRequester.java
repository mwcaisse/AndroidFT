/**
 * 
 */
package com.ricex.aft.android.requester;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

import com.ricex.aft.common.entity.Device;

/**
 * @author Mitchell Caisse
 *
 */
public enum DeviceRequester {
	
	/** The singleton instance of DeviceRequest */
	INSTANCE;

	/** The rest template to use to make requests */
	private final RestTemplate restTemplate;
	
	/** The address of the server to send the requests to */
	private final String serverAddress;
	
	/** The context to use to fetch Device UID */
	private Context context;
	
	private DeviceRequester() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		serverAddress = "http://192.168.1.160:8080/aft-servlet/manager/";
	}
	
	/** Registers the device with the app server
	 * 
	 * @param registrationId The registration ID of the device
	 * @return True if successful, false otherwise
	 */
	
	public boolean registerDevice(String registrationId) {
		Device device = createDevice();
		device.setDeviceRegistrationId(registrationId);
		long res = restTemplate.postForObject(serverAddress + "device/register", device, Long.class);
		return res >= 0;
	}
	
	/** Creates a device that represents this device
	 * 
	 * @return a device
	 */
	private Device createDevice() {
		Device device = new Device();
		device.setDeviceName(Build.MODEL);
		device.setDeviceUid(getDeviceUID());	
		return device;
	}
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	public long getDeviceUID() {
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return Long.parseLong(androidId, 16);
	}

	/**
	 * @return the context
	 */
	
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	
	public void setContext(Context context) {
		this.context = context;
	}
		
}
