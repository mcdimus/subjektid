package frontend.control;

import java.util.Map;

public class ControllerFactory {

	public Controller getController(Map<String, String[]> params) {

		Controller controller;
		
		String mode = params.get("mode")[0];
		String action = params.get("action")[0];
		
		if (mode.equals("login")) {
			controller = new LoginController();
			controller.setAction(action);
		} else if (mode.equals("add_new_subject")) {
			
		} else {
			
		}
		
		if (params.equals("login_event") || params.equals("logout_event")) {
			controller = new LoginController();
		} else if (params.equals("add_new_subject_event")) {
			controller = new SubjectController();
		} else {
			controller = new LoginController();
		}
		
		return controller;
	}

}
