package frontend.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public abstract class Validator {
	
	static HashMap<String, String> errors = new HashMap<String, String>();

	public final static HashMap<String, String> getErrors() {
		return errors;
	}

	public abstract void validate();
	
	final Date validateDate(String date) {
		String[] formatStrings = {"d.M.y", "d/M/y", "d-M-y"};
		
		for (String formatString : formatStrings) {
			try {
				return new SimpleDateFormat(formatString).parse(date);
			} catch (ParseException e) { }
		}
		return null;
	}

}
