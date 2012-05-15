package frontend.validator;

import java.util.HashMap;

import frontend.forms.AddressForm;

public class AddressFormValidator implements Validator{
	
	AddressForm form;
	HashMap<String, String> errors;
	
	public AddressFormValidator(AddressForm form) {
		this.form = form;
		this.errors = new HashMap<String, String>();
	}

	@Override
	public HashMap<String, String> getErrors() {
		// TODO Auto-generated method stub
		return this.errors;
	}
	
	@Override
	public boolean validate() {

		return false;
	}

}
