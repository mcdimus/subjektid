package frontend.validator;

import java.util.HashMap;

public abstract class Validator {
	
	static HashMap<String, String> errors = new HashMap<String, String>();

	public final static HashMap<String, String> getErrors() {
		return errors;
	}

	public abstract void validate();

}
