/**
 * 
 */
package com.ricex.aft.client.view.device;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.aft.client.cache.CacheListener;
import com.ricex.aft.client.cache.CacheUpdateEvent;
import com.ricex.aft.client.cache.DeviceCache;
import com.ricex.aft.client.controller.DeviceController;
import com.ricex.aft.client.controller.RequestListener;
import com.ricex.aft.client.request.IRequest;
import com.ricex.aft.client.view.tab.Tab;
import com.ricex.aft.common.entity.Device;

/**
 *  The table view for displaying devices
 *  
 * @author Mitchell Caisse
 *
 */
public class DeviceTableView extends Tab implements CacheListener, RequestListener<List<Device>> {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(DeviceTableView.class);
	
	/** The JTable for displaying the devices */
	private JTable deviceTable;
	
	/** The table model for the device table */
	private DeviceTableModel deviceTableModel;
	
	/** The ScrollPane for the device table */
	private JScrollPane tableScrollPane;
	
	/** Creates a new Device table view */
	
	public DeviceTableView() {
		deviceTableModel = new DeviceTableModel();
		deviceTable = new JTable(deviceTableModel);
		
		tableScrollPane = new JScrollPane(deviceTable);
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		add(tableScrollPane, BorderLayout.CENTER);

		DeviceCache.getInstance().addCacheListener(this);			
		//fetch all of the devices
		DeviceController.getInstance().getAllDevices(this);
	}
	
	/**
	 * {@inheritDoc}
	 * @return false
	 */
		
	@Override
	public boolean onTabClose() {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @return false
	 */
	
	@Override	
	public boolean isClosable() {
		return false;
	}

	/** Updates the data in the table when the DeviceCache is updated
	 * 
	 */
	
	public void update(CacheUpdateEvent e) {
		deviceTableModel.setDevices(DeviceCache.getInstance().getAll());
	}

	/** Called when the request to update the device table has succeed
	 * 
	 */
	
	public void onSucess(IRequest<List<Device>> request) {
	}

	/** Called when the request to update the device table was canceled 
	 * 
	 */
	
	public void cancelled(IRequest<List<Device>> request) {
		log.info("Request to update devices was cancelled");
	}

	/** Called when the request to update the device table failed
	 * 
	 */
	
	public void onFailure(IRequest<List<Device>> request, Exception e) {
		log.error("Request to update devices failed", e);
	}
	
}
