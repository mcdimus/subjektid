package frontend.forms;

import java.util.ArrayList;

public class EmployeeForm extends HumanForm {

	String employeeId;
	ArrayList<EmployeeRoleForm> roles;
	FormAttribute[] employeeAttributes;
	AccountForm accForm;

	public EmployeeForm() { }
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public ArrayList<EmployeeRoleForm> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<EmployeeRoleForm> roles) {
		this.roles = roles;
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
	
	public AccountForm getAccForm() {
		return accForm;
	}

	public void setAccForm(AccountForm accForm) {
		this.accForm = accForm;
	}
	
}
