package frontend.control;

import java.util.Map;

import log.MyLogger;

public class EventFinder {

	public String find(Map<String, String[]> params) {
		String event = "undefined";
		try {

			if (params.containsKey("action")) {
				String action = params.get("action")[0];
				if (action.equals("logout")) {
					event = "logout_event";
				} else if (action.equals("login")) {
					event = "login_event";
				} else if (action.equals("add_new_subject")) {
					event = "add_new_subject_event";
				}

			}
		} catch (Exception ex) {
			MyLogger.log("EventFinder.find():", ex.getMessage());
		}

		return event;
	}
}
