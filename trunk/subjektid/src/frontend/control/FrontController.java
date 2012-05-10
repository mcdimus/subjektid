package frontend.control;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		} catch (Exception e) {
			MyLogger.log("FrontController.init()", e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.doGet(req, resp);
	}

}
