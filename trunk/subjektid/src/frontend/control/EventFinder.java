package frontend.control;

import java.util.Map;

import log.MyLogger;

public class EventFinder {

	public String find(Map<String, String[]> params) {
		String event = "undefined";
		try {
			// if (req.getParameter("action") != null) {
			// event = "undefined";
			// if (req.getParameter("action").equals("update_auto")) {
			// event = "update_auto";
			// }
			//
			// if (req.getParameter("action").equals("save_new_auto")) {
			// event = "insert_auto";
			// }
			//
			// if (req.getParameter("action").equals("search_doc")) {
			// event = "search_doc";
			// }
			//
			// if (req.getParameter("action").equals("update_doc")) {
			// event = "show_doc";
			// }
			//
			// } else {
			// if (req.getParameter("sub_mode") != null) {
			// event = "undefined";
			// if (req.getParameter("sub_mode").equals("vaata_autot")) {
			// event = "show_auto";
			// }
			// if (req.getParameter("sub_mode").equals("autod")) {
			// event = "show_autos";
			// }
			// if (req.getParameter("sub_mode").equals("lisa")) {
			// event = "add_auto_form";
			// }
			//
			// if (req.getParameter("sub_mode").equals("vaata_doc")) {
			// event = "show_doc";
			// }
			//
			// if (req.getParameter("sub_mode").equals("add_doc")) {
			// event = "add_doc";
			// }
			//
			// if (req.getParameter("sub_mode").equals("docs")) {
			// event = "doc_search_page";
			// }
			//
			// }
			//
			// }

			if (params.containsKey("action")) {
				if (params.get("action")[0].equals("logout")) {
					event = "logout_event";
				} else if (params.get("action")[0].equals("login")) {
					event = "login_event";
				}

			}
		} catch (Exception ex) {
			MyLogger.log("EventFinder.find():", ex.getMessage());
		}

		return event;
	}
}
