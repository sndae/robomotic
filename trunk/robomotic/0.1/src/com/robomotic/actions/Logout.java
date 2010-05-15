/**
 *
 */
package com.robomotic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Giuseppe Miscione
 *
 */
public class Logout extends GenericAction {

	private static final long serialVersionUID = -3148246284722973812L;

	private static Logger LOG = LoggerFactory.getLogger(Logout.class);

	public Logout() {
		super();
	}

	@Override
	public String execute() throws Exception {
		LOG.debug("Logout action is being executed");
		session.clear();
		return "success";
	}
}
