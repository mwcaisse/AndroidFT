/**
 * 
 */
package com.ricex.aft.client.cache;

/**
 *  Listener that is notified when a change occurs in a Cache
 *  
 * @author Mitchell Caisse
 *
 */
public interface CacheListener {

	/** Called when a change is triggered in the cache
	 * 
	 * @param e The CacheUpdateEvent containing the type of change and the source of the change that occured
	 */
	
	public void update(CacheUpdateEvent e);
}
