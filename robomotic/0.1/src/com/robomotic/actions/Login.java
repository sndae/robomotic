/**
 *
 */
package com.robomotic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.robomotic.actions.models.LoginModel;

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

		if(session.get(userSessionParam) != null) {
			return "success";
		}

		if(model.getUsername() != null && model.getPassword() != null) {
			session.put(userSessionParam, Boolean.TRUE);
			return "success";
		}

		return "login";
	}

	@Override
	public LoginModel getModel() {
		return model;
	}

}
