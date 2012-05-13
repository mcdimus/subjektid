package backend.DA;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
			e.printStackTrace();
		}
		session.close();
		return data;
	}

    @SuppressWarnings("unchecked")
	public <T> List<T> findByID(Class<T> _class, int id) {
		Session session = null;
		List<T> data = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from " + _class.getName()
					+ " where subject_fk=:subj_id");
			q.setInteger("subj_id", id);		
		    data = (List<T>) q.list();
		} catch(Exception e) {
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
			 e.printStackTrace();
		      if (tx != null) {
		    	  tx.rollback();
		      }
		      return false;
		 } 
	}
}
