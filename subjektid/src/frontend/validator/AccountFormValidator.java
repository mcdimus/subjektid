package frontend.validator;

import java.util.Date;

import frontend.forms.AccountForm;
import general.Utils;

public class AccountFormValidator extends Validator {
	
	AccountForm form;
	
	public AccountFormValidator(AccountForm form) {
		this.form = form;
	}

	@Override
	public void validate() {
		validateUsername();
		validatePassword();
		validateValidFromAndValidTo();
		validatePasswordNeverExpires();
	}
	
	private void validateUsername() {
		String username = form.getUsername();
		if (username.isEmpty()) {
			errors.put("username", "Username is empty!");
		} else if (username.length() > 11) {
			errors.put("username", "Username should not exceed 11 symbols!!");
		} else if (username.matches("[A-Za-z0-9]+")) {
			errors.put("username", "Username may contain only letters and numbers!!");
		}
	}
	
	private void validatePassword() {
		String password = form.getPassword();
		if (password.isEmpty()) {
			errors.put("password", "Password is empty!");
		} else if (password.length() < 4) {
			errors.put("password", "Password should be at least 4 symbols!");
		}
	}
	
	private void validateValidFromAndValidTo() {
		Date validFrom = Utils.parseDate(form.getValidFrom());
		Date validTo = Utils.parseDate(form.getValidTo());
		
		// validate validFrom
		if (validFrom == null) {
			errors.put("validFrom", "Invalid format of the date! (21.06.1970)");
		} else if (validFrom.before(new Date())) {
			errors.put("validFrom", "The date cannot be in the past");
		}
		
		// validate validTo
		if (validTo == null) {
			errors.put("validTo", "Invalid format of the date! (21.06.1970)");
		} else if (validTo.before(new Date())) {
			errors.put("validTo", "The date cannot be in the past");
		} else if (validTo.before(validFrom)) {
			errors.put("validTo", "The date should be after the Valid From date");
		}
	}
	
	private void validatePasswordNeverExpires() {
		String passNeverExp = form.getPasswordNeverExpires();
		if (passNeverExp == null) {
			errors.put("passwordNeverExpires", "Password Never Expires is empty!");
		} else if (passNeverExp.matches("0|1")) {
			errors.put("passwordNeverExpires", "The value should either 0 or 1!");
		}
	}

}
