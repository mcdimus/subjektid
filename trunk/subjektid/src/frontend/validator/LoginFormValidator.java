package frontend.validator;

import java.util.HashMap;

import frontend.forms.LoginForm;

public class LoginFormValidator implements Validator {

	LoginForm form;
	HashMap<String, String> errors;

	public LoginFormValidator(LoginForm form) {
		this.form = form;
		this.errors = new HashMap<String, String>();
	}
	
	@Override
	public HashMap<String, String> getErrors() {
		return this.errors;
	}

	@Override
	public boolean validate() {
		boolean validUsername = this.validateUsername();
		boolean validPassword = this.validatePassword();

		return validUsername && validPassword;
	}

	private boolean validateUsername() {
		if (this.form.getUsername().isEmpty()) {
			errors.put("username", "Username cannot be empty!");
			return false;
		}
		return true;
	}

	private boolean validatePassword() {
		if (this.form.getPassword().isEmpty()) {
			errors.put("password", "Password cannot be empty!");
			return false;
		}
		return true;
	}

}
