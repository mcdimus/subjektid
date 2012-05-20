package frontend.validator;

import frontend.forms.EnterpriseForm;

public class EnterpriseFormValidator extends Validator {
	
	EnterpriseForm form;
	
	public EnterpriseFormValidator(EnterpriseForm form) {
		this.form = form;
	}
	
	@Override
	public void validate() {
		validateName();
		validateFullName();
	}
	
	private void validateName() {
		if (form.getName().length() == 0) {
			errors.put("name", "Enterprise name is empty!");
		}
	}
	
	private void validateFullName() {
		if (form.getFullName().length() == 0) {
			errors.put("full_name", "Enterprise full name is empty!");
		}
	}

}