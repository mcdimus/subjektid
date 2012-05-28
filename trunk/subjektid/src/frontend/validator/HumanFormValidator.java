package frontend.validator;

import java.util.Date;

import frontend.forms.HumanForm;
import general.Utils;

public class HumanFormValidator extends Validator {

	HumanForm form;

	public HumanFormValidator(HumanForm form) {
		this.form = form;
	}

	@Override
	public void validate() {
		validateFirstName();
		validateLastName();
		validateIdentityCode();
		validateBirthDate();
	}

	private void validateFirstName() {
		if (Utils.checkEmpty(form.getFirstName())) {
			errors.put("first_name", "First name is empty");
		} else if (!form.getFirstName().matches("[A-Za-z]+[-_]?[A-Za-z]+")) {
			errors.put("first_name", "First name contains non-letters!");
		} else if (form.getFirstName().length() > 100) {
			errors.put("first_name", "First name should not exceed 100 symbols!");
		}
	}
	
	private void validateLastName() {
		if (Utils.checkEmpty(form.getLastName())) {
			errors.put("last_name", "Last name is empty");
		} else if (!form.getLastName().matches("[A-Za-z]+[-_]?[A-Za-z]+")) {
			errors.put("last_name", "Last name contains non-letters!");
		} else if (form.getLastName().length() > 100) {
			errors.put("last_name", "Last name should not exceed 100 symbols!");
		}
	}
	
	private void validateIdentityCode() {
		if (Utils.checkEmpty(form.getIdentityCode())) {
			errors.put("identity_code", "Identity code is empty!");
		} else if (!form.getIdentityCode().matches("\\w+")) {
			errors.put("identity_code", "Identity code"
					+ " contains non-word characters!");
		} else if (form.getIdentityCode().length() > 20) {
			errors.put("identity_code", "Identity code should not exceed 20 symbols!");
		}
	}
	
	private void validateBirthDate() {
		if (Utils.checkEmpty(form.getIdentityCode())) {
			errors.put("birthdate", "Birthdate is empty!");
		} else {
			Date birthDate = Utils.parseDate(form.getBirthDate());
			if (birthDate != null) {
				if (birthDate.after(new Date())) {
					errors.put("birthdate", "Birthdate cannot be more than today!");
				}
			} else {
				errors.put("birthdate", "Birthdate is in the wrong format!");
			}
		}
	}
	
}
