package frontend.control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

				SubjectsORM orm = new SubjectsORM();

				this.getAllAccounts(req);
				view = "accounts_view";
			} else if (action.equals("edit")) {

			} else if (action.equals("add")) {

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				UserAccount account = new UserAccount();

				try {
					account.setUsername(params.get("username")[0]);
					account.setPassw(md5(params.get("password")[0]));
					account.setStatus(Long.valueOf(params.get("status")[0]));
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
				} catch (NoSuchAlgorithmException e) {
					MyLogger.log("AccountController: add new", e.getMessage());
					System.exit(0);
				}

				SubjectsORM orm = new SubjectsORM();
				orm.saveOrUpdate(account);

				req.setAttribute("message",
						"New account for " + account.getUsername() + " saved");
				this.getAllAccounts(req);
				view = "accounts_view";
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

	private String md5(String message) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(message.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}
}
