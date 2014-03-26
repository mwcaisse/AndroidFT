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

public class DeviceComboBoxModel implements MutableComboBoxModel, RequestListener<List<Device>> {

	/** The logger */
	private static Logger log = LoggerFactory.getLogger(DeviceComboBoxModel.class);
	
	/** The devices to list in the combo box */
	private List<Device> devices;
	
	/** The list data listeners to be notified when the data changes */
	private List<ListDataListener> listeners;
	
	/** The currently selected item */
	private Object selectedItem;
	
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
		updateDevices();
	}
	
	/** Requests a list of all devices from the web service
	 * 
	 */
	
	public void updateDevices() {
		DeviceController.getInstance().getAllDevices(this);	
		notifyListeners(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, devices.size() - 1));
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
		selectedItem = anItem;
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public Object getSelectedItem() {
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
	
	public Object getElementAt(int index) {
		return devices.get(index);
	}

	/** 
	 * {@inheritDoc}
	 */
	
	public void addElement(Object obj) {
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
		if (! (obj instanceof Device)) {
			return; //can only add Devices
		}
		Device device = (Device)obj;
		int index = devices.indexOf(device);
		if (index >= 0) {
			devices.remove(device);
			notifyListeners(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));			
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	
	public void insertElementAt(Object obj, int index) {
		if (! (obj instanceof Device)) {
			return; //can only add Devices
		}
		devices.add(index, (Device) obj);
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
