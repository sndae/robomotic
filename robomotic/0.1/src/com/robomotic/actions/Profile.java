/**
 *
 */
package com.robomotic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.robomotic.actions.models.ProfileModel;

/**
 * @author Giuseppe Miscione
 *
 */
public class Profile extends GenericAction implements ModelDriven<ProfileModel> {

	private static final long serialVersionUID = 7315892534780868960L;

	private static Logger LOG = LoggerFactory.getLogger(Profile.class);

	private ProfileModel model;

	public Profile() {
		super();
		model = new ProfileModel();
	}

	@Override
	public String execute() throws Exception {
		LOG.debug("Profile action is being executed");

		if(session.get(userSessionParam) == null) {
			LOG.debug("User is not logged in");
			return "login";
		}

		return "success";
	}

	@Override
	public ProfileModel getModel() {
		return model;
	}

}
