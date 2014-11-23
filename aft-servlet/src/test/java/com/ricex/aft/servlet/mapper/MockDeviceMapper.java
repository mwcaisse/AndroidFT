/**
 * 
 */
package com.ricex.aft.servlet.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ricex.aft.common.entity.Device;

/** Test implementation of the Device Mapper
 * 
 * @author Mitchell Caisse
 *
 */
public class MockDeviceMapper implements DeviceMapper {

	/** The listing of devices */
	private Map<Long, Device> devices;
	
	/** The last device id */
	private long lastDeviceId;
	
	/** Creates a new Test Device Mapper
	 * 
	 */
	public MockDeviceMapper() { 
		devices = new HashMap<Long, Device>();
		lastDeviceId = 0 ;
	}
	
	@Override
	public List<Device> getAllDevices() {
		return new ArrayList<Device>(devices.values());
	}
	
	@Override
	public List<Device> getAllDevicesByUser(long userId) {
		//TODO: Implement
		return new ArrayList<Device>();
	}

	@Override
	public Device getDeviceId(long deviceId) {
		return devices.get(deviceId);
	}

	@Override
	public Device getDeviceUid(String deviceUid) {
		Device withUid = null;
		for (Device device : getAllDevices()) {
			if (device.getDeviceUid().equals(deviceUid)) {
				withUid = device;
				break;
			}
		}
		return withUid;
	}

	@Override
	public void updateDevice(Device device) {
		devices.put(device.getDeviceId(), device);		
	}
	
	@Override
	public void createDevice(Device device) {
		device.setDeviceId(getNextDeviceId());
		updateDevice(device);		
	}
	
	/** Returns the next device id
	 * 
	 * @return the next device id
	 */
	private long getNextDeviceId() {
		return lastDeviceId ++;
	}

}
