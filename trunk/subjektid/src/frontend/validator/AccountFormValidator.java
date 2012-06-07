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
		validateUsernameAndPassword();
		validateUsername();
		validatePassword();
		validateValidFromAndValidTo();
	}
	
	private void validateUsernameAndPassword() {
		if (form.getAccountId().isEmpty()) {
			if (!form.getUsername().isEmpty()) {
				errors.put("username", "Username is empty!");
			} else if (!form.getPassword().isEmpty()) {
				errors.put("password", "Password is empty!");
			}
		} else {
			if (form.getUsername().isEmpty() && !form.getPassword().isEmpty()) {
				errors.put("username", "Username is empty!");
			}
		}
	}
	
	private void validateUsername() {
		String username = form.getUsername();
		if (username.length() > 50) {
			errors.put("username", "Username should not exceed 50 symbols!!");
		} else if (!username.matches("\\w*")) {
			errors.put("username", "Username may contain only letters and numbers!!");
		}
	}
	
	private void validatePassword() {
		String password = form.getPassword();
		if (password.length() > 300) {
			errors.put("password", "Password should not exceed 300 symbols!");
		}
	}
	
	private void validateValidFromAndValidTo() {
		Date validFrom = Utils.parseDate(form.getValidFrom());
		Date validTo = Utils.parseDate(form.getValidTo());
		
		// validate validFrom
		if (validFrom == null) {
			errors.put("valid_from", "Invalid format of the date! (21.06.1970)");
		} else if (validFrom.before(new Date())) {
			errors.put("valid_from", "The date cannot be in the past");
		}
		
		// validate validTo
		if (validTo == null) {
			errors.put("valid_to", "Invalid format of the date! (21.06.1970)");
		} else if (validTo.before(new Date())) {
			errors.put("valid_to", "The date cannot be in the past");
		} else if (validFrom != null && validTo.before(validFrom)) {
			errors.put("valid_to", "The date should be after the Valid From date");
		}
	}

}
