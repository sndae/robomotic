/**
 *
 */
package com.robomotic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.robomotic.actions.models.LoginModel;
import com.robomotic.stores.UserStore;
import com.robomotic.stores.entities.Administrator;
import com.robomotic.stores.entities.User;

/**
 * Action to manage esaminatori's login.
 *
 * @author Giuseppe Miscione
 *
 */
public class Login extends GenericAction implements ModelDriven<LoginModel> {

	private static final long serialVersionUID = -5562397326576186633L;
	private static Logger LOG = LoggerFactory.getLogger(Login.class);

	protected LoginModel model;

	public Login() {
		super();
		model = new LoginModel();
	}

	@Override
	public String execute() throws Exception {
		LOG.debug("Login action is being executed");

		if(model.getUsername() != null && model.getPassword() != null) {
			UserStore us = new UserStore(getEntityManager());
			User user = us.checkUserCredentials(model.getUsername(), model.getPassword());
			session.put(userSessionParam, user);
		}

		Object user = session.get(userSessionParam);
		if(user instanceof Administrator){
			return "admin";
		}
		else if(user instanceof User) {
			return "success";
		}

		return "login";
	}

	@Override
	public LoginModel getModel() {
		return model;
	}

}
