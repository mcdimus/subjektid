package frontend.control;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import log.MyLogger;

import backend.DA.SubjectsORM;
import frontend.forms.AddressForm;
import frontend.forms.ContactForm;
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
				SearchForm form = formSearchForm();
				SubjectsORM orm = new SubjectsORM();
				ArrayList<SearchResult> results = orm.search(form);
				req.setAttribute("results", results);
				req.setAttribute("action", "search");
			} else if (action.equals("search_xml")) {
				SearchForm form = formSearchForm();
				SubjectsORM orm = new SubjectsORM();
				DOMSource source = orm.searchXML(form);

				try {
					// transform XML to HTML using XSL

					Source xsltSource = new StreamSource(req
							.getServletContext().getResourceAsStream(
									"/static/xml/stylesheet.xsl"));
					TransformerFactory transformerFactory = TransformerFactory
							.newInstance();
					Transformer transformer = transformerFactory
							.newTransformer(xsltSource);

					Writer strWriter = new StringWriter();
					StreamResult result = new StreamResult(strWriter);

					transformer.transform(source, result);

					req.setAttribute("htmlWithResults", strWriter.toString());
				} catch (TransformerConfigurationException e) {
					MyLogger.log("SearchController.service(): TransConf",
							e.getMessage());
				} catch (TransformerFactoryConfigurationError e) {
					MyLogger.log("SearchController.service(): TransFacConf",
							e.getMessage());
				} catch (TransformerException e) {
					MyLogger.log("SearchController.service(): Trans",
							e.getMessage());
				}

			}
			view = "search_view";
		} else {
			view = "login_view";
		}
		return view;
	}

	private SearchForm formSearchForm() {
		SearchForm searchForm = new SearchForm();
		searchForm.setSubjectType(Long.parseLong(params.get("subject_type")
				[0]));
		searchForm.setFirstName(params.get("fname")[0]);
		searchForm.setLastName(params.get("lname")[0]);
		searchForm.setAddressForm(formAddressForm());
		searchForm.setContactForm(formContactForm());
		if (searchForm.getSubjectType() != 0) {
			searchForm.setAttributes(formAttributes());
		}
		return searchForm;
	}

	private AddressForm formAddressForm() {
		AddressForm addressForm = new AddressForm();
		addressForm.setCountry(params.get("country")[0]);
		addressForm.setCounty(params.get("county")[0]);
		addressForm.setStreetAddress(params.get("street_address")[0]);
		addressForm.setTownVillage(params.get("town_village")[0]);
		addressForm.setZipcode(params.get("zipcode")[0]);
		return addressForm;
	}
	
	private ContactForm formContactForm() {
		ContactForm form = new ContactForm();
		form.setContact(params.get("contact")[0]);
		form.setContactType(params.get("contact_type")[0]);
		return form;
	}

	private ArrayList<SearchAttribute> formAttributes() {
		ArrayList<SearchAttribute> attributes = new ArrayList<SearchAttribute>();
		int i = 0;
		while ((params.get("attribute_id")[i]) != null) {
			SearchAttribute attribute = new SearchAttribute();
			attribute.setAttrID((params.get("attribute_id")[i]));
			attribute.setName(params.get("attribute_name")[i]);
			attribute.setFirstValue(params.get("attribute_fval")[i]);
			attribute.setSecondValue(params.get("attribute_sval")[i]);
			attribute.setType(params.get("attribute_type")[i]);
			attributes.add(attribute);
			i++;
		}
		return attributes;
	}

}