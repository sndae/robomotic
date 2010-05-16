/**
 *
 */
package com.robomotic.actions.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.robomotic.actions.GenericAction;
import com.robomotic.actions.admin.models.AdministrationModel;

/**
 * @author Giuseppe Miscione
 *
 */
public class Administration extends GenericAction implements ModelDriven<AdministrationModel> {

	private static final long serialVersionUID = -5663022347077989886L;

	private static Logger LOG = LoggerFactory.getLogger(Administration.class);

	private AdministrationModel model;

	public Administration() {
		super();
		model = new AdministrationModel();
	}

	@Override
	public String execute() throws Exception {
		LOG.debug("Administration action is being executed");

		if(session.get(userSessionParam) == null) {
			LOG.debug("User is not logged in");
			return "login";
		}

		return "success";
	}

	@Override
	public AdministrationModel getModel() {
		return model;
	}

}
