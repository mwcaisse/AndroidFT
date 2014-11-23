/**
 * 
 */
package com.ricex.aft.servlet.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.ricex.aft.common.entity.Device;
import com.ricex.aft.servlet.mapper.DeviceMapper;
import com.ricex.aft.servlet.mapper.MockDeviceMapper;

/** Tests the functionality of the Device Manager
 * 
 * @author Mitchell Caisse
 *
 */
public class TestDeviceManager {

	/** The device mapper to use during testing */
	private DeviceMapper deviceMapper;
	
	/** The Device Manager to use during test */
	private DeviceManager deviceManager;
	
	@Before
	public void setup() {
		deviceMapper = new MockDeviceMapper();
		deviceManager = DeviceManager.INSTANCE;
		deviceManager.setDeviceMapper(deviceMapper);
	}
	
	@Test
	public void TestCreatingDevice() {
		Device dev = createTestDevice();
		long devId = deviceManager.createDevice(dev);
		assertEquals(0, devId);
		assertEquals(dev.getDeviceId(), devId);		
	}
	
	@Test
	public void TestDeviceExistsShouldReturnFalseIfNotExists() {
		assertFalse(deviceManager.deviceExists("testUuid"));
		assertFalse(deviceManager.deviceExists(5));
	}
	
	@Test
	public void TestDeviceExistsShouldReturnTrueIfExists() {
		Device dev = createTestDevice();
		dev.setDeviceUid("testUuid");
		
		//pad the device mapper with some devices
		deviceMapper.createDevice(createTestDevice()); //0
		deviceMapper.createDevice(createTestDevice());
		deviceMapper.createDevice(createTestDevice());		
		deviceMapper.createDevice(dev); //should have id of 3
		
		assertTrue(deviceManager.deviceExists("testUuid"));
		assertTrue(deviceManager.deviceExists(3));
		assertTrue(deviceManager.deviceExists(1));
		assertFalse(deviceManager.deviceExists(4)); //only 4  (0 - 4) devices add, id 4 should not exist		
	}
	
	@Test
	public void TestUpdateDeviceShouldChangeDevice() {
		Device origDev = createTestDevice();
		Device updatedDev = createTestDevice();
		
		deviceMapper.createDevice(origDev);
		updatedDev.setDeviceId(origDev.getDeviceId());
		
		assertEquals(0, deviceManager.updateDevice(updatedDev));
		
		Device modifiedDevice = deviceMapper.getDeviceId(updatedDev.getDeviceId());
		assertEquals(updatedDev.getDeviceName(), modifiedDevice.getDeviceName());
		assertEquals(updatedDev.getDeviceRegistrationId(), modifiedDevice.getDeviceRegistrationId());
		assertEquals(updatedDev.getDeviceUid(), modifiedDevice.getDeviceUid());
	}
	
	@Test
	public void TestGetDeviceReturnsCorrectDevice() {
		Device dev = createTestDevice();
		
		deviceMapper.createDevice(dev);
		
		assertEquals(dev, deviceManager.getDevice(0));
	}
	
	@Test
	public void TestGetAllDevicesReturnsCorrectDevices() {
		deviceMapper.createDevice(createTestDevice()); //0
		deviceMapper.createDevice(createTestDevice());
		deviceMapper.createDevice(createTestDevice());
		deviceMapper.createDevice(createTestDevice()); 
		deviceMapper.createDevice(createTestDevice());
		deviceMapper.createDevice(createTestDevice());
		
		assertEquals(deviceMapper.getAllDevices(), deviceManager.getAllDevices());
	}
	
	/** Creates a test device
	 * 
	 * @return a test device
	 */
	public Device createTestDevice() {
		Device device = new Device();
		device.setDeviceName(createRandomString());
		device.setDeviceRegistrationId(createRandomString());
		device.setDeviceUid(createRandomString());
		return device;
	}
	
	/** Creates a random string
	 * 
	 * @return
	 */
	public String createRandomString() {
		return UUID.randomUUID().toString();
	}
}
