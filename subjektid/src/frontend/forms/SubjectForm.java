package frontend.forms;

public abstract class SubjectForm {

	String subjectId;
	String createdBy;
	String updatedBy;
	
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
	
}
