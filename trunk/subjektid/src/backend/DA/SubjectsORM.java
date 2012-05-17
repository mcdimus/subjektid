package backend.DA;

import frontend.forms.AddressForm;
import frontend.forms.PersonForm;
import frontend.forms.SearchForm;
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
import backend.model.Person;
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
	public <T> T findByID(Class<T> _class, int id) {
		Session session = null;
		T data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from " + _class.getName()
					+ " obj where obj.id=:id");
			q.setInteger("id", id);	
		    data = (T) q.uniqueResult();
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
	
	public long savePerson(PersonForm form) {
		Person person = new Person();
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
		saveAddress(form.getAddressForm());
		
		// TODO: attributes addendum
		return person.getPerson();
	}
	
	private void saveAddress(AddressForm form) {
		Address address = new Address();
		address.setAddressTypeFk(Long.parseLong(form.getAddressTypeFk()));
		address.setCountry(form.getCountry());
		address.setCounty(form.getCounty());
		address.setTownVillage(form.getTownVillage());
		address.setStreetAddress(form.getStreetAddress());
		address.setZipcode(form.getZipcode());
		
		saveOrUpdate(address);
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
						queryStr += addIntDateCriterias(form.getMap(i));
					}
				}
			}
			queryStr = queryStr.substring(0, queryStr.length() - 4);
			System.out.println(queryStr);
		    data = (List<T>) session.createQuery(queryStr).list();
		} catch(Exception e) {
			MyLogger.log("SubjectsORM.findById(): ", e.getMessage());
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
	
	private String addIntDateCriterias(HashMap<String, String> criterias) {
		String queryPart = "";
		for (String key : criterias.keySet()) {
			String[] words = criterias.get(key).split(" ");
			queryPart += String.format(" %s between '%s' AND '%s' AND",
					key, words[0], words[1]);
		}
		return queryPart;
	}
}
