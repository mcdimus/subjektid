package frontend.forms;

public class EmployeeForm extends HumanForm {

	String employeeId;
	String employeeRoleType;
	String enterprise;
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

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	
	public FormAttribute[] getEmployeeAttributes() {
		return employeeAttributes;
	}
	
	public void setEmployeeAttributes(FormAttribute[] attrs) {
		employeeAttributes = attrs;
	}
	
}
