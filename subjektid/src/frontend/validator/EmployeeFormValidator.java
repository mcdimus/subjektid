package frontend.validator;

import frontend.forms.EmployeeForm;
import general.Utils;

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
//		if (Utils.checkEmpty(form.getEmployeeRoleType())) {
//			errors.put("employee_role_type", "Employee role should be specified!");
//		}
	}
	
	private void validateEnterpise() {
		if (Utils.checkEmpty(form.getEnterprise())) {
			errors.put("enterprise", "Employee enterprise should be specified!");
		}
	}
	
}
