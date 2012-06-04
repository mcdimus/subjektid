package frontend.control;

import java.util.ArrayList;
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
import frontend.forms.SubjectForm;
import frontend.validator.AddressFormValidator;
import frontend.validator.EmployeeFormValidator;
import frontend.validator.EnterpriseFormValidator;
import frontend.validator.FormAttributesValidator;
import frontend.validator.HumanFormValidator;
import frontend.validator.Validator;

import log.MyLogger;

import backend.DA.SubjectsORM;
import backend.model.Address;
import backend.model.EmployeeRoleType;
import backend.model.Enterprise;
import backend.model.Person;
import backend.model.SubjectAttribute;
import backend.model.SubjectAttributeType;

public class SubjectController extends Controller {
	
	SubjectsORM dao = new SubjectsORM();
	PersonForm personForm;
	EnterpriseForm enterpriseForm;
	EmployeeForm employeeForm;

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {

		String view = "default_view";
		SessionManager sessionManager = new SessionManager(req);
		
		List<SubjectAttributeType> types = dao.findByID(
				SubjectAttributeType.class, "subjectTypeFk", 1);
		personForm = new PersonForm();
		personForm.setAttributes(sortAndFormAttributes(types));
		req.setAttribute("personForm", personForm);
		
		types = dao.findByID(SubjectAttributeType.class, "subjectTypeFk", 2);
		enterpriseForm = new EnterpriseForm();
		enterpriseForm.setAttributes(sortAndFormAttributes(types));
		req.setAttribute("enterpriseForm", enterpriseForm);
		
		types = dao.findByID(SubjectAttributeType.class, "subjectTypeFk", 3);
		employeeForm = new EmployeeForm();
		employeeForm.setAttributes(personForm.getAttributes());
		employeeForm.setEmployeeAttributes(sortAndFormAttributes(types));
		req.setAttribute("employeeForm", employeeForm);
		
		if (sessionManager.loggedIn()) {
			if (action.equals("add_new_subject")) {
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
			} else if (action.contains("add")){
				// Clear previous Validator warnings
				Validator.getErrors().clear();
				
				if (action.equals("add_person")) {
					formAndValidateHumanForm(sessionManager, personForm);
					req.setAttribute("personForm", personForm);
					req.setAttribute("subjectTypeFk", "1");
					req.setAttribute("addressForm", personForm.getAddressForm());
					HashMap<String, String> errors = Validator.getErrors();		
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						dao.saveHuman(personForm);
						
						view = "edit_subject_view";
					} else {
						req.setAttribute("status", "ERROR");
						req.setAttribute("errors", errors);
						
						view="add_new_subject_view";
					}
				} else if (action.equals("add_employee")) {
					formAndValidateHumanForm(sessionManager, employeeForm);
					formAndValidateEmployeeForm(employeeForm);
					req.setAttribute("employeeForm", employeeForm);
					req.setAttribute("subjectTypeFk", "3");
					req.setAttribute("addressForm", employeeForm.getAddressForm());
					HashMap<String, String> errors = Validator.getErrors();	
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						dao.saveHuman(employeeForm);
						dao.saveEmployee(employeeForm);
						
						view = "edit_subject_view";
					} else {
						req.setAttribute("status", "ERROR");
						req.setAttribute("errors", errors);
						
						view="add_new_subject_view";
					}
				} else if (action.equals("add_enterprise")) {
					formAndValidateEnterpriseForm(sessionManager, enterpriseForm);
					req.setAttribute("enterpriseForm", enterpriseForm);
					req.setAttribute("subjectTypeFk", "2");
					req.setAttribute("addressForm", enterpriseForm.getAddressForm());
					HashMap<String, String> errors = Validator.getErrors();	
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						dao.saveEnterprise(enterpriseForm);
						
						view = "edit_subject_view";
					} else {
						req.setAttribute("status", "ERROR");
						req.setAttribute("errors", errors);
						
						view="add_new_subject_view";
					}
				}

			} else if (action.equals("edit_subject")) {
				formEditForm(req);
				view = "edit_subject_view";
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
		humanForm.setSubjectId(params.get("subject_id")[0]);
		humanForm.setCreatedBy(sessionManager.getEmployeeId());
		humanForm.setUpdatedBy(sessionManager.getEmployeeId());
		humanForm.setFirstName(params.get("first_name")[0]);
		humanForm.setLastName(params.get("last_name")[0]);
		humanForm.setIdentityCode(params.get("identity_code")[0]);
		humanForm.setBirthDate(params.get("birthdate")[0]);
		humanForm.setCustomer(params.get("customer") != null 
				? params.get("customer")[0] : null);
		
		HumanFormValidator humanFormValidator = new HumanFormValidator(humanForm);
		humanFormValidator.validate();
		
		formAndValidateAddressForms(humanForm);
		
		formAndValidateFormAttributes(humanForm.getAttributes());
		return humanForm;
	}
	
	private EmployeeForm formAndValidateEmployeeForm(EmployeeForm employeeForm) {
		employeeForm.setSubjectId(params.get("employee_id")[0]);
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
		enterpriseForm.setSubjectId(params.get("subject_id")[0]);
		enterpriseForm.setCreatedBy(sessionManager.getEmployeeId());
		enterpriseForm.setUpdatedBy(sessionManager.getEmployeeId());
		enterpriseForm.setName(params.get("name")[0]);
		enterpriseForm.setFullName(params.get("full_name")[0]);
		enterpriseForm.setCustomer(params.get("customer") != null 
				? params.get("customer")[0] : null);

		formAndValidateAddressForms(enterpriseForm);
		
		EnterpriseFormValidator enterpriseFormValidator =
				new EnterpriseFormValidator(enterpriseForm);
		enterpriseFormValidator.validate();
		formAndValidateFormAttributes(enterpriseForm.getAttributes());
		
		return enterpriseForm;
	}
	
	private void formAndValidateAddressForms(SubjectForm form) {
		AddressForm addressForm = formAddressForm(0);
		AddressFormValidator addressFormValidator = new AddressFormValidator(addressForm);
		addressFormValidator.validate();
		form.setAddressForm(addressForm);
		
		ArrayList<AddressForm> addresses = new ArrayList<AddressForm>();
		for (int i = 1; i < params.get("country").length; i++) {
			addressForm = formAddressForm(i);
			addresses.add(addressForm);
		}
		form.setAddresses(addresses);
		addressForm.setAddresses(addresses);
	}
	
	private AddressForm formAddressForm(int i) {
		AddressForm addressForm = new AddressForm();
		addressForm.setAddressId(params.get("addressId")[i]);
		addressForm.setAddressTypeFk(params.get("address_type_fk")[i]);
		addressForm.setCountry(params.get("country")[i]);
		addressForm.setCounty(params.get("county")[i]);
		addressForm.setStreetAddress(params.get("street_address")[i]);
		addressForm.setTownVillage(params.get("town_village")[i]);
		addressForm.setZipcode(params.get("zipcode")[i]);
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
	
	private void formEditForm(HttpServletRequest req) {
		int subjectType = Integer.parseInt(params.get("subject_type")[0]);
		switch (subjectType) {
		case 1:
			formHumanEdit(personForm);
			req.setAttribute("subjectTypeFk", "1");
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}
	
	private HumanForm formHumanEdit(HumanForm form) {
		Person person = dao.findByID(Person.class,
				Long.parseLong(params.get("subject_id")[0]));
		form.setSubjectId(String.valueOf(person.getPerson()));
		form.setFirstName(person.getFirstName());
		form.setLastName(person.getLastName());
		form.setIdentityCode(person.getIdentityCode());
		form.setBirthDate(person.getBirthDate().toString());
		form.setCreatedBy(String.valueOf(person.getCreatedBy()));
		
		ArrayList<AddressForm> addressForms = formAddressEdit(person.getPerson(),
				1);
		form.setAddressForm(addressForms.remove(addressForms.size() - 1));
		form.setAddresses(addressForms);
		return form;
	}
	
	private ArrayList<AddressForm> formAddressEdit(long subjectId,
			long subjectTypeFk) {
		List<Address> addresses = dao.findBySubjectID(Address.class,
				subjectId, subjectTypeFk);
		ArrayList<AddressForm> addressForms = new ArrayList<AddressForm>();
		AddressForm main = null;
		for (Address address : addresses) {
			AddressForm form = new AddressForm();
			form.setAddressId(String.valueOf(address.getAddress()));
			form.setCountry(address.getCountry());
			form.setCounty(form.getCounty());
			form.setTownVillage(form.getTownVillage());
			form.setStreetAddress(form.getStreetAddress());
			form.setZipcode(address.getZipcode());
			if (address.getAddressTypeFk() != 2) {
				main = form;
			} else {
				addressForms.add(form);
			}
		}
		addressForms.add(main);
		return addressForms;
	}
	
	private void formAttributesEdit(long subjectId,
			long subjectTypeFk, SubjectForm form) {
		List<SubjectAttribute> attrs = dao.findBySubjectID(
				SubjectAttribute.class, subjectId, subjectTypeFk);
		FormAttribute[] attributes = form.getAttributes();
		for (int i = 0; i < attrs.size(); i++) {
			for (FormAttribute attribute : attributes) {
				
			}
		}
	}
}
