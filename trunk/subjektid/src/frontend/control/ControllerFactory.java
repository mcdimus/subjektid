package frontend.control;

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

		if (mode.equals("login")) {
			controller = new LoginController();
		} else if (mode.equals("adding_new_subject")) {
			controller = new SubjectController();
		} else {
			controller = new LoginController();

		}

		controller.setAction(action);
		return controller;
	}

}
