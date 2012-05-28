package frontend.validator;

import frontend.forms.LoginForm;

public class LoginFormValidator extends Validator {

	LoginForm form;

	public LoginFormValidator(LoginForm form) {
		this.form = form;
	}

	@Override
	public void validate() {
		validateUsername();
		validatePassword();
	}

	private void validateUsername() {
		if (this.form.getUsername().isEmpty()) {
			errors.put("username", "Username cannot be empty!");
		}
	}

	private void validatePassword() {
		if (this.form.getPassword().isEmpty()) {
			errors.put("password", "Password cannot be empty!");
		}
	}

}
