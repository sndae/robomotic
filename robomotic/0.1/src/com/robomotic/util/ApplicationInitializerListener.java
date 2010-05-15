/**
 *
 */
package com.robomotic.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.robomotic.config.ConfigLoader;
import com.robomotic.stores.SensorDataStore;

/**
 * @author Giuseppe Miscione
 *
 */
public class ApplicationInitializerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// initialize config loader
		ConfigLoader.getInstance(event.getServletContext());

		// initialize store data thread
		SensorDataStore.startStoreThread();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		SensorDataStore.stopStoreThread();
	}

}
