package com.robomotic.struts2.interceptors;

import javax.persistence.EntityManager;

public interface EntityManagerAware {

	/**
	 * Returns the instance of the {@link EntityManager}
	 * associated with the current action.
	 *
	 * @return an {@link EntityManager}.
	 */
	public EntityManager getEntityManager();

	/**
	 * Sets the instance of the {@link EntityManager}
	 * that this action can use to retrieve data.
	 *
	 * @param em An initialized {@link EntityManager}
	 * that this action can use.
	 */
	public void setEntityManager(EntityManager em);

}
