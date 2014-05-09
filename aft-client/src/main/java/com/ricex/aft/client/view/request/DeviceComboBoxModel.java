/**
 * 
 */
package com.ricex.aft.client.view.request;

import java.util.ArrayList;
import java.util.List;

import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.controller.DeviceController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.common.entity.Device;

/** The Combobox model that will be used to display the available devices in RequestView
 * 
 * @author Mitchell Caisse
 *
 */

public class DeviceComboBoxModel implements MutableComboBoxModel<Device>, RequestListener<List<Device>> {

	/** The logger */
	private static Logger log = LoggerFactory.getLogger(DeviceComboBoxModel.class);
	
	/** The devices to list in the combo box */
	private List<Device> devices;
	
	/** The list data listeners to be notified when the data changes */
	private List<ListDataListener> listeners;
	
	/** The currently selected item */
	private Device selectedItem;
	
	/** Creates a new DeviceComboBoxModel
	 * 
	 */
	
	public DeviceComboBoxModel() {
		devices = new ArrayList<Device>();
		listeners = new ArrayList<ListDataListener>();
		log.debug("Creating device Combo Box Model");
		DeviceController.getInstance().getAllDevices(this);
	}

	/** Called when the request to fetch the devices is successful
	 * 
	 */
	
	public void onSucess(IRequest<List<Device>> request) {
		log.debug("Received an OnSuccess from the server when requesting devices");
		this.devices = new ArrayList<Device>(request.getResponse());	
	}
	
	/** Sets the devices in this combo box to the devices in the given list
	 * 
	 * @param devices The new devices for the combo box
	 */
	
	protected void setDevices(List<Device> devices) {
		this.devices = new ArrayList<Device>(devices);
		notifyListeners(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, devices.size() - 1));
	}	
	
	/** Requests a list of all devices from the web service
	 * 
	 */
	
	public void updateDevices() {
		DeviceController.getInstance().getAllDevices(this);			
	}
	
	/** Called when the request to fetch the devices has been cancelled 
	 * 
	 */

	public void cancelled(IRequest<List<Device>> request) {
		log.warn("Received an cancelled from the server when requesting devices");
	}
	
	/**Called when the request to fetch the devices has failed
	 * 
	 */

	public void onFailure(IRequest<List<Device>> request, Exception e) {
		log.error("Received an OnFailure from the server when requesting devices", e);
	}


	/** 
	 * {@inheritDoc}
	 */
	
	public void setSelectedItem(Object anItem) {
		if (!(anItem instanceof Device)) {
			return;
		}
		selectedItem = (Device)anItem;
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public Device getSelectedItem() {
		return selectedItem;
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public int getSize() {
		return devices.size();
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public Device getElementAt(int index) {
		return devices.get(index);
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public void addElement(Device obj) {
		if (! (obj instanceof Device)) {
			return; //can only add Devices
		}
		Device device = (Device) obj;
		devices.add(device);
		int index = devices.indexOf(device);
		notifyListeners(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public void removeElement(Object obj) {	
		int index = devices.indexOf(obj);
		if (index >= 0) {
			devices.remove(obj);
			notifyListeners(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));			
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	
	public void insertElementAt(Device obj, int index) {
		devices.add(index, obj);
		notifyListeners(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public void removeElementAt(int index) {	
		devices.remove(index);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public void removeListDataListener(ListDataListener l) {	
		listeners.remove(l);
	}
	
	/** Notifies listeners that the data has changed
	 *  
	 * @param e The ListDataEvent to send to the data listeners
	 */
	
	protected void notifyListeners(ListDataEvent e) {
		for (ListDataListener listener : listeners) {
			listener.contentsChanged(e);
		}
	}
}
