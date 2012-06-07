package frontend.control;

import general.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.MyLogger;

import backend.DA.SubjectsORM;
import backend.model.UserAccount;

public class AccountController extends Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {
		String view = "default_view";
		SessionManager sessionManager = new SessionManager(req);
		if (sessionManager.loggedIn()) {

			if (action.equals("view")) {

				this.getAllAccounts(req);
				view = "accounts_view";
			} else if (action.equals("delete")) {
				this.getAllAccounts(req);
				view = "accounts_view";
			} else if (action.equals("edit")) {

			} else if (action.equals("form")) {

				
				view = "accountForm_view";
			} else if (action.equals("add")) {

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				UserAccount account = new UserAccount();

				try {
					account.setUsername(params.get("username")[0]);
					account.setPassw(Utils.generateMD5(params.get("username")[0]
							+ params.get("password")[0]));
					account.setStatus(params.get("status")[0]);
					account.setValidFrom(df.parse(params.get("validFrom")[0]));
					account.setValidTo(df.parse(params.get("validTo")[0]));
					account.setCreatedBy(Long.valueOf(params.get("createdBy")[0]));
					account.setCreated(new Date(Long.valueOf(params
							.get("created")[0])));
					account.setPasswordNeverExpires(params
							.get("passwordNeverExpires")[0]);
				} catch (NumberFormatException e) {
					MyLogger.log("AccountController: add new", e.getMessage());
					System.exit(0);
				} catch (ParseException e) {
					MyLogger.log("AccountController: add new", e.getMessage());
					System.exit(0);
				} catch (NullPointerException e) {
					MyLogger.log("AccountController: add new", e.getMessage());
					System.exit(0);
				}

				SubjectsORM orm = new SubjectsORM();
				orm.saveOrUpdate(account);

				req.setAttribute("message",
						"New account for " + account.getUsername() + " saved");

				view = "accountForm_view";
			}

		} else {
			view = "login_view";
		}
		return view;
	}

	private void getAllAccounts(HttpServletRequest req) {
		SubjectsORM orm = new SubjectsORM();
		List<UserAccount> accounts = orm.findAll(UserAccount.class);

		req.setAttribute("accounts", accounts);
	}
}
