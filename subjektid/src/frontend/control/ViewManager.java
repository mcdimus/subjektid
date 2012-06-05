package frontend.control;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.MyLogger;

public class ViewManager {

	public void navigate(String view, HttpServletRequest req,
			HttpServletResponse resp, ServletContext context) {

		String dispatcher = null;
		try {
			if (view.equals("login_view")) {
				dispatcher = "/login.jsp";
			} else if (view.equals("default_view")) {
				dispatcher = "/default.jsp";
			} else if (view.equals("add_new_subject_view")) {
				dispatcher = "/new_subject.jsp";
			} else if (view.equals("edit_subject_view")) {
				dispatcher = "/edit_subject.jsp";
			} else if (view.equals("search_view")) {
				dispatcher = "/search.jsp";
			} else if (view.equals("accounts_view")){
				dispatcher = "/accounts.jsp";
			} else if (view.equals("accountForm_view")){
				dispatcher = "/account_form.jsp";
			}

			context.getRequestDispatcher(dispatcher).forward(req, resp);
		} catch (Exception e) {
			MyLogger.log("ViewManager.navigate()", e.getMessage());
		}

		// case 6:
		// // Peale onnestunud uue kirje lisamist suuname brauseri ymber -
		// // et valtida andmete korduvat postitamist
		// // ja sellest tulenevat korduvat andmesisestamist kui kasutaja
		// // teeb brauseris "refresh"-i.
		// // On ka teine voimalus - html-vormide nummeramise ja
		// // sessiooniobjekti kasutamsie abil -
		// // vaata naidet "addnew".
		// // req.getContextPath() - annab veebirakenduse nime serveril.
		// inserted_auto = (String) request.getAttribute("inserted_auto");
		// response.sendRedirect(request.getContextPath()
		// + "/c?mode=auto&sub_mode=vaata_autot&auto="
		// + inserted_auto);
		// break;


	}
}