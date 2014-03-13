/**
 * 
 */
package com.ricex.aft.client.cache;

import java.util.ArrayList;
import java.util.List;

/**
 *  Base implementation of the Cache interface
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractCache<T, I> implements Cache<T, I> {
	
	/** The list of cache listeners */
	protected List<CacheListener> cacheListeners;
	
	/** Initializes the list of cache listeners
	 * 
	 */
	
	public AbstractCache() {
		cacheListeners = new ArrayList<CacheListener>();		
	}

	
	/** Sends the specified cache update vent to all of the cache listeners
	 * 
	 * @param e THe cache update event to send
	 */
	
	protected void fireCacheUpdateEvent(CacheUpdateEvent e) {
		for (CacheListener listener : cacheListeners) {
			listener.update(e);
		}
	}
	
	/** Adds the specified cache listener
	 * 
	 * @param listener The cache listener to add
	 */
	
	public void addCacheListener(CacheListener listener) {
		cacheListeners.add(listener);
	}
	
	/** Removes the specified cache listener
	 * 
	 * @param listener The cache listener to remove
	 */
	
	public void removeCacheListener(CacheListener listener) {
		cacheListeners.add(listener);
	}
}
