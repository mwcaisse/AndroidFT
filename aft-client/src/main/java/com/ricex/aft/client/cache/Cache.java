/**
 * 
 */
package com.ricex.aft.client.cache;

import java.util.List;

/**
 *  A local cache of an element fetched from the web service
 *  
 * @author Mitchell Caisse
 *
 */
public interface Cache<T, I> {
	
	/** Adds the given element to the cache
	 * 	
	 * @param element The element to add
	 */
	
	public void add(T element);
	
	/** Adds the given set of elements to the cache
	 * 
	 * @param elements The elements to add
	 */
	
	public void add(List<T> elements);
	
	/** Sets the elements in the cache to the specified elements
	 * 
	 * @param elements The new elements
	 */
	
	public void setElements(List<T> elements);
	
	/** Purges all of the elements from the cache
	 * 
	 */
	
	public void purgeCache();
	
	/** Gets the element in the cache with the specified id
	 * 
	 * @param id The id of the element to fetch
	 * @return The element with the specified id
	 */
	
	public T get(I id);
	
	/** Gets a list of all of the elements in the cache
	 * 
	 * @return List of all the elements in the cache
	 */
	
	public List<T> getAll();
	
	/** Adds the specified cache listener
	 * 
	 * @param listener The cache listener to add
	 */
	
	public void addCacheListener(CacheListener listener);
	
	/** Removes the specified cache listener
	 * 
	 * @param listener The cache listener to remove
	 */
	
	public void removeCacheListener(CacheListener listener);

}
