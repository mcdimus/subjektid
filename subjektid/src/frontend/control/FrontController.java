package frontend.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.forms.LoginForm;
import frontend.validator.LoginFormValidator;

import log.MyLogger;

public class FrontController extends HttpServlet {

	/**
	 * Auto-generated serial version UID.
	 */
	private static final long serialVersionUID = 1124151500741085057L;

	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
			MyLogger.logMessage("Front Contoller successfully initialized.");
		} catch (Exception e) {
			MyLogger.log("FrontController.init()", e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map<String, String[]> params = req.getParameterMap();

		ServletContext context = getServletConfig().getServletContext();
		SessionManager sessionManager = new SessionManager(req);
		ViewManager viewManager = new ViewManager();

		String event = null;
		String view = "default_view";

		event = new EventFinder().find(params);

		if (event.equals("logout_event")) {

			if (sessionManager.isExist()) {

				sessionManager.invalidateCurrent();
				view = "login_view";
				viewManager.navigate(view, req, resp, context);
			}
		}

		sessionManager.createNewSession();

		if (sessionManager.loggedIn()) {
			viewManager.navigate(view, req, resp, context);
		} else {
			if (event.equals("login_event")) {
				String username = null;
				String password = null;

				if (params.containsKey("username")) {
					username = params.get("username")[0];
				}
				if (params.containsKey("password")) {
					password = params.get("password")[0];
				}

				if (username != null && password != null) {
					LoginForm loginForm = new LoginForm(username, password);
					LoginFormValidator loginFormValidator = new LoginFormValidator(
							loginForm);

					if (loginFormValidator.validate()) {
						viewManager.navigate(view, req, resp, context);
					} else {
						view = "login_view";
						HashMap<String, String> errors = (HashMap<String, String>) loginFormValidator
								.getErrors();
						req.setAttribute("errors", errors);
						req.setAttribute("loginForm", loginForm);
						viewManager.navigate(view, req, resp, context);
					}
				} else {
					view = "login_view";
					viewManager.navigate(view, req, resp, context);
				}
			} else {
				view = "login_view";
				viewManager.navigate(view, req, resp, context);
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.doGet(req, resp);
	}

}
