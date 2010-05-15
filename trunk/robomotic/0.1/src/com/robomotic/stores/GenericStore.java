/**
 *
 */
package com.robomotic.stores;

import javax.persistence.EntityManager;

/**
 * @author Giuseppe Miscione
 *
 */
public class GenericStore {

	protected EntityManager em;

	/**
	 * Creates a new instance of a generic store.
	 *
	 * @param em The {@link EntityManager} that will be
	 * used to retrieve data.
	 */
	public GenericStore(EntityManager em) {
		this.em = em;
	}

}
