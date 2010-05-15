package com.robomotic.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ognl.OgnlRuntime;

public class OGNLFixListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

	public OGNLFixListener()  {
	}

	public void contextInitialized(ServletContextEvent servletContextEvent)  {
	    OgnlRuntime.setSecurityManager(null);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {

	}

}
