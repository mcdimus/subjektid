package frontend.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

	public String service(String event, HttpServletRequest req, HttpServletResponse resp);
}
