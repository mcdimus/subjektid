package frontend.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchController extends Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {
		
		return "search";
	}

}
