/**
 * 
 */
package com.ricex.aft.client.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Base implementation of the Cache interface
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractCache<T, I> implements Cache<T, I> {
	
	/** The map for storing the cache elements */
	protected Map<I, T> elements;
	
	/** The list of cache listeners */
	protected List<CacheListener> cacheListeners;
	
	/** Initializes the list of cache listeners, and the cache for storing elements
	 * 
	 */
	
	public AbstractCache() {
		elements = new HashMap<I, T>();
		cacheListeners = new ArrayList<CacheListener>();		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	
	public void add(List<T> elements) {
		add(elements);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	
	public void setElements(List<T> elements) {
		elements.clear();
		add(elements);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public T get(I id) {
		return elements.get(id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public List<T> getAll() {
		return new ArrayList<T>(elements.values());
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
