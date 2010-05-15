/**
 *
 */
package com.robomotic.stores;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robomotic.config.ConfigLoader;
import com.robomotic.stores.entities.SensorData;

/**
 * @author Giuseppe Miscione
 *
 */
public class SensorDataStore extends GenericStore {

	/**
	 * The thread that stores sensor data in the database.
	 * This class pops data out from the blocking queue
	 * and store it using Hibernate.
	 *
	 * @author Giuseppe Miscione
	 *
	 */
	private static class StoreSensorDataThread extends Thread {

		private boolean stop;

		public StoreSensorDataThread() {
			setStop(false);
		}

		public synchronized boolean isStop() {
			return stop;
		}

		@Override
		public void run() {
			while(!isStop()) {
				try {
					LOG.debug("Store thread: getting data from queue.");
					sensorData.take();
					LOG.debug("Store thread: data retrieved from queue.");
				} catch(InterruptedException e) {
					LOG.debug("Store thread: thread was interrupted. " + e.getMessage());
				} catch(Exception e) {
					LOG.error("Store thread: " + e.getMessage(), e);
				}
			}
			LOG.debug("Store thread: exiting from thread.");
		}

		public synchronized void setStop(boolean stop) {
			this.stop = stop;
		}

	}

	private static Logger LOG = LoggerFactory.getLogger(SensorDataStore.class);
	private static BlockingQueue<SensorData> sensorData;

	private static StoreSensorDataThread storeThread;

	static {
		int maxQueueLength = 10;
		try {
			String queueLength = (String)ConfigLoader.getInstance(null).get("sensor-data.max-queue-length");
			maxQueueLength = Integer.parseInt(queueLength);
		} catch(Exception e){ /* Bad numeric format, usgin default */ }

		sensorData = new LinkedBlockingQueue<SensorData>(maxQueueLength);
	}

	/**
	 * Static method to initialize and start a data storing
	 * thread. If the thread is already initialized this method
	 * does nothing.
	 */
	public static synchronized void startStoreThread() {
		if(storeThread == null) {
			storeThread = new StoreSensorDataThread();
			storeThread.start();
		}
	}

	/**
	 * Static method to stop the data storing thread. If the
	 * thread is non initialized or has been already stopped,
	 * this method does nothing.
	 */
	public static synchronized void stopStoreThread() {
		if(storeThread != null) {
			synchronized (storeThread) {
				if(storeThread.isAlive()) {
					storeThread.setStop(true);
					storeThread.interrupt();
					storeThread = null;
				}
			}
		}
	}

	/**
	 * Creates a new instance of a sensor data store.
	 *
	 * @param em The {@link EntityManager} that will be
	 * used to retrieve data.
	 */
	public SensorDataStore(EntityManager em) {
		super(em);
	}

}
