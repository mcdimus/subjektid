package frontend.forms;

import java.util.ArrayList;
import java.util.Date;

public abstract class SubjectForm {

	String subjectId;
	String createdBy, updatedBy;
	Date created, updated;
	AddressForm addressForm;
	ArrayList<ContactForm> contacts;
	FormAttribute[] attributes;
	FormAttribute[] customerAttributes;
	String customer;
	String customerId;

	public final String getSubjectId() {
		return subjectId;
	}

	public final void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return the createdBy
	 */
	public final String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public final void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the created
	 */
	public final Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public final void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the addressForm
	 */
	public final AddressForm getAddressForm() {
		return addressForm;
	}

	/**
	 * @param addressForm the addressForm to set
	 */
	public final void setAddressForm(AddressForm addressForm) {
		this.addressForm = addressForm;
	}
	public final ArrayList<ContactForm> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<ContactForm> contacts) {
		this.contacts = contacts;
	}
	
	public final FormAttribute[] getAttributes() {
		return attributes;
	}
	
	public final void setAttributes(FormAttribute[] attrs) {
		attributes = attrs;
	}
	
	public FormAttribute[] getCustromerAttributes() {
		return customerAttributes;
	}
	
	public void setCustomerAttributes(FormAttribute[] attrs) {
		customerAttributes = attrs;
	}

	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String c) {
		customer = c;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String c) {
		customerId = c;
	}
	
}
