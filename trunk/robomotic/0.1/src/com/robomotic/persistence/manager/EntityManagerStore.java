/**
 *
 */
package com.robomotic.persistence.manager;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robomotic.config.ConfigLoader;
import com.robomotic.util.Util;

/**
 * @author Giuseppe Miscione
 *
 */
public class EntityManagerStore {

	private static Logger LOG = LoggerFactory.getLogger(EntityManagerStore.class);

	private static EntityManagerFactory emf;

	static {
		try{
			Map<String, String> properties = new LinkedHashMap<String, String>();

			String persistenceUnitName = (String)ConfigLoader.getInstance().get("persistence.persistence-unit");
			if(Util.isEmptyOrWhiteSpaceOnly(persistenceUnitName)) {
				persistenceUnitName = "robomotic";
			}
			emf = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
			if(emf != null){
				EntityManager em = emf.createEntityManager();
				em.close();
			}
		} catch(Exception e){
			LOG.error("Unable to initialize entity manager.", e);
		}
	}

	public static EntityManager getEntityManager(){
		return (emf != null ? emf.createEntityManager() : null);
	}
}
