/**
 * 
 */
package com.ricex.aft.client.cache;

/**
 *  Update event that is produced when a change is made in the Cache
 *  
 * @author Mitchell Caisse
 *
 */
public class CacheUpdateEvent {

	public enum Type {
		NOT_SPECIFIED, ADDED_ELEMENT, ADDED_MULTIPLE_ELEMENTS, RESET_ELEMENTS;
	}
	
	/** The type of this cache update event */
	private Type type;
	
	/** The source of this cache update */
	private Cache<?,?> source;	
	
	
	/** Creates a new Cache Update Event defaulting to NOT_SPECIFIED type
	 * 
	 * @param source The source of the cache event
	 */
	
	public CacheUpdateEvent(Cache<?,?> source) {
		this(source, Type.NOT_SPECIFIED);
	}
	
	/** Creates a new CacheUpdateEvent with the given source and type
	 * 
	 * @param source The Cache object that triggered this update
	 * @param type The type cache update performed
	 */
	
	public CacheUpdateEvent(Cache<?,?> source, Type type) {
		this.source = source;
		this.type = type;
	}

	/**
	 * @return The type of update that was performed
	 */
	
	public Type getType() {
		return type;
	}

	/**
	 * @return The cache object that triggered this cache update
	 */
	
	public Cache<?, ?> getSource() {
		return source;
	}
	
	
	
}
