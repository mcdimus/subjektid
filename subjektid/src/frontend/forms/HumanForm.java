package frontend.forms;

public abstract class HumanForm extends SubjectForm {

	String firstName;
	String lastName;
	String identityCode;
	String birthDate;
	
	public HumanForm() { }

	/**
	 * @return the firstName
	 */
	public final String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public final String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the identityCode
	 */
	public final String getIdentityCode() {
		return identityCode;
	}

	/**
	 * @param identityCode the identityCode to set
	 */
	public final void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	/**
	 * @return the birthDate
	 */
	public final String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public final void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
}