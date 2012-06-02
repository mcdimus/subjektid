package frontend.forms;

import java.util.ArrayList;

public class SearchForm {

	private String subjectType;
	private String firstName, lastName;
	private AddressForm addressForm;
	private ArrayList<SearchAttribute> attributes;
	private boolean notEmptyPerson, notEmptyEnterprise;
	private String[] query = { "", "", "", "" };
	
	public SearchForm() { }
	
	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjType) {
		subjectType = subjType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fname) {
		firstName = fname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lname) {
		lastName = lname;
	}

	public AddressForm getAddressForm() {
		return addressForm;
	}

	public void setAddressForm(AddressForm form) {
		addressForm = form;
	}

	public ArrayList<SearchAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes (ArrayList<SearchAttribute> attrs) {
		attributes = attrs;
	}
	
	public boolean isPersonNotEmpty() {
		return notEmptyPerson;
	}
	
	public void setPersonNotEmpty() {
		notEmptyPerson = true;
	}
	
	public boolean isEnterpriseNotEmpty() {
		return notEmptyEnterprise;
	}
	
	public void setEnterpriseNotEmpty() {
		notEmptyEnterprise = true;
	}
	
	public String getQueryPart(int i) {
		return query[i];
	}
	
	public void setQueryPart(String queryPart, int i) {
		query[i] = queryPart;
	}

}