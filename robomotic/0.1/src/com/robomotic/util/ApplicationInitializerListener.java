/**
 *
 */
package com.robomotic.util;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.robomotic.config.ConfigLoader;
import com.robomotic.persistence.manager.EntityManagerStore;
import com.robomotic.stores.SensorDataStore;
import com.robomotic.stores.UserStore;

/**
 * @author Giuseppe Miscione
 *
 */
public class ApplicationInitializerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// initialize config loader
		ConfigLoader.getInstance(event.getServletContext());

		// check for admin user existence
		EntityManager em = null;
		try {
			em = EntityManagerStore.getEntityManager();
			UserStore us = new UserStore(em);
			us.checkForAdminExistence();
		} finally {
			Util.closeObject(em);
		}

		// initialize store data thread
		SensorDataStore.startStoreThread();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		SensorDataStore.stopStoreThread();
	}

}
