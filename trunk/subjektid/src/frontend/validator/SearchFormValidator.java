package frontend.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import frontend.forms.SearchForm;

public class SearchFormValidator implements Validator {

	SearchForm form;
	HashMap<String, String> errors;
	
	public SearchFormValidator(SearchForm form) {
		this.form = form;
		this.errors = new HashMap<String, String>();
	}
	
	@Override
	public HashMap<String, String> getErrors() {
		return null;
	}

	@Override
	public boolean validate() {
		boolean validateNumbers = validateNumbers(),
				validateDates = validateDates();
		return validateNumbers && validateDates;
	}
	
	private boolean validateNumbers() {
		boolean answer = true;
		HashMap<String, String> criterias = new HashMap<String, String>();
		for (String key : criterias.keySet()) {
			try {
				Integer.parseInt(criterias.get(key));
			} catch (NumberFormatException e) {
				errors.put(key, "This is not a number!");
				criterias.remove(key);
				answer = false;
			}
		}
		return answer;
	}

	private boolean validateDates() {
		boolean answer = true;
		
		// Zero out the hour, minute, second, and millisecond
		Calendar currDtCal = Calendar.getInstance();
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);
	   	Date today = currDtCal.getTime();
		
	   	HashMap<String, String> criterias = new HashMap<String, String>();
		for (String key : criterias.keySet()) {
			try {
				Date date = new SimpleDateFormat("dd.MM.yyyy").parse(criterias.get(key));
				
			   	if (!date.before(today)) {
					errors.put(key, "Date cannot be more than today or equal!");
					criterias.remove(key);
					answer = false;
				}
			} catch (ParseException e) {
				errors.put(key, "This is not a valid date!");
				criterias.remove(key);
				answer = false;
			}
		}
		return answer;
	}
}
