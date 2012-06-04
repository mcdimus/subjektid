package frontend.forms;

public class ContactForm {

	private String contactType;
	private String contact;
	private String contactId;
	
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
	
}
