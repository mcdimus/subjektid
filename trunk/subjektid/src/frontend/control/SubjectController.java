package frontend.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.forms.AccountForm;
import frontend.forms.AddressForm;
import frontend.forms.ContactForm;
import frontend.forms.EmployeeForm;
import frontend.forms.EmployeeRoleForm;
import frontend.forms.EnterpriseForm;
import frontend.forms.FormAttribute;
import frontend.forms.HumanForm;
import frontend.forms.PersonForm;
import frontend.forms.SubjectForm;
import frontend.validator.AccountFormValidator;
import frontend.validator.AddressFormValidator;
import frontend.validator.EmployeeFormValidator;
import frontend.validator.EnterpriseFormValidator;
import frontend.validator.FormAttributesValidator;
import frontend.validator.HumanFormValidator;
import frontend.validator.Validator;

import log.MyLogger;

import backend.DA.SubjectsORM;
import backend.model.Address;
import backend.model.Contact;
import backend.model.Customer;
import backend.model.Employee;
import backend.model.EmployeeRole;
import backend.model.EmployeeRoleType;
import backend.model.EntPerRelationType;
import backend.model.Enterprise;
import backend.model.EnterprisePersonRelation;
import backend.model.Person;
import backend.model.SubjectAttribute;
import backend.model.SubjectAttributeType;
import backend.model.UserAccount;

public class SubjectController extends Controller {
	
