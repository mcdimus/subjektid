package frontend.forms;

public class EnterpriseForm extends SubjectForm {
	
	private String name;
	private String fullName;
	private FormAttribute[] attributes;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public FormAttribute[] getAttributes() {
		return attributes;
	}
	
	public void setAttributes(FormAttribute[] attrs) {
		attributes = attrs;
	}

}
