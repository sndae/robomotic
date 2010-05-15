package com.robomotic.struts2.interceptors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.robomotic.persistence.manager.EntityManagerStore;
import com.robomotic.util.Util;

public class EntityManagerInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 62150656732419332L;

	private static Logger LOG = LoggerFactory.getLogger(EntityManagerInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		// before action exection, try to set EntityManager instance
		if(action instanceof EntityManagerAware) {
			EntityManager em = EntityManagerStore.getEntityManager();
			if(em == null)
				LOG.error("Entity manager cannot be initialized.");
			((EntityManagerAware)action).setEntityManager(em);
		}

		// execute action
		String ret = invocation.invoke();

		// after response generation, try to close EntityManager instance
		if(action instanceof EntityManagerAware) {
			EntityManager em = ((EntityManagerAware)action).getEntityManager();
			Util.closeObject(em);
			em = null;
			((EntityManagerAware)action).setEntityManager(null);
		}

		return ret;
	}

}
