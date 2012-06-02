package frontend.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.DA.SubjectsORM;
import backend.model.SubjectAttributeType;

import log.MyLogger;

public class AjaxController extends HttpServlet {
	/**
	 * Auto-generated serial version UID.
	 */
	private static final long serialVersionUID = -2369954145801947583L;

	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
			MyLogger.logMessage("AjaxContoller successfully initialized.");
		} catch (Exception e) {
			MyLogger.log("AjaxController.init()", e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		SessionManager sessionManager = new SessionManager(req);
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			MyLogger.log("AjaxController.doPost()", e.getMessage());
		}
		if (sessionManager.loggedIn()) {
			if (req.getParameter("attribute").equals("subjectTypeFk")) {
				String json = getSubjectAttributes(Long.parseLong(
						req.getParameter("value")));
				out.write(json);
				out.flush();
			}
		}
		
	}
	
	private String getSubjectAttributes(long subjectTypeFk) {
		SubjectsORM orm = new SubjectsORM();
		List<SubjectAttributeType> types = orm.findByID(
				SubjectAttributeType.class, "subjectTypeFk", subjectTypeFk);
		sortSubjectAttributes(types);
		String json = "[";
		for (SubjectAttributeType type : types) {
			json += "{ \"name\": \"" + type.getTypeName() + "\", \"type\": \""
					+ type.getDataType() + "\" }, ";
		}
		return json.substring(0, json.length() - 2) + "]";
	}
	
	private List<SubjectAttributeType> sortSubjectAttributes(
			List<SubjectAttributeType> types) {
		for (int i = 0; i < types.size(); i++) {
			int min = i;
			for (int j = i + 1; j < types.size(); j++) {
				if (types.get(min).getOrderby() > types.get(j).getOrderby()) {
					min = j;
				}
			}
			if (i != min) {
				SubjectAttributeType spare = types.get(i);
				types.set(i, types.get(min));
				types.set(min, spare);
			}
		}
		return types;
	}

}