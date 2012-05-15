package frontend.control;

public class ControllerFactory {

	public Controller getController(String event) {

		Controller controller;
		
		if (event.equals("login_event") || event.equals("logout_event")) {
			controller = new LoginController();
		} else if (event.equals("add_new_subject_event")) {
			controller = new SubjectController();
		} else {
			controller = new LoginController();
		}
		
		return controller;
	}

}
