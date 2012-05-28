package frontend.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

	private HttpServletRequest req = null;

	public SessionManager(HttpServletRequest req) {
		this.req = req;
	}

	public HttpSession getSession(boolean create) {

		return this.req.getSession(create);
	}

	public boolean isExist() {
		HttpSession existingSession = req.getSession(false);
		if (existingSession != null) {
			return true;
		} else {
			return false;
		}
	}

	public void invalidateCurrent() {
		req.getSession(false).invalidate();
	}

	/**
	 * If there is no current session, creates a new session.
	 */
	public void createNewSession() {
		req.getSession(true);
	}

	public boolean loggedIn() {
		if (this.isExist() && req.getSession(false).getAttribute("username") != null) {
			return true;
		} else {
			return false;
		}
	}

	public void set(String atrName, String atrValue) {
		req.getSession(false).setAttribute(atrName, atrValue);
	}
	
	public String get(String atrName) {
		return (String) req.getSession(false).getAttribute(atrName);
	}
	
	public String getEmployeeId() {
		return this.get("employee_id");
	}
}
