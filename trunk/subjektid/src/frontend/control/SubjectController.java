package frontend.control;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.forms.AddressForm;
import frontend.forms.PersonForm;
import frontend.validator.AddressFormValidator;
import frontend.validator.PersonFormValidator;

import log.MyLogger;

import backend.DA.SubjectsORM;
import backend.model.EmployeeRoleType;
import backend.model.Enterprise;

public class SubjectController extends Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {

		String view = "default_view";
		SessionManager sessionManager = new SessionManager(req);

		if (sessionManager.loggedIn()) {
			if (action.equals("show_form")) {
				try {
					SubjectsORM dao = new SubjectsORM();
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
			} else if (action.equals("add_new_person")) {

				AddressForm addressForm = new AddressForm();
				addressForm.setAddressTypeFk(params.get("address_type_fk")[0]);
				addressForm.setCountry(params.get("country")[0]);
				addressForm.setCounty(params.get("county")[0]);
				addressForm.setStreetAddress(params.get("street_address")[0]);
				addressForm.setTownVillage(params.get("town_village")[0]);
				addressForm.setZipcode(params.get("zipcode")[0]);
				
				AddressFormValidator addressFormValidator = new AddressFormValidator(addressForm);
				
				PersonForm personForm = new PersonForm();
				personForm.setCreatedBy(sessionManager.getEmployeeId());
				personForm.setUpdatedBy(sessionManager.getEmployeeId());
				personForm.setFirstName(params.get("first_name")[0]);
				personForm.setLastName(params.get("last_name")[0]);
				personForm.setIdentityCode(params.get("identity_code")[0]);
				personForm.setBirthDate(params.get("birthdate")[0]);
				personForm.setGender(params.get("gender")[0]);
				personForm.setEestiResident(params.get("eesti_resident")[0]);
				personForm.setNationality(params.get("nationality")[0]);
				personForm.setReligion(params.get("religion")[0]);
				personForm.setFavNumber(params.get("fav_number")[0]);
				personForm.setAddressForm(addressForm);
				
				PersonFormValidator personFormValidator = new PersonFormValidator(personForm);
				
				if (addressFormValidator.validate() && personFormValidator.validate()) {
					
					// vse good --> send personForm to backend
				} else {
					HashMap<String, String> errors = new HashMap<String, String>();
					
					errors.putAll(personFormValidator.getErrors());
					errors.putAll(addressFormValidator.getErrors());
					
					errors.put("reason", "Validation failed");
					
					req.setAttribute("errors", errors);
					req.setAttribute("personForm", personForm);
					
					view="add_new_subject_view";
				}

			}
		} else {
			view = "login_view";
		}

		return view;
	}
}
