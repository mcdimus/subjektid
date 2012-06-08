package frontend.validator;

import frontend.forms.EmployeeForm;

public class EmployeeFormValidator extends Validator {

	EmployeeForm form;
	
	public EmployeeFormValidator(EmployeeForm f) {
		form = f;
	}
	
	@Override
	public void validate() {
		validateEmployeeRoleType();
		validateEnterpise();
	}

	
	private void validateEmployeeRoleType() {
		if (form.getRoles() == null) {
			errors.put("employee_role_type", "At least one employee role"
					+ " should be specified!");
		}
	}
	
	private void validateEnterpise() {
		if (form.getEnterprise().isEmpty()) {
			errors.put("enterprise", "Employee enterprise should be specified!");
		}
	}
	
}
