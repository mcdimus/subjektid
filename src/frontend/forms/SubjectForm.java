package frontend.forms;

import java.util.ArrayList;

public abstract class SubjectForm {

	String subjectId;
	String createdBy;
	String updatedBy;
	ArrayList<AddressForm> addressForms;
	
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
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
	public final ArrayList<AddressForm> getAddressForms() {
		return addressForms;
	}

	/**
	 * @param addressForm the addressForm to set
	 */
	public final void setAddressForms(ArrayList<AddressForm> addressForms) {
		this.addressForms = addressForms;
	}
	
}
