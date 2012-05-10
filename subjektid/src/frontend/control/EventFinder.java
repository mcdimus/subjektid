package frontend.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.MyLogger;

public class EventFinder {

	public String find(HttpServletRequest request, HttpServletResponse response) {
		String event = "undefined";
		try {
			if (request.getParameter("action") != null) {
				event = "undefined";
				if (request.getParameter("action").equals("update_auto")) {
					event = "update_auto";
				}

				if (request.getParameter("action").equals("save_new_auto")) {
					event = "insert_auto";
				}

				if (request.getParameter("action").equals("search_doc")) {
					event = "search_doc";
				}

				if (request.getParameter("action").equals("update_doc")) {
					event = "show_doc";
				}

			} else {
				if (request.getParameter("sub_mode") != null) {
					event = "undefined";
					if (request.getParameter("sub_mode").equals("vaata_autot")) {
						event = "show_auto";
					}
					if (request.getParameter("sub_mode").equals("autod")) {
						event = "show_autos";
					}
					if (request.getParameter("sub_mode").equals("lisa")) {
						event = "add_auto_form";
					}

					if (request.getParameter("sub_mode").equals("vaata_doc")) {
						event = "show_doc";
					}

					if (request.getParameter("sub_mode").equals("add_doc")) {
						event = "add_doc";
					}

					if (request.getParameter("sub_mode").equals("docs")) {
						event = "doc_search_page";
					}

				}

			}

		} catch (Exception ex) {
			MyLogger.log("EventFinder.find():", ex.getMessage());
		}

		return event;
	}
}
