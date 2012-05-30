package backend.DA;

import frontend.forms.AddressForm;
import frontend.forms.EmployeeForm;
import frontend.forms.EnterpriseForm;
import frontend.forms.FormAttribute;
import frontend.forms.HumanForm;
import frontend.forms.SearchForm;
import frontend.forms.SubjectForm;
import general.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
		try {
			person.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(
					form.getBirthDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

	@SuppressWarnings("unchecked")
	public <T> List<T> search(SearchForm form, Class<T> _class) {
		Session session = null;
		List<T> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String queryStr = "from " + _class.getName() + " where";
			for (int i = 1; i <= 3; i++) {
				if (form.getMap(i) != null) {
					if (i == 1) {
						queryStr += addStringCriterias(form.getMap(i));
					} else {
						queryStr += addNumDateCriterias(form.getMap(i));
					}
				}
			}
			queryStr = queryStr.substring(0, queryStr.length() - 4);
		    data = (List<T>) session.createQuery(queryStr).list();
		} catch(Exception e) {
			MyLogger.log("SubjectsORM.search(): ", e.getMessage());
			e.printStackTrace();
		}
		session.close();
		return data;
	}
	
	private String addStringCriterias(HashMap<String, String> criterias) {
		String queryPart = "";
		for (String key : criterias.keySet()) {
			queryPart += " " + key + " like '" + criterias.get(key) + "%' AND";
		}
		return queryPart;
	}
	
	private String addNumDateCriterias(HashMap<String, String> criterias) {
		String queryPart = "";
		for (String key : criterias.keySet()) {
			String[] words = criterias.get(key).split(" ");
			queryPart += String.format(" %s between '%s' AND '%s' AND",
					key, words[0], words[1]);
		}
		return queryPart;
	}
}
