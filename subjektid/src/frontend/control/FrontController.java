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

import backend.DA.SubjectsDAO;
import backend.model.Employee;

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
	//	SessionManager sessionManager = new SessionManager(req);
		ViewManager viewManager = new ViewManager();

		String event = null;
		String view = "default_view";

		// in all controllers do if sessionManager.loggedIn()
		event = new EventFinder().find(params);

		Controller controller = new ControllerFactory().getController(event);

		view = controller.service(event, req, resp);
		viewManager.navigate(view, req, resp, context);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.doGet(req, resp);
	}

}
