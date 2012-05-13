package backend.DA;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import backend.model.Address;
import backend.model.AddressType;
import backend.model.Contact;

public class SubjectsORM {

    @SuppressWarnings("unchecked")
	public List<Address> findAddressesByID(int id) {
		Session session = null;
		List<Address> addresses = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from Address where"
					+ " subject_fk=:subj_id");
			q.setInteger("subj_id", id);		
		    addresses = (List<Address>) q.list();
		} catch(Exception e) {
			e.printStackTrace();
		}
		session.close();
		return addresses;
	}
	
	public boolean saveOrUpdateAddress(Address address) {
		 Transaction tx = null;
		 Session session = null;
		 try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		    tx = session.beginTransaction();
		    session.saveOrUpdate(address);
			tx.commit();
			return true;
		 } catch(Exception e) {
			 e.printStackTrace();
		      if (tx != null) {
		    	  tx.rollback();
		      }
		      return false;
		 } 
	}
	
	@SuppressWarnings("unchecked")
	public List<AddressType> findAddressTypes(int id) {
		Session session = null;
		List<AddressType> types = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
		    types = (List<AddressType>) session.createQuery("from AddressType")
		    		.list();
		} catch(Exception e) {
			e.printStackTrace();
		}
		session.close();
		return types;
	}
    
	@SuppressWarnings("unchecked")
	public List<Contact> findContactsByID(int id) {
		Session session = null;
		List<Contact> contacts = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from Contact where"
					+ " subject_fk=:subj_id");
			q.setInteger("subj_id", id);
			contacts = q.list();
		} catch(Exception e) {
			e.printStackTrace();
		}
		session.close();
		return contacts;
	}
	
	public boolean saveOrUpdateContact(Contact contact) {
		 Transaction tx = null;
		 Session session = null;
		 try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		    tx = session.beginTransaction();
		    session.saveOrUpdate(contact);
			tx.commit();
			return true;
		 } catch(Exception e) {
			 e.printStackTrace();
		      if (tx != null) {
		    	  tx.rollback();
		      }
		      return false;
		 } 
	}
}
