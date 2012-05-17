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
		return this.errors;
	}
	
	@Override
	public boolean validate() {
		boolean validateCountry = validateCountry(),
				validateCounty = validateCounty(),
				validateTownVillage = validateTownVillage(),
				validateStreetAddress = validateStreetAddress(),
				validateZipcode = validateZipcode();
		return validateCountry && validateCounty && validateTownVillage
				&& validateStreetAddress && validateZipcode;
	}
	
	private boolean validateCountry() {
		if (!form.getCountry().matches("[A-Za-z]+")) {
			errors.put("country", "Country name is empty / contains non-letters!");
			return false;
		} else if (form.getCountry().length() > 50) {
			errors.put("country", "Country name should not exceed 50 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCounty() {
		if (!form.getCounty().matches("[A-Za-z]+")) {
			errors.put("county", "County name is empty / contains non-letters!");
			return false;
		} else if (form.getCounty().length() > 100) {
			errors.put("county", "County name should not exceed 100 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateTownVillage() {
		if (!form.getTownVillage().matches("[A-Za-z]+")) {
			errors.put("town_village", "Town/village name is empty / contains non-letters!");
			return false;
		} else if (form.getTownVillage().length() > 100) {
			errors.put("town_village", "Town/village name should not exceed 100 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateStreetAddress() {
		if (form.getStreetAddress().length() > 100) {
			errors.put("street_address", "Street address should not exceed"
					+ " 100 symbols!");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateZipcode() {
		if (!form.getZipcode().matches("\\d+")) {
			errors.put("zipcode", "Zipcode is empty / contains non-numbers!");
			return false;
		} else if (form.getZipcode().length() > 50) {
			errors.put("zipcode", "Zipcode should not exceed 50 symbols!");
			return false;
		} else {
			return true;
		}
	}

}
