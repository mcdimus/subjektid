package frontend.forms;

import java.util.ArrayList;

public abstract class SubjectForm {

	String subjectId;
	String createdBy;
	String updatedBy;
	AddressForm addressForm;
	ArrayList<AddressForm> addresses;
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

	/**
	 * @return the updatedBy
	 */
	public final String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public final void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
	
	public final ArrayList<AddressForm> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<AddressForm> addresses) {
		this.addresses = addresses;
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