	SubjectsORM dao = new SubjectsORM();
	PersonForm personForm;
	EnterpriseForm enterpriseForm;
	EmployeeForm employeeForm;

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {

		String view = "default_view";
		SessionManager sessionManager = new SessionManager(req);
		
		List<SubjectAttributeType> types = dao.findByIdAndOrder(
				SubjectAttributeType.class, "subjectTypeFk", 1);
		personForm = new PersonForm();
		personForm.setAttributes(formAttributes(types));
		req.setAttribute("personForm", personForm);
		
		types = dao.findByIdAndOrder(SubjectAttributeType.class, "subjectTypeFk", 2);
		enterpriseForm = new EnterpriseForm();
		enterpriseForm.setAttributes(formAttributes(types));
		req.setAttribute("enterpriseForm", enterpriseForm);
		
		types = dao.findByIdAndOrder(SubjectAttributeType.class, "subjectTypeFk", 3);
		employeeForm = new EmployeeForm();
		employeeForm.setAttributes(personForm.getAttributes());
		employeeForm.setEmployeeAttributes(formAttributes(types));
		req.setAttribute("employeeForm", employeeForm);
		
		types = dao.findByIdAndOrder(SubjectAttributeType.class, "subjectTypeFk", 4);
		personForm.setCustomerAttributes(formAttributes(types));
		enterpriseForm.setCustomerAttributes(formAttributes(types));

		List<EmployeeRoleType> employeeRoleTypeList = dao
				.findAll(EmployeeRoleType.class);
		List<Enterprise> enterpriseList = dao
				.findAll(Enterprise.class);
		List<EntPerRelationType> rels = dao.findAll(
				EntPerRelationType.class);
		
		req.setAttribute("employeeRoleTypeList",
				employeeRoleTypeList);
		req.setAttribute("enterpriseList", enterpriseList);
		req.setAttribute("ent_per_rels", rels);
		
		if (sessionManager.loggedIn()) {
			if (action.equals("add_new_subject")) {
				try {
					
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
					formPersonForm(personForm);
					req.setAttribute("personForm", personForm);
					req.setAttribute("subjectTypeFk", "1");
					req.setAttribute("addressForm", personForm.getAddressForm());
					req.setAttribute("contacts", personForm.getContacts());
					HashMap<String, String> errors = Validator.getErrors();		
					if (errors.isEmpty()) {
						req.setAttribute("status", "SUCCESS");
						dao.saveHuman(personForm);
						dao.savePerson(personForm);
						
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
					req.setAttribute("contacts", employeeForm.getContacts());
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
					req.setAttribute("contacts", enterpriseForm.getContacts());
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
	
	private FormAttribute[] formAttributes(List<SubjectAttributeType> types) {
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
		return attributes;
	}
	
	private HumanForm formAndValidateHumanForm(SessionManager sessionManager,
			HumanForm humanForm) {
		humanForm.setSubjectId(params.get("subject_id")[0]);
		if (humanForm.getCreatedBy() != null) {
			humanForm.setUpdatedBy(sessionManager.getEmployeeId());
			humanForm.setUpdated(new Date());
		} else {
			humanForm.setCreatedBy(sessionManager.getEmployeeId());
			humanForm.setCreated(new Date());
		}
		humanForm.setFirstName(params.get("first_name")[0]);
		humanForm.setLastName(params.get("last_name")[0]);
		humanForm.setIdentityCode(params.get("identity_code")[0]);
		humanForm.setBirthDate(params.get("birthdate")[0]);
		if (params.containsKey("customer_id")) {
			humanForm.setCustomerId(params.get("customer_id")[0]);
			humanForm.setCustomer("ON");
		} else if (params.containsKey("customer")) {
			humanForm.setCustomer(params.get("customer")[0] != null 
					? params.get("customer")[0] : null);
		}
		HumanFormValidator humanFormValidator = new HumanFormValidator(humanForm);
		humanFormValidator.validate();

		formAndValidateAddressForms(humanForm);
		formContactForms(humanForm);
		
		formAndValidateFormAttributes(humanForm.getAttributes());
		if (humanForm.getCustomerId() != null) {
			formAndValidateFormAttributes(humanForm.getCustromerAttributes());
		}
		return humanForm;
	}
	
	private PersonForm formPersonForm(PersonForm personForm) {
		personForm.setEntPerRelId(params.get("ent_rel_id")[0]);
		personForm.setEnterprise(params.get("enterprise")[0]);
		personForm.setEntPerRelType(params.get("relation_type")[0]);
		return personForm;
	}
	
	private EmployeeForm formAndValidateEmployeeForm(EmployeeForm employeeForm) {
		employeeForm.setEmployeeId(params.get("employee_id")[0]);
		employeeForm.setEnterprise(params.get("enterprise")[0]);
		ArrayList<EmployeeRoleForm> roles = new ArrayList<EmployeeRoleForm>();
		if (params.containsKey("role_type_id")) {
			for (int i = 0; i < params.get("role_type_id").length; i++) {
				EmployeeRoleForm role = new EmployeeRoleForm();
				role.setRoleID(params.get("role_id")[i]);
				role.setRole(params.get("role_type_id")[i]);
				role.setRoleName(params.get("role_name")[i]);
				roles.add(role);
			}
			employeeForm.setRoles(roles);
		}
		
		EmployeeFormValidator employeeFormValidator =
				new EmployeeFormValidator(employeeForm);
		employeeFormValidator.validate();

		formAndValidateFormAttributes(employeeForm.getEmployeeAttributes());
		
		if (params.containsKey("account_id")) {
			employeeForm.setAccForm(formAndValidateAccountForm());
		}
		
		return employeeForm;
	}
	
	private AccountForm formAndValidateAccountForm() {
		AccountForm form = new AccountForm();
		form.setAccountId(params.get("account_id")[0]);
		form.setUsername(params.get("username")[0]);
		form.setPassword(params.get("password")[0]);
		form.setValidFrom(params.get("valid_from")[0]);
		form.setValidTo(params.get("valid_to")[0]);
		form.setPasswordNeverExpires(params.get("password_never_expires")[0]);
		form.setStatus(params.get("status")[0]);
		form.setCreatedBy(params.get("created_by")[0]);
		form.setCreated(params.get("created")[0]);
		
		AccountFormValidator validator = new AccountFormValidator(form);
		validator.validate();
		return form;
	}
	
	private EnterpriseForm formAndValidateEnterpriseForm(SessionManager sessionManager,
			EnterpriseForm enterpriseForm) {
		enterpriseForm.setSubjectId(params.get("subject_id")[0]);
		if (enterpriseForm.getCreatedBy() != null) {
			enterpriseForm.setUpdatedBy(sessionManager.getEmployeeId());
			enterpriseForm.setUpdated(new Date());
		} else {
			enterpriseForm.setCreatedBy(sessionManager.getEmployeeId());
			enterpriseForm.setCreated(new Date());
		}
		enterpriseForm.setName(params.get("name")[0]);
		enterpriseForm.setFullName(params.get("full_name")[0]);
		if (params.containsKey("customer_id")) {
			enterpriseForm.setCustomerId(params.get("customer_id")[0]);
			enterpriseForm.setCustomer("ON");
		} else if (params.containsKey("customer")) {
			enterpriseForm.setCustomer(params.get("customer")[0] != null 
					? params.get("customer")[0] : null);
		}

		formAndValidateAddressForms(enterpriseForm);
		formContactForms(enterpriseForm);
		
		EnterpriseFormValidator enterpriseFormValidator =
				new EnterpriseFormValidator(enterpriseForm);
		enterpriseFormValidator.validate();
		formAndValidateFormAttributes(enterpriseForm.getAttributes());
		
		if (enterpriseForm.getCustomerId() != null) {
			formAndValidateFormAttributes(enterpriseForm.getCustromerAttributes());
		}
		return enterpriseForm;
	}
	
	private void formAndValidateAddressForms(SubjectForm form) {
		AddressForm addressForm = formAddressForm(0);
		AddressFormValidator addressFormValidator = new AddressFormValidator(addressForm);
		addressFormValidator.validate();
		form.setAddressForm(addressForm);
		
		ArrayList<AddressForm> addresses = new ArrayList<AddressForm>();
		for (int i = 1; i < params.get("address_type_fk").length; i++) {
			AddressForm aForm = formAddressForm(i);
			addresses.add(aForm);
		}
		addressForm.setAddresses(addresses);
	}
	
	private AddressForm formAddressForm(int i) {
		AddressForm addressForm = new AddressForm();
		addressForm.setAddressId(params.get("address_id")[i]);
		addressForm.setAddressTypeFk(params.get("address_type_fk")[i]);
		addressForm.setCountry(params.get("country")[i]);
		addressForm.setCounty(params.get("county")[i]);
		addressForm.setStreetAddress(params.get("street_address")[i]);
		addressForm.setTownVillage(params.get("town_village")[i]);
		addressForm.setZipcode(params.get("zipcode")[i]);
		return addressForm;
	}
	
	private void formContactForms(SubjectForm form) {
		ArrayList<ContactForm> contacts = new ArrayList<ContactForm>();
		if (params.containsKey("contact")) {
			for (int i = 0; i < params.get("contact").length; i++) {
				ContactForm contact = new ContactForm();
				contact.setContactId(params.get("contact_id")[i]);
				contact.setContactType(params.get("contact_type")[i]);
				contact.setContact(params.get("contact")[i]);
				contact.setNote(params.get("note")[i]);
				contact.setOrderBy(params.get("contact_orderby")[i]);
				contacts.add(contact);
			}
		}
		form.setContacts(contacts);
	}
	
	private FormAttribute[] formAndValidateFormAttributes(
			FormAttribute[] attributes) {
		for (FormAttribute attribute : attributes) {
			attribute.setFormAttributeId(params.get(attribute.getName()
					+ "_id")[0]);
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
			if (formEmployeeEdit(employeeForm, Long.parseLong(params
					.get("subject_id")[0]))) {
				formHumanEdit(employeeForm);
				req.setAttribute("subjectTypeFk", "3");
				req.setAttribute("addressForm", employeeForm.getAddressForm());
				req.setAttribute("contacts", employeeForm.getContacts());
			} else {
				formHumanEdit(personForm);
				formEntPerRelationEdit(personForm);
				formCustomerEdit(personForm, 1);
				req.setAttribute("subjectTypeFk", "1");
				req.setAttribute("addressForm", personForm.getAddressForm());
				req.setAttribute("contacts", personForm.getContacts());
			}
			break;
		case 2:
			formEnterpriseEdit(enterpriseForm);
			req.setAttribute("subjectTypeFk", "2");
			req.setAttribute("addressForm", enterpriseForm.getAddressForm());
			req.setAttribute("contacts", enterpriseForm.getContacts());
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		form.setBirthDate(sdf.format(person.getBirthDate()));
		form.setCreatedBy(String.valueOf(person.getCreatedBy()));
		form.setCreated(person.getCreated());
		
		form.setAddressForm(formAddressEdit(person.getPerson(), 1));
		
		form.setContacts(formContactsEdit(person.getPerson(), 1));
		
		form.setAttributes(formAttributesEdit(form.getAttributes(),
				person.getPerson(), 1));
		return form;
	}
	
	private EnterpriseForm formEnterpriseEdit(EnterpriseForm form) {
		Enterprise enterprise = dao.findByID(Enterprise.class,
				Long.parseLong(params.get("subject_id")[0]));
		form.setSubjectId(String.valueOf(enterprise.getEnterprise()));
		form.setName(enterprise.getName());
		form.setFullName(enterprise.getFullName());
		form.setCreatedBy(String.valueOf(enterprise.getCreatedBy()));
		form.setCreated(enterprise.getCreated());
		
		form.setAddressForm(formAddressEdit(enterprise.getEnterprise(), 2));
		
		form.setContacts(formContactsEdit(enterprise.getEnterprise(), 2));
		
		form.setAttributes(formAttributesEdit(form.getAttributes(),
				enterprise.getEnterprise(), 2));
		formCustomerEdit(form, 2);
		return form;
	}
	
	private boolean formEmployeeEdit(EmployeeForm form, long subjectId) {
		List<Employee> employees = dao.findByID(Employee.class, "personFk",
				subjectId);
		if (!employees.isEmpty()) {
			formUserAccEdit(employeeForm, subjectId);
			form.setEmployeeId(String.valueOf(employees.get(0).getEmployee()));
			form.setEnterprise(String.valueOf(employees.get(0).getEnterpriseFk()));
			List<EmployeeRole> roles = dao.findByID(EmployeeRole.class,
					"employee_fk", employees.get(0).getEmployee());
			ArrayList<EmployeeRoleForm> formRoles =
					new ArrayList<EmployeeRoleForm>();
			for (EmployeeRole role : roles) {
				EmployeeRoleType ert = dao.findByID(EmployeeRoleType.class, role.getEmployeeRoleTypeFk());
				EmployeeRoleForm formRole = new EmployeeRoleForm();
				formRole.setRoleID(String.valueOf(role.getEmployeeRole()));
				formRole.setRole(String.valueOf(role.getEmployeeRoleTypeFk()));
				formRole.setRoleName(ert.getTypeName());
				formRoles.add(formRole);
			}
			form.setRoles(formRoles);
			form.setEmployeeAttributes(formAttributesEdit(
					form.getEmployeeAttributes(), employees.get(0)
					.getEmployee(), 3));
			return true;
		} else {
			return false;
		}
	}
	
	private void formUserAccEdit(EmployeeForm form, long subjectId) {
		List<UserAccount> accs = dao.findByID(UserAccount.class, "subjectFk",
				subjectId);
		if (!accs.isEmpty()) {
			AccountForm accForm = new AccountForm(accs.get(0));
			form.setAccForm(accForm);
		}
	}
	
	private PersonForm formEntPerRelationEdit(PersonForm form) {
		List<EnterprisePersonRelation> rels = dao.findByID(EnterprisePersonRelation
				.class, "personFk", Long.parseLong(form.getSubjectId()));
		if (rels.size() != 0) {
			form.setEnterprise(rels.get(0).getEnterpriseFk().toString());
			form.setEntPerRelId(String.valueOf(rels.get(0).getEnterprisePersonRelation()));
			form.setEntPerRelType(rels.get(0).getEntPerRelationTypeFk().toString());
		}
		return form;
	}
	
	private AddressForm formAddressEdit(long subjectId,
			long subjectTypeFk) {
		List<Address> addresses = dao.findBySubjectID(Address.class,
				subjectId, subjectTypeFk);
		ArrayList<AddressForm> addressForms = new ArrayList<AddressForm>();
		AddressForm main = new AddressForm();
		if (addresses != null) {
			for (Address address : addresses) {
				AddressForm form = new AddressForm();
				form.setAddressId(String.valueOf(address.getAddress()));
				form.setCountry(address.getCountry());
				form.setCounty(address.getCounty());
				form.setTownVillage(address.getTownVillage());
				form.setStreetAddress(address.getStreetAddress());
				form.setZipcode(address.getZipcode());
				if (address.getAddressTypeFk() != 2) {
					main = form;
				} else {
					addressForms.add(form);
				}
			}
			main.setAddresses(addressForms);
		}
		return main;
	}
	
	private ArrayList<ContactForm> formContactsEdit(long subjectId, long subjectTypeFk) {
		List<Contact> contacts = dao.findBySubjectIdAndOrderContacts(
				Contact.class, subjectId, subjectTypeFk);
		ArrayList<ContactForm> contactForms = new ArrayList<ContactForm>();
		if (contacts != null) {
			for (Contact contact : contacts) {
				ContactForm form = new ContactForm();
				form.setContactId(String.valueOf(contact.getContact()));
				form.setContact(contact.getValueText());
				form.setContactType(String.valueOf(contact.getContactTypeFk()));
				form.setNote(contact.getNote());
				form.setOrderBy(String.valueOf(contact.getOrderby()));

				contactForms.add(form);
			}
		}
		return contactForms;
	}
	
	private FormAttribute[] formAttributesEdit(FormAttribute[] attributes,
			long subjectId, long subjectType) {
		List<SubjectAttribute> attrs = dao.findBySubjectIdAndOrder(
				SubjectAttribute.class, subjectId, subjectType);
		for (int i = 0; i < attrs.size(); i++) {
			for (FormAttribute attribute : attributes) {
				if (attribute.getSubjectAttributeTypeFk() == 
						attrs.get(i).getSubjectAttributeTypeFk()) {
					attribute.setFormAttributeId(String.valueOf(attrs.get(i)
							.getSubjectAttribute()));
					switch (attrs.get(i).getDataType()) {
					case 1:
						attribute.setValue(attrs.get(i).getValueText());
						break;
					case 2:
						attribute.setValue(attrs.get(i).getValueNumber().toString());
						break;
					case 3:
						attribute.setValue(attrs.get(i).getValueDate().toString());
						break;
					}
					break;
				}
			}
		}
		return attributes;
	}
	
	private void formCustomerEdit(SubjectForm form, long subjectType) {
		long subjectId = Long.parseLong(form.getSubjectId());
		List<Customer> customers = dao.findBySubjectID(Customer.class,
				subjectId, subjectType);
		if (customers.size() != 0) {
			form.setCustomerId(String.valueOf(customers.get(0).getCustomer()));
			form.setCustomerAttributes(formAttributesEdit(form
					.getCustromerAttributes(), customers.get(0).getCustomer(), 4));
		}
	}
}