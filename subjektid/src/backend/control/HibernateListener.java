package backend.control;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import backend.DA.HibernateUtil;

public class HibernateListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateUtil.getSessionFactory();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.getSessionFactory().close();
	}

}
