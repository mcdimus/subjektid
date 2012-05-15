package frontend.validator;

import java.util.HashMap;

public interface Validator {

	public HashMap<String, String> getErrors();

	public boolean validate();

}
