package frontend.validator;

import java.util.HashMap;

import frontend.forms.EnterpriseForm;

public class EnterpriseFormValidator implements Validator {
	
	EnterpriseForm form;
	HashMap<String, String> errors;
	
	public EnterpriseFormValidator(EnterpriseForm form) {
		this.form = form;
		this.errors = new HashMap<String, String>();
	}

	@Override
	public HashMap<String, String> getErrors() {
		return this.errors;
	}
	
	@Override
	public boolean validate() {
		boolean validateName = validateName(),
				validateFullName = validateFullName();
		return validateName && validateFullName;
	}
	
	private boolean validateName() {
		if (form.getName().length() == 0) {
			errors.put("name", "Enterprise name is empty!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateFullName() {
		if (form.getFullName().length() == 0) {
			errors.put("full_name", "Enterprise full name is empty!");
			return false;
		} else {
			return true;
		}
	}

}