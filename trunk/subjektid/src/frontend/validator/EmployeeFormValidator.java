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
		if (form.getEmployeeRoleType() == null || form.getEmployeeRoleType()
				.equals("")) {
			errors.put("employee_role_type", "Employee role should be specified!");
		}
	}
	
	private void validateEnterpise() {
		if (form.getEnterprise() == null || form.getEnterprise().equals("")) {
			errors.put("enterprise", "Employee enterprise should be specified!");
		}
	}
	
}
