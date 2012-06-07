package backend.DA;

import frontend.forms.AccountForm;
import frontend.forms.AddressForm;
import frontend.forms.ContactForm;
import frontend.forms.EmployeeForm;
import frontend.forms.EmployeeRoleForm;
import frontend.forms.EnterpriseForm;
import frontend.forms.FormAttribute;
import frontend.forms.HumanForm;
import frontend.forms.PersonForm;
import frontend.forms.SearchAttribute;
import frontend.forms.SearchForm;
import frontend.forms.SearchResult;
import frontend.forms.SubjectForm;
import general.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import log.MyLogger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import backend.model.Address;
import backend.model.Contact;
import backend.model.Customer;
import backend.model.Employee;
import backend.model.EmployeeRole;
import backend.model.Enterprise;
import backend.model.EnterprisePersonRelation;
import backend.model.Person;
import backend.model.SubjectAttribute;
import backend.model.UserAccount;

public class SubjectsORM {

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> _class) {
		Session session = null;
		List<T> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			data = (List<T>) session.createQuery("from " + _class.getName())
					.list();
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.findAll(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return data;
	}

	@SuppressWarnings("unchecked")
	public <T> T findByID(Class<T> _class, long id) {
		Session session = null;
		T data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from " + _class.getName()
					+ " obj where obj.id=:id");
			q.setLong("id", id);
			data = (T) q.uniqueResult();
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.findByID(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return data;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> findByIdOrder(Class<T> _class, String attribute,
			long id, String orderBy) {
		Session session = null;
		List<T> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from " + _class.getName()
					+ " where " + attribute + "=:id" + orderBy);
			q.setLong("id", id);
			data = (List<T>) q.list();
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.findByID(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return data;
	}
	
	public <T> List<T> findByID(Class<T> _class, String attribute, long id) {
		return findByIdOrder(_class, attribute, id, "");
	}

	public <T> List<T> findByIdAndOrder(Class<T> _class, String attribute,
			long id) {
		return findByIdOrder(_class, attribute, id, " ORDER BY orderby");
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> findBySubjectIdOrder(Class<T> _class, long subjectId,
			long subjectType, String orderBy) {
		Session session = null;
		List<T> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session
					.createQuery("from "
							+ _class.getName()
							+ " obj where obj.subjectFk=:id"
							+ " AND obj.subjectTypeFk=:type" + orderBy);
			q.setLong("id", subjectId);
			q.setLong("type", subjectType);
			data = (List<T>) q.list();
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.findByID(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return data;
	}

	public <T> List<T> findBySubjectID(Class<T> _class, long subjectId,
			long subjectType) {
		return findBySubjectIdOrder(_class, subjectId, subjectType, "");
	}
	
	public <T> List<T> findBySubjectIdAndOrder(Class<T> _class,
			long subjectId, long subjectType) {
		return findBySubjectIdOrder(_class, subjectId, subjectType,
				" ORDER BY orderby");
	}
	
	public <T> List<T> findBySubjectIdAndOrderContacts(Class<T> _class,
			long subjectId, long subjectType) {
		return findBySubjectIdOrder(_class, subjectId, subjectType,
				" ORDER BY contactTypeFk, orderby");
	}

	public <T> boolean saveOrUpdate(T object) {
		Transaction tx = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(object);
			tx.commit();
			return true;
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.saveOrUpdate(): ", e.getMessage());
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
	}

	public void deleteByID(String object, String attribute, long id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("delete from " + object
					+ " where " + attribute + "=:id");
			q.setLong("id", id);
			q.executeUpdate();
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.deleteByID(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
	}

	public String saveHuman(HumanForm form) {
		Person person = new Person();
		if (!form.getSubjectId().isEmpty()) {
			person.setPerson(Long.parseLong(form.getSubjectId()));
		}
		person.setFirstName(form.getFirstName());
		person.setLastName(form.getLastName());
		person.setIdentityCode(form.getIdentityCode());
		person.setBirthDate(Utils.parseDate(form.getBirthDate()));
		person.setCreatedBy(Long.parseLong(form.getCreatedBy()));
		person.setCreated(new Date());

		saveOrUpdate(person);
		form.setSubjectId(String.valueOf(person.getPerson()));
		saveAddress(form.getAddressForm(), person.getPerson(), 1);
		for (AddressForm address : form.getAddressForm().getAddresses()) {
			saveAddress(address, person.getPerson(), 1);
		}
		saveContacts(form.getContacts(), person.getPerson(), 1);
		
		saveAttributes(person.getPerson(), form.getAttributes());
		if (form.getCustomer() != null) {
			saveCustomer(form, 1);
		}

		return String.valueOf(person.getPerson());
	}
	
	public String savePerson(PersonForm form) {
		if (!form.getEnterprise().isEmpty() && !form.getEntPerRelType()
				.isEmpty()) {
			EnterprisePersonRelation rel = new EnterprisePersonRelation();
			if (!form.getEntPerRelId().isEmpty()) {
				rel.setEnterprisePersonRelation(Long.parseLong(form
						.getEntPerRelId()));
			}
			rel.setPersonFk(Long.parseLong(form.getSubjectId()));
			rel.setEnterpriseFk(Long.parseLong(form.getEnterprise()));
			rel.setEntPerRelationTypeFk(Long.parseLong(
					form.getEntPerRelType()));
			
			saveOrUpdate(rel);
			
			return String.valueOf(rel.getEnterprisePersonRelation());
		} else {
			return new String();
		}
	}

	public String saveEmployee(EmployeeForm form) {
		Employee employee = new Employee();
		if (!form.getEmployeeId().isEmpty()) {
			employee.setEmployee(Long.parseLong(form.getSubjectId()));
		}
		employee.setPersonFk(Long.parseLong(form.getSubjectId()));
		employee.setEnterpriseFk(Long.parseLong(form.getEnterprise()));
		employee.setActive("Y");

		saveOrUpdate(employee);
		form.setEmployeeId(String.valueOf(employee.getEmployee()));
		
		ArrayList<EmployeeRoleForm> roles = form.getRoles();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getRoleID().isEmpty()) {
				EmployeeRole employeeRole = new EmployeeRole();
				employeeRole.setEmployeeFk(employee.getEmployee());
				employeeRole.setEmployeeRoleTypeFk(Long.parseLong(roles.get(i)
						.getRole()));
				employeeRole.setActive("Y");
				saveOrUpdate(employeeRole);
				roles.get(i).setRoleID(String.valueOf(employeeRole
						.getEmployeeRole()));
			}
		}
		saveUserAccount(form.getAccForm(), employee.getEmployee());

		saveAttributes(employee.getEmployee(), form.getEmployeeAttributes());

		return String.valueOf(employee.getEmployee());
	}

	public String saveEnterprise(EnterpriseForm form) {
		Enterprise enterprise = new Enterprise();
		if (!form.getSubjectId().isEmpty()) {
			enterprise.setEnterprise(Long.parseLong(form.getSubjectId()));
		}
		enterprise.setName(form.getName());
		enterprise.setFullName(form.getFullName());
		enterprise.setCreatedBy(Long.parseLong(form.getCreatedBy()));
		enterprise.setCreated(new Date());

		saveOrUpdate(enterprise);
		form.setSubjectId(String.valueOf(enterprise.getEnterprise()));
		saveAddress(form.getAddressForm(), enterprise.getEnterprise(), 2);
		for (AddressForm address : form.getAddressForm().getAddresses()) {
			saveAddress(address, enterprise.getEnterprise(), 2);
		}
		saveContacts(form.getContacts(), enterprise.getEnterprise(), 2);
		
		saveAttributes(enterprise.getEnterprise(), form.getAttributes());
		if (form.getCustomer() != null) {
			saveCustomer(form, 2);
		}

		return form.getSubjectId();
	}

	private void saveCustomer(SubjectForm form, long subjectTypeFk) {
		Customer customer = new Customer();
		customer.setSubjectFk(Long.parseLong(form.getSubjectId()));
		customer.setSubjectTypeFk(subjectTypeFk);
		saveOrUpdate(customer);
		
		if (form.getCustromerAttributes() != null) {
			saveAttributes(customer.getCustomer(), form.getCustromerAttributes());
		}

		form.setCustomerId(String.valueOf(customer.getCustomer()));
	}
	
	private void saveUserAccount(AccountForm form, long employeeID) {
		UserAccount account = new UserAccount();
		if (!form.getAccountId().isEmpty()) {
			account.setUserAccount(Long.parseLong(form.getAccountId()));
		}
		account.setSubjectFk(employeeID);
		account.setSubjectTypeFk((long) 3);
		account.setUsername(form.getUsername());
		account.setPassw(Utils.generateMD5(form.getUsername() +
				form.getPassword()));
		account.setValidFrom(Utils.parseDate(form.getValidFrom()));
		account.setValidTo(Utils.parseDate(form.getValidTo()));
		account.setPasswordNeverExpires(form.getPasswordNeverExpires());
		account.setStatus(form.getStatus());
		account.setCreated(Utils.parseDate(form.getCreated()));
		account.setCreatedBy(Long.parseLong(form.getCreatedBy()));
	}

	private void saveAddress(AddressForm form, long subjectFk,
			long subjectTypeFk) {
		Address address = new Address();
		if (!form.getAddressId().isEmpty()) {
			address.setAddress(Long.parseLong(form.getAddressId()));
		}
		address.setSubjectFk(subjectFk);
		address.setSubjectTypeFk(subjectTypeFk);
		address.setAddressTypeFk(Long.parseLong(form.getAddressTypeFk()));
		address.setCountry(form.getCountry());
		address.setCounty(form.getCounty());
		address.setTownVillage(form.getTownVillage());
		address.setStreetAddress(form.getStreetAddress());
		address.setZipcode(form.getZipcode());

		saveOrUpdate(address);
		form.setAddressId(String.valueOf(address.getAddress()));
	}
	
	private void saveContacts(ArrayList<ContactForm> forms, long subjectFk,
			long subjectTypeFk) {
		for (int i = 0; i < forms.size(); i++) {
			ContactForm form = forms.get(i);
			Contact contact = new Contact();
			if (!form.getContactId().isEmpty()) {
				contact.setContact(Long.parseLong(form.getContactId()));
			}
			contact.setContactTypeFk(Long.parseLong(form.getContactType()));
			contact.setNote(form.getNote());
			contact.setSubjectFk(subjectFk);
			contact.setSubjectTypeFk(subjectTypeFk);
			contact.setValueText(form.getContact());
			
			saveOrUpdate(contact);
		}
	}

	private void saveAttributes(long id, FormAttribute[] attributes) {
		for (FormAttribute attribute : attributes) {
			if (!attribute.getValue().isEmpty()) {
				SubjectAttribute subjAttr = new SubjectAttribute();
				if (!attribute.getFormAttributeId().isEmpty()) {
					subjAttr.setSubjectAttribute(Long.parseLong(attribute
							.getFormAttributeId()));
				}
				subjAttr.setSubjectFk(id);
				subjAttr.setSubjectTypeFk(attribute.getSubjectTypeFk());
				subjAttr.setSubjectAttributeTypeFk(attribute
						.getSubjectAttributeTypeFk());
				subjAttr.setOrderby(attribute.getOrderby());
				subjAttr.setDataType(attribute.getType());
				switch (subjAttr.getDataType()) {
				case 1:
					subjAttr.setValueText(attribute.getValue());
					break;
				case 2:
					subjAttr.setValueNumber(Long.parseLong(attribute.getValue()));
					break;
				case 3:
					subjAttr.setValueDate(Utils.parseDate(attribute.getValue()));
					break;
				}
				saveOrUpdate(subjAttr);
				attribute.setFormAttributeId(String.valueOf(subjAttr
						.getSubjectAttribute()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<SearchResult> search(SearchForm form) {
		Session session = null;
		List<Object[]> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String queryStr = formSearchQuery(form);
			if (queryStr != null) {
				if (!form.getQueryPart(3).isEmpty()) {
					String[] queris = queryStr.split("\t");
					data = session.createQuery(queris[0]).list();
					data.addAll(session.createQuery(queris[1]).list());
				} else {
					data = session.createQuery(queryStr).list();
				}
			} else {
				return new ArrayList<SearchResult>();
			}
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.search(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return castSearchResults(data);
	}

	@SuppressWarnings("unchecked")
	public DOMSource searchXML(SearchForm form) {
		Session session = null;
		List<Object[]> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String queryStr = formSearchQuery(form);
			if (queryStr != null) {
				if (!form.getQueryPart(3).isEmpty()) {
					String[] queris = queryStr.split("\t");
					data = session.createQuery(queris[0]).list();
					data.addAll(session.createQuery(queris[1]).list());
				} else {
					data = session.createQuery(queryStr).list();
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			MyLogger.log("SubjectsORM.search(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();

		return castSearchResultsToXML(data);

	}

	public String formSearchQuery(SearchForm form) {
		addPersonCriterias(form);
		addEnterpriseCriteria(form);
		addAddressCriterias(form);
		addContactCriterias(form);
		if (form.getSubjectType() != 0) {
			addSubjectCriterias(form);
		}
		addEmployeeCriteria(form);
		addCustomerCriteria(form);
		String queryPartOne = form.getQueryPart(0), queryPartTwo = form
				.getQueryPart(1), queryPartThree = form.getQueryPart(2), queryPartFour = form
				.getQueryPart(3);
		if (!queryPartTwo.isEmpty()) {
			if (!queryPartFour.isEmpty()) {
				return String
						.format("select distinct P.person as subject_id,"
								+ "'person' as subject_type, P.lastName as subject_name"
								+ " from%s where%s\tselect distinct E.enterprise as subject_id,"
								+ " 'enterprise' as subject_type, E.name as subject_name"
								+ " from%s where%s", queryPartOne.substring(0,
								queryPartOne.length() - 1), queryPartTwo
								.substring(0, queryPartTwo.length() - 4),
								queryPartThree.substring(0,
										queryPartThree.length() - 1),
								queryPartFour.substring(0,
										queryPartFour.length() - 4));
			} else {
				return getQueryBeginning(form) + " from"
						+ queryPartOne.substring(0, queryPartOne.length() - 1)
						+ " where"
						+ queryPartTwo.substring(0, queryPartTwo.length() - 4);
			}
		} else {
			return null;
		}
	}

	private String getQueryBeginning(SearchForm form) {
		String queryPart = "";
		if (!(form.getSubjectType() == 2)) {
			queryPart = "select distinct P.person as subject_id, 'person' as subject_type,"
					+ "P.lastName as subject_name";
		} else {
			queryPart = "select distinct E.enterprise as subject_id,"
					+ "'enterprise' as subject_type,"
					+ " E.name as subject_name";
		}
		return queryPart;
	}

	private void addPersonCriterias(SearchForm form) {
		if (!(form.getSubjectType() == 2)) {
			String queryPartOne = " Person P,", queryPartTwo = "";
			if (!form.getFirstName().isEmpty()) {
				queryPartTwo += " lower(P.firstName) LIKE '%" + form.getFirstName()
						.toLowerCase() + "%' AND";
			}
			if (!form.getLastName().isEmpty()) {
				queryPartTwo += " lower(P.lastName) LIKE '%" + form.getLastName()
						.toLowerCase() + "%' AND";
			}
			form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
			form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
		}
	}

	private void addEnterpriseCriteria(SearchForm form) {
		if (!(form.getSubjectType() == 1) && !(form.getSubjectType() == 3)) {
			String queryPartOne = "", queryPartTwo = "";
			if (form.getFirstName().isEmpty()) {
				queryPartOne = " Enterprise E,";
				if (!form.getLastName().isEmpty()) {
					queryPartTwo += " lower(E.name) LIKE '%" + form.getLastName()
							.toLowerCase() + "%' AND";
				}
			}
			if (form.getSubjectType() == 2) {
				form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
				form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
			} else {
				form.setQueryPart(form.getQueryPart(2) + queryPartOne, 2);
				form.setQueryPart(form.getQueryPart(3) + queryPartTwo, 3);
			}
		}
	}
	
	private void addEmployeeCriteria(SearchForm form) {
		if (form.getSubjectType() == 3 && !form.getQueryPart(1).isEmpty()) {
			form.setQueryPart(form.getQueryPart(0) + " Employee Em,", 0);
			form.setQueryPart(form.getQueryPart(1) + " Em.personFk"
					+ " = P.person AND", 1);
		}
	}
	
	private void addCustomerCriteria(SearchForm form) {
		if (form.getSubjectType() == 4 && !form.getQueryPart(1).isEmpty()) {
			String queryPartOne = " Customer Cu,";
			form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
			form.setQueryPart(form.getQueryPart(1) + " Cu.subjectFk = P.person"
					+ " AND Cu.subjectTypeFk = 1 AND", 1);
			if (!form.getQueryPart(3).isEmpty()) {
				form.setQueryPart(form.getQueryPart(2) + queryPartOne, 2);
				form.setQueryPart(form.getQueryPart(3) + " Cu.subjectFk = E.enterprise"
						+ " AND Cu.subjectTypeFk = 2 AND", 3);
			}
		}
	}

	private void addAddressCriterias(SearchForm form) {
		AddressForm aform = form.getAddressForm();
		String queryPartOne = " Address A,", queryPartTwo = "", queryPartFour = "";
		if (!aform.getCountry().isEmpty()) {
			queryPartTwo += " lower(A.country) LIKE '%" + aform.getCountry()
					.toLowerCase() + "%' AND";
		}
		if (!aform.getCounty().isEmpty()) {
			queryPartTwo += " lower(A.county) LIKE '%" + aform.getCounty()
					.toLowerCase() + "%' AND";
		}
		if (!aform.getTownVillage().isEmpty()) {
			queryPartTwo += " lower(A.townVillage) LIKE '%" + aform.getTownVillage()
					.toLowerCase() + "%' AND";
		}
		if (!aform.getStreetAddress().isEmpty()) {

			queryPartTwo += " lower(A.streetAddress) LIKE '%" + aform.getStreetAddress()
					.toLowerCase() + "%' AND";
		}
		if (!aform.getZipcode().isEmpty()) {
			queryPartTwo += " lower(A.zipcode) LIKE '%" + aform.getZipcode()
					.toLowerCase() + "%' AND";
		}
		queryPartFour = queryPartTwo + " E.enterprise = A.subjectFk"
				+ " AND A.subjectTypeFk = 2 AND";
		if (!queryPartTwo.isEmpty()) {
			if (!(form.getSubjectType() == 2)) {
				queryPartTwo += " P.person = A.subjectFk AND A.subjectTypeFk = 1 AND";
			} else {
				queryPartTwo += " E.enterprise = A.subjectFk"
						+ " AND A.subjectTypeFk = 2 AND";
			}
			if (form.getSubjectType() == 0 || form.getSubjectType() == 4) {
				form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
				form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
				form.setQueryPart(form.getQueryPart(2) + queryPartOne, 2);
				form.setQueryPart(form.getQueryPart(3) + queryPartFour, 3);
			} else {
				form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
				form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
			}
		}
	}
	
	private void addContactCriterias(SearchForm form) {
		ContactForm cform = form.getContactForm();
		String queryPartOne = " Contact C,", queryPartTwo = "", queryPartFour = "";
		if (!cform.getContact().isEmpty()) {
			queryPartTwo += " lower(C.valueText) LIKE '%" + cform.getContact()
					.toLowerCase() + "%' AND";
		}
		if (!cform.getNote().isEmpty()) {
			queryPartTwo += " lower(C.note) LIKE '%" + cform.getNote()
					.toLowerCase() + "%' AND";
		}
		if (!queryPartTwo.isEmpty()) { 
			queryPartTwo += " C.contactTypeFk=" + Long.parseLong(cform
					.getContactType()) + " AND";
			queryPartFour += queryPartTwo + " E.enterprise = C.subjectFk"
					+ " AND C.subjectTypeFk = 2 AND";
			if (!(form.getSubjectType() == 2)) {
				queryPartTwo += " P.person = C.subjectFk AND C.subjectTypeFk = 1 AND";
			} else {
				queryPartTwo += " E.enterprise = C.subjectFk"
						+ " AND C.subjectTypeFk = 2 AND";
			}
			if (form.getSubjectType() == 0 || form.getSubjectType() == 4) {
				form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
				form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
				form.setQueryPart(form.getQueryPart(2) + queryPartOne, 2);
				form.setQueryPart(form.getQueryPart(3) + queryPartFour, 3);
			} else {
				form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
				form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
			}
		}
	}

	private void addSubjectCriterias(SearchForm form) {
		String queryPartOne = " SubjectAttribute S,", queryPartTwo = "";
		ArrayList<SearchAttribute> attributes = form.getAttributes();
		for (SearchAttribute attribute : attributes) {
			if (!attribute.getFirstValue().isEmpty()) {
				int type = Integer.parseInt(attribute.getType());
				switch(type) {
				case 1:
					queryPartTwo += " lower(S.valueText) LIKE '%" + attribute
							.getFirstValue().toLowerCase() + "%' AND";
					break;
				case 2:
					queryPartTwo += " S.valueNumber BETWEEN " + attribute
							.getFirstValue() + " AND " + attribute
							.getSecondValue() + " AND";
					break;
				case 3:
					queryPartTwo += " S.valueDate BETWEEN " + attribute
							.getFirstValue() + " AND " + attribute
							.getSecondValue() + " AND";
					break;
				}
				queryPartTwo += " S.subjectAttributeTypeFk=" + Long.parseLong(
						attribute.getAttrID()) + " AND";
				switch((int) form.getSubjectType()) {
				case 1:
					queryPartTwo += " S.subjectFk = P.person" +
							" AND S.subjectTypeFk = 1 AND";
					break;
				case 2:
					queryPartTwo += " S.subjectFk = E.enterprise" +
							" AND S.subjectTypeFk = 2 AND";
					break;
				case 3:
					queryPartTwo += " S.subjectFk = Em.employee"
							+ " AND S.subjectTypeFk = 3 AND";
					break;
				case 4:
					queryPartTwo += " S.subjectFk = Cu.customer"
							+ " AND S.subjectTypeFk = 4 AND";
					form.setQueryPart(form.getQueryPart(2) + queryPartOne, 2);
					form.setQueryPart(form.getQueryPart(3) + queryPartTwo, 3);
					break;
				}
				form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
				form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
			}
		}
		
	}

	private ArrayList<SearchResult> castSearchResults(List<Object[]> data) {
		if (data != null) {
			ArrayList<SearchResult> results = new ArrayList<SearchResult>();
			for (Object[] objects : data) {
				SearchResult res = new SearchResult();
				res.setSubjectId((Long) objects[0]);
				res.setSubjectType((String) objects[1]);
				res.setSubjectName((String) objects[2]);
				results.add(res);
			}
			return results;
		} else {
			return null;
		}
	}

	private DOMSource castSearchResultsToXML(List<Object[]> data) {
		if (data != null) {
			Document doc = null;
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root elements
				doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("subjects");
				doc.appendChild(rootElement);
				rootElement.setAttribute("quantity", String.valueOf(data.size()));

				for (Object[] objects : data) {
					// subject elements
					Element subject = doc.createElement("subject");
					rootElement.appendChild(subject);

					// id elements
					Element id = doc.createElement("id");
					id.appendChild(doc.createTextNode(String.valueOf(objects[0])));
					subject.appendChild(id);

					// type elements
					Element type = doc.createElement("type");
					type.appendChild(doc.createTextNode((String) objects[1]));
					subject.appendChild(type);

					// name elements
					Element name = doc.createElement("name");
					name.appendChild(doc.createTextNode((String) objects[2]));
					subject.appendChild(name);

				}
			} catch (Exception e) {
				MyLogger.log("SubjectsORM.castSearchResultsToXML(): ",
						e.getMessage());
			}

			return new DOMSource(doc);
			
		} else {

			return null;
		}
	}

}