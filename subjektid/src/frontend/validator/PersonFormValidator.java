package frontend.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import frontend.forms.PersonForm;

public class PersonFormValidator implements Validator {

	PersonForm form;
	HashMap<String, String> errors;

	public PersonFormValidator(PersonForm form) {
		this.form = form;
		this.errors = new HashMap<String, String>();
	}

	@Override
	public HashMap<String, String> getErrors() {
		return this.errors;
	}

	@Override
	public boolean validate() {
		boolean validateFirstName = validateFirstName(),
				validateLastName = validateLastName(),
				validateIdentityCode = validateIdentityCode(),
				validateBirthDate = validateBirthDate();
		return validateFirstName && validateLastName && validateIdentityCode
				&& validateBirthDate;
	}

	private boolean validateFirstName() {
		if (!form.getFirstName().matches("[A-Za-z]+")) {
			errors.put("first_name", "First name is empty / contains non-letters!");
			return false;
		} else if (form.getFirstName().length() > 100) {
			errors.put("first_name", "First name should not exceed 100 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLastName() {
		if (!form.getLastName().matches("[A-Za-z]+")) {
			errors.put("last_name", "Last name is empty / contains non-letters!");
			return false;
		} else if (form.getLastName().length() > 100) {
			errors.put("last_name", "Last name should not exceed 100 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateIdentityCode() {
		if (!form.getIdentityCode().matches("\\w+")) {
			errors.put("identity_code", "Identity code is empty /"
					+ " contains non-word characters!");
			return false;
		} else if (form.getIdentityCode().length() > 20) {
			errors.put("identity_code", "Identity code should not exceed 20 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateBirthDate() {
		Date birthDate, today;
		try {
			birthDate = new SimpleDateFormat("dd.MM.yyyy").parse(form.getBirthDate());
			
			// Zero out the hour, minute, second, and millisecond
			Calendar currDtCal = Calendar.getInstance();
		    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    currDtCal.set(Calendar.MINUTE, 0);
		    currDtCal.set(Calendar.SECOND, 0);
		    currDtCal.set(Calendar.MILLISECOND, 0);
		   	today = currDtCal.getTime();
			
		   	if (!birthDate.before(today)) {
				errors.put("birthdate", "Birthdate cannot be more than today or equal!");
		   		return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			errors.put("birthdate", "Birthdate is empty / in the wrong format");
			return false;
		}
	}
	
}
