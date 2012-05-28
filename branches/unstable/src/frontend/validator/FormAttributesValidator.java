package frontend.validator;

import java.util.Date;

import frontend.forms.FormAttribute;
import general.Utils;


public class FormAttributesValidator extends Validator {
	
	FormAttribute[] attributes;
	Date today;
	
	public FormAttributesValidator(FormAttribute[] attrs) {
		attributes = attrs;
	}
	
	@Override
	public void validate() {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getRequired()
					|| !Utils.checkEmpty(attributes[i].getValue())) {
				if (attributes[i].getType() == 1) {
					validateString(attributes[i]);
				} else if (attributes[i].getType() == 2) {
					validateNumber(attributes[i]);
				} else if (attributes[i].getType() == 3) {
					validateDate(attributes[i]);
				}
			}
		}
	}
	
	private void validateString(FormAttribute attr) {
		if (Utils.checkEmpty(attr.getValue())) {
			errors.put(attr.getName(), attr.getName() + " is empty!");
		} else if (attr.getValue().length() > 100) {
			errors.put(attr.getName(), attr.getName()
					+ " is too long (100 chars max)!");
		}
	}

	private void validateNumber(FormAttribute attr) {
		if (Utils.checkEmpty(attr.getValue())) {
			errors.put(attr.getName(), attr.getName() + " is empty!");
		} else {
			try {
				Long.parseLong(attr.getValue());
			} catch (NumberFormatException e) {
				errors.put(attr.getName(), attr.getName() + " is not a number!");
			}
		}
	}

	private void validateDate(FormAttribute attr) {
		if (Utils.checkEmpty(attr.getValue())) {
			errors.put(attr.getName(), attr.getName() + " is empty!");
		} else {
			Date date = Utils.parseDate(attr.getValue());
			if (date != null) {
			   	if (date.after(new Date())) {
					errors.put(attr.getName(), "Date cannot be more than today!");
				}
			} else {
				errors.put(attr.getName(), "Date is in the wrong format");
			}
		}
	}

}
