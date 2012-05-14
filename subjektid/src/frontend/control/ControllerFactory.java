package frontend.control;

public class ControllerFactory {

	public Controller getController(String event) {

		Controller controller;
		
		if (event.equals("login_event") || event.equals("logout_event")) {
			controller = new LoginController();
		} else {
			controller = new LoginController();
		}
		
		return controller;
	}

}
