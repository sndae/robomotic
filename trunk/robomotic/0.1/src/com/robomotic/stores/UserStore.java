/**
 *
 */
package com.robomotic.stores;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robomotic.stores.entities.Administrator;
import com.robomotic.stores.entities.User;
import com.robomotic.util.Util;

/**
 * @author Giuseppe Miscione
 *
 */
public class UserStore extends GenericStore {

	private static Logger LOG = LoggerFactory.getLogger(UserStore.class);

	/**
	 * Creates a new instance of a user store.
	 *
	 * @param em The {@link EntityManager} that will be
	 * used to retrieve data.
	 */
	public UserStore(EntityManager em) {
		super(em);
	}

	/**
	 * Checks if an administrator exists in the system.
	 * If not, an admin with username <code>admin</code>
	 * and password <code>adminadmin</code> will be created.
	 *
	 * @return <code>true</code> if an administrator exists,
	 * <code>false</code> otherwise.
	 */
	public boolean checkForAdminExistence() {
		boolean ret = false;

		try {
			String query =
				"SELECT a " +
				"FROM Administrator a";

			try {
				Query q = em.createQuery(query);
				List<Administrator> admins = q.getResultList();
				if(admins.size() > 0)
					ret = true;
			} catch(Exception e) { }

			if(!ret) {
				LOG.info("Administrators are not present, creating default one.");
				Administrator admin = new Administrator();
				admin.setUsername("admin");
				admin.setAndEncodePassword("adminadmin");
				admin.setName("admin");
				admin.setSurname("admin");
				admin.setEmail("admin@robomotic.com");

				em.getTransaction().begin();
				try {
					em.persist(admin);
					em.getTransaction().commit();
				} catch(Exception e) {
					LOG.error("Cannot created default administrator.", e);
					em.getTransaction().rollback();
				}
			}
			else {
				LOG.info("Administrators already exist in the system.");
			}

		} catch(Exception e) {
			LOG.error("An error occurred while searching administrators.", e);
		}

		return ret;
	}

	/**
	 * Checks the provided user credentials and returns the corresponding user.
	 * If the credentials are not valid, <code>null</code> is returned.
	 *
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return The {@link User} corresponding to the provided credentials or
	 * <code>null</code> if the credentials are not valid.
	 */
	public User checkUserCredentials(String username, String password) {
		User ret = null;
		String query =
			"SELECT u " +
			"FROM User u " +
			"WHERE username = :username AND password = :password";
		try {
			Query q = em.createQuery(query);
			q.setParameter("username", username);
			q.setParameter("password", Util.encodeUserPassword(password));

			ret = (User)q.getSingleResult();
		} catch(Exception e) {
			LOG.error("An error occurred while checking credentials.", e);
		}
		return ret;
	}

}
