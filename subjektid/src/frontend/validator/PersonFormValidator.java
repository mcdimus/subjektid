package frontend.validator;

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
		// TODO Auto-generated method stub
		return false;
	}

}
