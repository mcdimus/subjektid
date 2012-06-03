package backend.DA;

import frontend.forms.AddressForm;
import frontend.forms.EmployeeForm;
import frontend.forms.EnterpriseForm;
import frontend.forms.FormAttribute;
import frontend.forms.HumanForm;
import frontend.forms.SearchAttribute;
import frontend.forms.SearchForm;
import frontend.forms.SearchResult;
import frontend.forms.SubjectForm;
import general.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import log.MyLogger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import backend.model.Address;
import backend.model.Customer;
import backend.model.Employee;
import backend.model.EmployeeRole;
import backend.model.Enterprise;
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
		    data = (List<T>) session.createQuery("from "
		    		+ _class.getName()).list();
		} catch(Exception e) {
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
		} catch(Exception e) {
			MyLogger.log("SubjectsORM.findById(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return data;
	}
    
    @SuppressWarnings("unchecked")
   	public <T> List<T> findByID(Class<T> _class, String attribute, long id) {
   		Session session = null;
   		List<T> data = null;
   		try {
   			session = HibernateUtil.getSessionFactory().getCurrentSession();
   			session.beginTransaction();
   			Query q = session.createQuery("from " + _class.getName()
   					+ " where " + attribute + "=:id");
   			q.setLong("id", id);	
   		    data = (List<T>) q.list();
   		} catch(Exception e) {
   			MyLogger.log("SubjectsORM.findById(): ", e.getMessage());
   			e.printStackTrace();
   		}
   		session.close();
   		return data;
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
		 } catch(Exception e) {
			MyLogger.log("SubjectsORM.saveOrUpdate(): ", e.getMessage());
			 e.printStackTrace();
		      if (tx != null) {
		    	  tx.rollback();
		      }
		      return false;
		 } 
	}
	
	public boolean saveUserAcc(UserAccount user) {
		user.setPassw(Utils.generateMD5(user.getUsername()
				+ user.getPassw()));
		return saveOrUpdate(user);
	}
	
	public String saveHuman(HumanForm form) {
		Person person = new Person();
		if (!Utils.checkEmpty(form.getSubjectId())) {
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
		for (AddressForm address : form.getAddresses()) {
			saveAddress(address, person.getPerson(), 1);
		}
		saveAttributes(person.getPerson(), form.getAttributes());
		if (form.getCustomer() != null) {
			saveCustomer(form, 1);
		}
		
		return form.getSubjectId();
	}
	
	public String saveEmployee(EmployeeForm form) {
		long subjId = Long.parseLong(form.getSubjectId());
		Employee employee = new Employee();
		employee.setPersonFk(subjId);
		if (!Utils.checkEmpty(form.getEmployeeId())) {
			employee.setEmployee(Long.parseLong(form.getSubjectId()));
		}
		employee.setEnterpriseFk(Long.parseLong(form.getEnterprise()));
		employee.setActive("Y");
		
		saveOrUpdate(employee);
		form.setEmployeeId(String.valueOf(employee.getEmployee()));
		
		EmployeeRole employeeRole = new EmployeeRole();
		employeeRole.setEmployeeFk(employee.getEmployee());
		employeeRole.setEmployeeRoleTypeFk(Long.parseLong(form
				.getEmployeeRoleType()));
		employeeRole.setActive("Y");
		
		saveAttributes(subjId, form.getEmployeeAttributes());
		
		return String.valueOf(employee.getEmployee());
	}
	
	public String saveEnterprise(EnterpriseForm form) {
		Enterprise enterprise = new Enterprise();
		if (!Utils.checkEmpty(form.getSubjectId())) {
			enterprise.setEnterprise(Long.parseLong(form.getSubjectId()));
		}
		enterprise.setName(form.getName());
		enterprise.setFullName(form.getFullName());
		enterprise.setCreatedBy(Long.parseLong(form.getCreatedBy()));
		enterprise.setCreated(new Date());
		
		saveOrUpdate(enterprise);
		form.setSubjectId(String.valueOf(enterprise.getEnterprise()));
		saveAddress(form.getAddressForm(), enterprise.getEnterprise(), 2);
		for (AddressForm address : form.getAddresses()) {
			saveAddress(address, enterprise.getEnterprise(), 2);
		}
		saveAttributes(enterprise.getEnterprise(), form.getAttributes());
		if (form.getCustomer() != null) {
			saveCustomer(form, 2);
		}
		
		return form.getSubjectId();
	}
	
	private void saveAddress(AddressForm form, long subjectFk, 
			long subjectTypeFk) {
		Address address = new Address();
		if (!Utils.checkEmpty(form.getAddressId())) {
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
	
	private void saveAttributes(long id, FormAttribute[] attributes) {
		for (FormAttribute attribute : attributes) {
			if (attribute.getValue().length() != 0) {
				SubjectAttribute subjAttr = new SubjectAttribute();
				subjAttr.setSubjectFk(id);
				subjAttr.setSubjectTypeFk(attribute.getSubjectTypeFk());
				subjAttr.setSubjectAttributeTypeFk(attribute
						.getSubjectAttributeTypeFk());
				subjAttr.setOrderby(attribute.getOrderby());
				subjAttr.setDataType(attribute.getType());
				switch(subjAttr.getDataType()) {
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
	
	private void saveCustomer(SubjectForm form, long subjectTypeFk) {
		Customer customer = new Customer();
		customer.setSubjectFk(Long.parseLong(form.getSubjectId()));
		customer.setSubjectTypeFk(subjectTypeFk);
		saveOrUpdate(customer);
		
		form.setCustomerId(String.valueOf(customer.getCustomer()));
	}
	
//	private String addStringCriterias(HashMap<String, String> criterias) {
//		String queryPart = "";
//		for (String key : criterias.keySet()) {
//			queryPart += " " + key + " like '" + criterias.get(key) + "%' AND";
//		}
//		return queryPart;
//	}
//	
//	private String addNumDateCriterias(HashMap<String, String> criterias) {
//		String queryPart = "";
//		for (String key : criterias.keySet()) {
//			String[] words = criterias.get(key).split(" ");
//			queryPart += String.format(" %s between '%s' AND '%s' AND",
//					key, words[0], words[1]);
//		}
//		return queryPart;
//	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SearchResult> search(SearchForm form) {
		Session session = null;
		List<Object[]> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String queryStr = formSearchQuery(form);
			if (queryStr != null) {
				if (!Utils.checkEmpty(form.getQueryPart(3))) {
					String[] queris = queryStr.split("\t");
					data = session.createQuery(queris[0]).list();
					data.addAll(session.createQuery(queris[1]).list());
				} else {
					data = session.createQuery(queryStr).list();
				}
			} else {
				return new ArrayList<SearchResult>();
			}
		} catch(Exception e) {
			MyLogger.log("SubjectsORM.search(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return castSearchResults(data);
	}
	
	public String formSearchQuery(SearchForm form) {
		addPersonCriterias(form);
		addEnterpriseCriteria(form);
		addAddressCriterias(form);
		String queryPartOne = form.getQueryPart(0), queryPartTwo
				= form.getQueryPart(1), queryPartThree = form.getQueryPart(2),
				queryPartFour = form.getQueryPart(3);
		if (!Utils.checkEmpty(queryPartTwo)) {
			if (!Utils.checkEmpty(queryPartFour)) {
				return String.format("select P.person as subject_id,"
						+ "'person' as subject_type, P.lastName as subject_name"
						+ " from%s where%s\tselect E.enterprise as subject_id,"
						+ " 'enterprise' as subject_type, E.name as subject_name"
						+ " from%s where%s",
						queryPartOne.substring(0, queryPartOne.length() - 1),
						queryPartTwo.substring(0, queryPartTwo.length() - 4),
						queryPartThree.substring(0, queryPartThree.length() - 1),
						queryPartFour.substring(0, queryPartFour.length() - 4));
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
		if (!(form.getSubjectType() == 0) && !(form.getSubjectType() == 4)) {
			if (!(form.getSubjectType() == 2 )) {
				queryPart = "select P.person as subject_id, 'person' as subject_type,"
						+ "P.lastName as subject_name";
			} else {
				queryPart = "select E.enterprise as subject_id,"
						+ "'enterprise' as subject_type,"
						+ "E.name as subject_name";
			}
		}
		return queryPart;
	}
	
	private void addPersonCriterias(SearchForm form) {
		if (!(form.getSubjectType() == 2)) {
			String queryPartOne = " Person P,", queryPartTwo = "";
			if (!Utils.checkEmpty(form.getFirstName())) {
				queryPartTwo += " P.firstName LIKE '%" + form.getFirstName()
						+ "%' AND";
			}
			if (!Utils.checkEmpty(form.getLastName())) {
				queryPartTwo += " P.lastName LIKE '%" + form.getLastName()
						+ "%' AND";
			}
			form.setQueryPart(form.getQueryPart(0) + queryPartOne, 0);
			form.setQueryPart(form.getQueryPart(1) + queryPartTwo, 1);
		}
	}
	
	private void addEnterpriseCriteria(SearchForm form) {
		if (!(form.getSubjectType() == 1) && !(form.getSubjectType() == 3)) {
			String queryPartOne = "", queryPartTwo = "";
			if (Utils.checkEmpty(form.getFirstName())) {
				queryPartOne = " Enterprise E,";
				if(!Utils.checkEmpty(form.getLastName())) {
					queryPartTwo += " E.name LIKE '%" + form.getLastName()
							+ "%' AND";
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
	
	private void addAddressCriterias(SearchForm form) {
		AddressForm aform = form.getAddressForm();
		String queryPartOne = " Address A,", queryPartTwo = "",
				queryPartFour = "";
		if (!Utils.checkEmpty(aform.getCountry())) {
			queryPartTwo += " A.country LIKE '%" + aform.getCountry() + "%' AND";
		}
		if (!Utils.checkEmpty(aform.getCounty())) {
			queryPartTwo += " A.county LIKE '%" + aform.getCounty() + "%' AND";
		}
		if (!Utils.checkEmpty(aform.getTownVillage())) {
			queryPartTwo += " A.townVillage LIKE '%" + aform.getTownVillage()
					+ "%' AND";
		}
		if (!Utils.checkEmpty(aform.getStreetAddress())) {
			queryPartTwo += " A.streetAddress LIKE '%" + aform.getStreetAddress()
					+ "%' AND";
		}
		if (!Utils.checkEmpty(aform.getZipcode())) {
			queryPartTwo += " A.zipcode LIKE '%" + aform.getZipcode() + "%' AND";
		}
		queryPartFour = queryPartTwo;
		if (!Utils.checkEmpty(queryPartTwo)) {
			if (!(form.getSubjectType() == 2)) {
				queryPartTwo += " P.person = A.subjectFk AND A.subjectTypeFk = 1 AND";
			if (!(form.getSubjectType() == 1)
				&& !(form.getSubjectType() == 3)) {
					queryPartFour += " E.enterprise = A.subjectFk"
							+ " AND A.subjectTypeFk = 2 AND";
				}
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
		String queryPartOne = " SubjectAttribute ", queryPartTwo = "";
		ArrayList<SearchAttribute> attributes = form.getAttributes();
		for (SearchAttribute attribute : attributes) {
			 // TODO: in progress
		}
	}
	
	private ArrayList<SearchResult> castSearchResults(List<Object[]> data) {
		ArrayList<SearchResult> results = new ArrayList<SearchResult>(); 
		for (Object[] objects: data) {
			SearchResult res = new SearchResult();
			res.setSubjectId((Long) objects[0]);
			res.setSubjectType((String) objects[1]);
			res.setSubjectName((String) objects[2]);
			results.add(res);
		}
		return results;
	}
	
}
