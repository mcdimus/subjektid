package frontend.validator;

import java.util.Calendar;
import java.util.Date;

import frontend.forms.HumanForm;

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
		if (!form.getFirstName().matches("[A-Za-z]+")) {
			errors.put("first_name", "First name is empty / contains non-letters!");
		} else if (form.getFirstName().length() > 100) {
			errors.put("first_name", "First name should not exceed 100 symbols!");
		}
	}
	
	private void validateLastName() {
		if (!form.getLastName().matches("[A-Za-z]+")) {
			errors.put("last_name", "Last name is empty / contains non-letters!");
		} else if (form.getLastName().length() > 100) {
			errors.put("last_name", "Last name should not exceed 100 symbols!");
		}
	}
	
	private void validateIdentityCode() {
		if (!form.getIdentityCode().matches("\\w+")) {
			errors.put("identity_code", "Identity code is empty /"
					+ " contains non-word characters!");
		} else if (form.getIdentityCode().length() > 20) {
			errors.put("identity_code", "Identity code should not exceed 20 symbols!");
		}
	}
	
	private void validateBirthDate() {
		Date birthDate = validateDate(form.getBirthDate()), today;
		if (birthDate != null) {
			// Zero out the hour, minute, second, and millisecond
			Calendar currDtCal = Calendar.getInstance();
		    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    currDtCal.set(Calendar.MINUTE, 0);
		    currDtCal.set(Calendar.SECOND, 0);
		    currDtCal.set(Calendar.MILLISECOND, 0);
		   	today = currDtCal.getTime();
			
		   	if (!birthDate.before(today)) {
				errors.put("birthdate", "Birthdate cannot be more than today or equal!");
			}
		} else {
			errors.put("birthdate", "Birthdate is empty / in the wrong format");
		}
	}
	
}
