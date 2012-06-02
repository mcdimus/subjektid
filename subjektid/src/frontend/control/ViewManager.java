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
			}

			context.getRequestDispatcher(dispatcher).forward(req, resp);
		} catch (Exception e) {
			MyLogger.log("ViewManager.navigate()", e.getMessage());
		}
		
		// int user_view = 0;
		// String inserted_auto = "";
		//
		// try {
		// if (view.equals("show_autos")) {
		// user_view = 1;
		// }
		//
		// if (view.equals("show_auto")) {
		// user_view = 2;
		// }
		//
		// if (view.equals("otsi_autot")) {
		// user_view = 3;
		// }
		//
		// if (view.equals("lisa_auto")) {
		// user_view = 4;
		// }
		//
		// if (view.equals("peale_edukat_lisamist")) {
		// user_view = 6;
		// }
		//
		// if (view.equals("login")) {
		// user_view = 7;
		// }
		//
		// if (view.equals("doc_search_page")) {
		// user_view = 8;
		// }
		//
		// if (view.equals("show_doc")) {
		// user_view = 9;
		// }
		//
		// if (view.equals("add_doc")) {
		// user_view = 10;
		// }
		// switch (user_view) {
		//
		// case 1:
		// context.getRequestDispatcher("/auto.jsp").forward(request,
		// response);
		// break;
		//
		// case 2:
		// context.getRequestDispatcher("/muuda_auto.jsp").forward(
		// request, response);
		// break;
		//
		// case 3:
		// context.getRequestDispatcher("/otsi.jsp").forward(request,
		// response);
		// break;
		//
		// case 4:
		// context.getRequestDispatcher("/lisa_auto.jsp").forward(request,
		// response);
		// break;
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
		// case 7:
		// context.getRequestDispatcher("/login.jsp").forward(request,
		// response);
		// break;
		//
		// case 8:
		// context.getRequestDispatcher("/doc.jsp").forward(request,
		// response);
		// break;
		//
		// case 9:
		// context.getRequestDispatcher("/muuda_doc.jsp").forward(request,
		// response);
		// break;
		//
		// case 10:
		// context.getRequestDispatcher("/lisa_doc.jsp").forward(request,
		// response);
		// break;
		//
		// default:
		// context.getRequestDispatcher("/otsi.jsp").forward(request,
		// response);
		// break;
		//
		// }

	}
}