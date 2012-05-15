package frontend.control;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class Controller {

	protected HashMap<String, String[]> params;
	protected String action;

	public abstract String service(HttpServletRequest req, HttpServletResponse resp);
	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the params
	 */
	public HashMap<String, String[]> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(HashMap<String, String[]> params) {
		this.params = params;
	}


	
}
