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
	
	/** Adds the given element to the element map without firing a change event
	 * 
	 * 	This allows the AbstractCache to perform all the necesary add events, and each sub class
	 * 		only needs to implement this method and does not have to worry about creating update events
	 * 
	 * @param element The element to add to the Cache
	 */
	
	protected abstract void addElement(T element);
	
	/** Adds the specified element to the cache and notifies the CacheListeners that a change has occured
	 * 
	 * @param element The element to add to the cache
	 */
	
	public void add(T element) {
		addElement(element);
		fireCacheUpdateEvent(new CacheUpdateEvent(this, CacheUpdateEvent.Type.ADDED_ELEMENT));
	}
	
	/** Adds the specified list of elements to the cache, and notifies the CacheListeners that a change has occured'
	 * 
	 * @param elements The elements to add to the cache
	 */
	
	public void add(List<T> elements) {
		for (T element : elements) {
			System.out.println("AbstractCache adding element");
			addElement(element);
		}
		System.out.println("What?");
		fireCacheUpdateEvent(new CacheUpdateEvent(this, CacheUpdateEvent.Type.ADDED_MULTIPLE_ELEMENTS));
	}
	
	/** Sets the elements in the cache to the specified elements.
	 * 
	 * Removes all of the current elements and replaces then with the elements in the list, then notifies the CacheListeners
	 * 		that an update has occurred.
	 * 
	 * @param elements The new elements to put into the Cache
	 */
	
	public void setElements(List<T> elements) {
		this.elements.clear();
		for (T element : elements) {
			addElement(element);
		}
		fireCacheUpdateEvent(new CacheUpdateEvent(this, CacheUpdateEvent.Type.RESET_ELEMENTS));
	}
	
	/** Purges all of the elements from the cache
	 * 
	 */
	
	public void purgeCache() {
		elements.clear();
		fireCacheUpdateEvent(new CacheUpdateEvent(this, CacheUpdateEvent.Type.RESET_ELEMENTS));
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
