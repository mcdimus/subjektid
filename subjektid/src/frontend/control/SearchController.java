package frontend.control;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.DA.SubjectsORM;

import frontend.forms.AddressForm;
import frontend.forms.SearchAttribute;
import frontend.forms.SearchForm;
import frontend.forms.SearchResult;

public class SearchController extends Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) {
		String view = "default_view";
		SessionManager sessionManager = new SessionManager(req);
		if (sessionManager.loggedIn()) {
			if (action.equals("search")) {
				HashMap<String, String[]> myParams =
						new HashMap<String, String[]>();
				myParams.putAll(params);
				params = myParams;
				SearchForm form = formSearchForm();
				SubjectsORM orm = new SubjectsORM();
				ArrayList<SearchResult> results = orm.search(form);
				req.setAttribute("results", results);
				req.setAttribute("Search", "search");
			}
			view = "search_view";
		} else {
			view = "login_view";
		}
		return view;
	}

	private SearchForm formSearchForm() {
		SearchForm searchForm = new SearchForm();
		searchForm.setSubjectType(params.remove("subject_type")[0]);
		searchForm.setFirstName(params.remove("fname")[0]);
		searchForm.setLastName(params.remove("lname")[0]);
		searchForm.setAddressForm(formAddressForm());
		searchForm.setAttributes(formAttributes());
		return searchForm;
	}
	
	private AddressForm formAddressForm() {
		AddressForm addressForm = new AddressForm();
		addressForm.setAddressId(params.remove("addressId")[0]);
		addressForm.setAddressTypeFk(params.remove("address_type_fk")[0]);
		addressForm.setCountry(params.remove("country")[0]);
		addressForm.setCounty(params.remove("county")[0]);
		addressForm.setStreetAddress(params.remove("street_address")[0]);
		addressForm.setTownVillage(params.remove("town_village")[0]);
		addressForm.setZipcode(params.remove("zipcode")[0]);
		return addressForm;
	}
	
	private ArrayList<SearchAttribute> formAttributes() {
		ArrayList<SearchAttribute> attributes = 
				new ArrayList<SearchAttribute>();
		for (String key : params.keySet()) {
			SearchAttribute attribute = new SearchAttribute();
			attribute.setName(key);
			attribute.setType(params.get(key)[0]);
			attribute.setFirstValue(params.get(key)[1]);
			if (params.get(key).length > 2) {
				attribute.setSecondValue(params.get(key)[2]);
			}
			attributes.add(attribute);
		}
		return attributes;
	}

}