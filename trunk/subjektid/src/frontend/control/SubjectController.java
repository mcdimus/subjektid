package frontend.control;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.forms.AddressForm;
import frontend.forms.EmployeeForm;
import frontend.forms.EnterpriseForm;
import frontend.forms.FormAttribute;
import frontend.forms.HumanForm;
import frontend.forms.PersonForm;
import frontend.validator.AddressFormValidator;
import frontend.validator.EmployeeFormValidator;
import frontend.validator.EnterpriseFormValidator;
import frontend.validator.FormAttributesValidator;
import frontend.validator.HumanFormValidator;
import frontend.validator.Validator;

import log.MyLogger;

import backend.DA.SubjectsORM;
import backend.model.EmployeeRoleType;
import backend.model.Enterprise;
import backend.model.SubjectAttributeType;

public class SubjectController extends Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {

		String view = "default_view";
		SessionManager sessionManager = new SessionManager(req);
		SubjectsORM dao = new SubjectsORM();
		
		List<SubjectAttributeType> types = dao.findByID(
				SubjectAttributeType.class, "subjectTypeFk", 1);
		PersonForm personForm = new PersonForm();
		personForm.setAttributes(sortAndFormAttributes(types));
		req.setAttribute("personForm", personForm);
		
		types = dao.findByID(SubjectAttributeType.class, "subjectTypeFk", 2);
		EnterpriseForm enterpriseForm = new EnterpriseForm();
		enterpriseForm.setAttributes(sortAndFormAttributes(types));
		req.setAttribute("enterpriseForm", enterpriseForm);
		
		types = dao.findByID(SubjectAttributeType.class, "subjectTypeFk", 3);
		EmployeeForm employeeForm = new EmployeeForm();
		employeeForm.setAttributes(personForm.getAttributes());
		employeeForm.setEmployeeAttributes(sortAndFormAttributes(types));
		req.setAttribute("employeeForm", employeeForm);
		
