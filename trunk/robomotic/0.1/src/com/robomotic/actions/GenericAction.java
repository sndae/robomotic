/**
 *
 */
package com.robomotic.actions;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.robomotic.beans.ThemeBean;
import com.robomotic.config.ConfigLoader;
import com.robomotic.struts2.interceptors.EntityManagerAware;
import com.robomotic.util.Constants;
import com.robomotic.util.Util;

/**
 * This class is the superclass of all backend actions.
 *
 * @author Giuseppe Miscione
 *
 */
public class GenericAction extends ActionSupport implements ApplicationAware, SessionAware, EntityManagerAware{

	private static final long serialVersionUID = 2762044250396432648L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> application;
	protected Map<String, Object> session;
	protected EntityManager em;

	protected ThemeBean themeInfo;
	protected String errorMessageKey;
	protected String userSessionParam;

	public GenericAction() {
		userSessionParam = (String)ConfigLoader.getInstance().get("general.session.user-session");
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}

	/**
	 * Returns the list of all configured languages.
	 *
	 * @return A list with all configured languages.
	 */
	public List<String> getAvailableLanguages() {
		List<String> ret = Util.getConfigurationParamAsList("general.lang.available-langs");
		return ret;
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	/**
	 * Returns the error message key that will be
	 * shown in the page.
	 *
	 * @return The error message key or <code>null</code>
	 * if no error message must be shown.
	 */
	public String getErrorMessageKey() {
		return errorMessageKey;
	}

	/**
	 * Returns the locale stored in the session.
	 *
	 * @return The locale stored in the session. The default
	 * language is loaded from the configuration file.
	 */
	public String getPageLocale() {
		return (session.containsKey(Constants.pageLocaleParam) ?
			(String)session.get(Constants.pageLocaleParam) :
			(String)ConfigLoader.getInstance().get("general.lang.default"));
	}

	/**
	 * Returns the string that represents the query string
	 * removing the parameter used to switch page locale.
	 *
	 * @return The query string except the parameter used
	 * to switch the page locale.
	 */
	public String getQueryString() {
		String qs = request.getQueryString();
		if(Util.isEmptyOrWhiteSpaceOnly(qs))
			return null;
		String params[] = qs.split("\\&");
		StringBuffer ret = new StringBuffer();
		for(int i = 0; i < params.length; i++) {
			if(!params[i].startsWith(Constants.pageLocaleParam)) {
				if(ret.length() != 0) ret.append("&");
				ret.append(params[i]);
			}
		}
		return ret.toString();
	}

	/**
	 * Loads the theme that is set for this action.
	 *
	 * @return The {@link ThemeBean} object with the
	 * informations of the theme.
	 */
	public ThemeBean getThemeInfo() {
		if(themeInfo == null) {
			themeInfo = new ThemeBean(getThemeName());
		}
		return themeInfo;
	}

	/**
	 * Returns the theme name stored in the session.
	 *
	 * @return The theme name stored in the session. The default
	 * theme is loaded from the configuration file.
	 */
	protected String getThemeName() {
		return (session.containsKey(Constants.themeParam) ?
				(String)session.get(Constants.themeParam) :
				(String)ConfigLoader.getInstance().get("general.theme.default"));
	}

	/**
	 * Returns the title of the site as set in the configuration file.
	 *
	 * @return The title of the site.
	 */
	public String getTitle() {
		return Util.getConfigurationParamAsString("general.title");
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Sets the error message key that will be shown in the page.
	 *
	 * @param errorMessage the error message key that will be
	 * shown in the page.
	 */
	public void setErrorMessageKey(String errorMessageKey) {
		this.errorMessageKey = errorMessageKey;
	}

	/**
	 * Sets the specified locale in session.
	 *
	 * @param locale The locale to set.
	 */
	public void setPageLocale(String pageLocale) {
		session.put(Constants.pageLocaleParam, pageLocale);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * Sets the theme name that will be used to render pages.
	 *
	 * @param themeName The name of theme to set.
	 */
	protected void setThemeName(String themeName) {
		session.put(Constants.themeParam, themeName);
	}

}
