package frontend.control;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

	public Controller getController(Map<String, String[]> params) {

		Controller controller;

		String mode = "undefined";
		String action = "undefined";

		if (params.containsKey("mode")) {
			mode = params.get("mode")[0];
		}
		if (params.containsKey("action")) {
			action = params.get("action")[0];
		}

		if (mode.equals("subject")) {
			controller = new SubjectController();
		} else if (mode.equals("search")) {
			controller = new SearchController();
		} else if (mode.equals("account")) {
			controller = new AccountController();
		} else {
			controller = new LoginController();
		}

		controller.setParams((HashMap<String, String[]>) params);
		controller.setAction(action);
		return controller;
	}

}
