package frontend.forms;

public class EmployeeForm extends HumanForm {

	String employeeId;
	String employeeRoleType;
	FormAttribute[] employeeAttributes;
	
	public EmployeeForm() { }
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeRoleType() {
		return employeeRoleType;
	}

	public void setEmployeeRoleType(String employeeRoleType) {
		this.employeeRoleType = employeeRoleType;
	}
	
	public FormAttribute[] getEmployeeAttributes() {
		return employeeAttributes;
	}
	
	public void setEmployeeAttributes(FormAttribute[] attrs) {
		employeeAttributes = attrs;
	}

	public String getCustomer() { 
		return null;
	}
	
	public void setCustomer(String c) { }
	
	public String getCustomerId() { 
		return null;
	}
	
	public void setCustomerId(String c) { }

	public FormAttribute[] getCustromerAttributes() {
		return null;
	}
	
	public void setCustomerAttributes(FormAttribute[] attrs) { }
	
}
