package frontend.validator;

import frontend.forms.EnterpriseForm;
import general.Utils;

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
		if (Utils.checkEmpty(form.getName())) {
			errors.put("name", "Enterprise name is empty!");
		}
	}
	
	private void validateFullName() {
		if (Utils.checkEmpty(form.getFullName())) {
			errors.put("full_name", "Enterprise full name is empty!");
		}
	}

}