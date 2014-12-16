package com.ricex.aft.common.entity;


/** Abstract Entity for all entities to inherit from
 * 
 * @author Mitchell Caisse
 *
 */
public abstract class AbstractEntity implements Entity {

	/** The id of thi entity */
	protected long id;
	
	/** Creates a new Abstract Entity
	 * 
	 */
	public AbstractEntity() {
		
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	
}