		if (sessionManager.loggedIn()) {
			if (action.equals("show_form")) {
				try {
					List<EmployeeRoleType> employeeRoleTypeList = dao
							.findAll(EmployeeRoleType.class);
					List<Enterprise> enterpriseList = dao
							.findAll(Enterprise.class);
					
					req.setAttribute("employeeRoleTypeList",
							employeeRoleTypeList);
					req.setAttribute("enterpriseList", enterpriseList);
					
					view = "add_new_subject_view";
				} catch (Exception e) {
					MyLogger.log("SubjectController:AddNewSubject:show_form",
							e.getMessage());
				}
			} else {
				// Clear previous Validator warnings
				Validator.getErrors().clear();
				
				if (action.equals("add_new_person")) {
					formAndValidateHumanForm(sessionManager, personForm);
					
					HashMap<String, String> errors = Validator.getErrors();		
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						dao.saveHuman(personForm);
					} else {
						req.setAttribute("status", "ERROR");
						req.setAttribute("errors", errors);
						req.setAttribute("personForm", personForm);
						
						view="add_new_subject_view";
					}
				} else if (action.equals("add_new_employee")) {
					formAndValidateHumanForm(sessionManager, employeeForm);
					formAndValidateEmployeeForm(employeeForm);
					
					HashMap<String, String> errors = Validator.getErrors();	
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						long subjectId = dao.saveHuman(personForm);
						dao.saveEmployee(subjectId, employeeForm);
					} else {
						req.setAttribute("status", "ERROR");
						req.setAttribute("errors", errors);
						req.setAttribute("employeeForm", employeeForm);
						
						view="add_new_subject_view";
					}
				} else if (action.equals("add_new_enterprise")) {
					formAndValidateEnterpriseForm(sessionManager, enterpriseForm);

					HashMap<String, String> errors = Validator.getErrors();	
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						dao.saveEnterprise(enterpriseForm);
					} else {
						req.setAttribute("status", "ERROR");
						req.setAttribute("errors", errors);
						req.setAttribute("employeeForm", employeeForm);
						
						view="add_new_subject_view";
					}
					
				}

			}
		} else {
			view = "login_view";
		}

		return view;
	}
	
	private FormAttribute[] sortAndFormAttributes(List<SubjectAttributeType> types) {
		FormAttribute[] attributes = new FormAttribute[types.size()];
		for (int i = 0; i < types.size(); i++) {
			SubjectAttributeType type = types.get(i);
			FormAttribute attribute = new FormAttribute();
			attribute.setName(type.getTypeName());
			attribute.setType(type.getDataType());
			attribute.setOrderby(type.getOrderby());
			attribute.setSubjectAttributeTypeFk(type.getSubjectAttributeType());
			attribute.setSubjectTypeFk(type.getSubjectTypeFk());
			attribute.setRequired(type.getRequired().equals("Y") ? true : false);
			attributes[i] = attribute;
		}
		return sortAttributes(attributes);
	}
	
	private FormAttribute[] sortAttributes(FormAttribute[] attributes) {
		for (int i = 0; i < attributes.length; i++) {
			int min = i;
			for (int j = i + 1; j < attributes.length; j++) {
				if (attributes[min].getOrderby() > attributes[j].getOrderby()) {
					min = j;
				}
			}
			if (i != min) {
				FormAttribute spare = attributes[i];
				attributes[i] = attributes[min];
				attributes[min] = spare;
			}
		}
		return attributes;
	}
	
	private HumanForm formAndValidateHumanForm(SessionManager sessionManager,
			HumanForm humanForm) {
		humanForm.setCreatedBy(sessionManager.getEmployeeId());
		humanForm.setUpdatedBy(sessionManager.getEmployeeId());
		humanForm.setFirstName(params.get("first_name")[0]);
		humanForm.setLastName(params.get("last_name")[0]);
		humanForm.setIdentityCode(params.get("identity_code")[0]);
		humanForm.setBirthDate(params.get("birthdate")[0]);
		
		HumanFormValidator humanFormValidator = new HumanFormValidator(humanForm);
		humanFormValidator.validate();
		
		humanForm.setAddressForm(formAndValidateAddressForm());
		formAndValidateFormAttributes(humanForm.getAttributes());
		return humanForm;
	}
	
	private EmployeeForm formAndValidateEmployeeForm(EmployeeForm employeeForm) {
		employeeForm.setEmployeeRoleType(params.get("employee_role_type")[0]);
		employeeForm.setEnterprise(params.get("enterprise")[0]);
		
		EmployeeFormValidator employeeFormValidator =
				new EmployeeFormValidator(employeeForm);
		employeeFormValidator.validate();

		formAndValidateFormAttributes(employeeForm.getEmployeeAttributes());
		
		return employeeForm;
	}
	
	private EnterpriseForm formAndValidateEnterpriseForm(SessionManager sessionManager,
			EnterpriseForm enterpriseForm) {
		enterpriseForm.setCreatedBy(sessionManager.getEmployeeId());
		enterpriseForm.setUpdatedBy(sessionManager.getEmployeeId());
		enterpriseForm.setName(params.get("name")[0]);
		enterpriseForm.setFullName(params.get("full_name")[0]);
		enterpriseForm.setAddressForm(formAndValidateAddressForm());
		
		EnterpriseFormValidator enterpriseFormValidator =
				new EnterpriseFormValidator(enterpriseForm);
		enterpriseFormValidator.validate();
		formAndValidateFormAttributes(enterpriseForm.getAttributes());
		
		return enterpriseForm;
	}
	
	private AddressForm formAndValidateAddressForm() {
		AddressForm addressForm = new AddressForm();
		addressForm.setAddressTypeFk(params.get("address_type_fk")[0]);
		addressForm.setCountry(params.get("country")[0]);
		addressForm.setCounty(params.get("county")[0]);
		addressForm.setStreetAddress(params.get("street_address")[0]);
		addressForm.setTownVillage(params.get("town_village")[0]);
		addressForm.setZipcode(params.get("zipcode")[0]);
		
		AddressFormValidator addressFormValidator = new AddressFormValidator(addressForm);
		addressFormValidator.validate();
		return addressForm;
	}
	
	private FormAttribute[] formAndValidateFormAttributes(
			FormAttribute[] attributes) {
		
		for (FormAttribute attribute : attributes) {
			attribute.setValue(params.get(attribute.getName())[0]);
		}
		FormAttributesValidator attrFormValidator = 
				new FormAttributesValidator(attributes);
		attrFormValidator.validate();
		return attributes;
	}
}
