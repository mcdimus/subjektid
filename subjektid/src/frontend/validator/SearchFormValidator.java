package frontend.validator;

import java.util.Date;
import java.util.HashMap;

import frontend.forms.SearchForm;

public class SearchFormValidator extends Validator {

	SearchForm form;
	
	public SearchFormValidator(SearchForm form) {
		this.form = form;
	}

	@Override
	public void validate() {
		validateNumbers();
		validateDates();
	}
	
	private void validateNumbers() {
		HashMap<String, String> criterias = new HashMap<String, String>();
		for (String key : criterias.keySet()) {
			try {
				Integer.parseInt(criterias.get(key));
			} catch (NumberFormatException e) {
				errors.put(key, "This is not a number!");
				criterias.remove(key);
			}
		}
	}

	private void validateDates() {
	   	HashMap<String, String> criterias = new HashMap<String, String>();
		for (String key : criterias.keySet()) {
			Date date = validateDate(criterias.get(key));
			
			if (date.after(new Date())) {
				errors.put(key, "Date cannot be more than today or equal!");
				criterias.remove(key);
			}
		}
	}
}
