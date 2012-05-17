package frontend.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.DA.SubjectsDAO;
import backend.model.Employee;
import frontend.forms.LoginForm;
import frontend.validator.LoginFormValidator;

public class LoginController extends Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {

		String view = "default_view";

		Map<String, String[]> params = req.getParameterMap();
		SessionManager sessionManager = new SessionManager(req);

		if (action.equals("logout")) {

			if (sessionManager.isExist()) {

				sessionManager.invalidateCurrent();
				view = "login_view";
			}
		}

		sessionManager.createNewSession();

		if (action.equals("login")) {
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
					SubjectsDAO subjectDao = new SubjectsDAO();
					Employee employee = subjectDao.auth(loginForm);
					if (employee != null) {
						req.setAttribute("status", "WELCOME");
						sessionManager.set("username", username);
						sessionManager.set("employee_id",
								String.valueOf(employee.getEmployee()));

						view = "default_view";
					} else {
						HashMap<String, String> errors = new HashMap<String, String>();
//						errors.put("reason", "Wrong username or password");
						errors.put("password", "Wrong username or password");
						req.setAttribute("errors", errors);
						req.setAttribute("loginForm", loginForm);
						view = "login_view";
					}

				} else {
					view = "login_view";
					HashMap<String, String> errors = (HashMap<String, String>) loginFormValidator
							.getErrors();
//					errors.put("reason", "Login failed");
					req.setAttribute("errors", errors);
					req.setAttribute("loginForm", loginForm);
				}
			} else {
				view = "login_view";
			}
		} else {
			if (sessionManager.loggedIn()) {
				view = "default_view";
			} else {
				view = "login_view";
			}
		}

		return view;
	}
}
