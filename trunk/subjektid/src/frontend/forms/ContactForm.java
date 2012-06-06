package frontend.forms;

public class ContactForm {

	private String contactId;
	private String contactType;
	private String contact;
	private String orderBy;
	private String note;
	
	
	public String getContactType() {
		return contactType;
	}
	
	public void setContactType(String type) {
		contactType = type;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String c) {
		contact = c;
	}
	
	public String getContactId() {
		return contactId;
	}
	
	public void setContactId(String id) {
		contactId = id;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
}
