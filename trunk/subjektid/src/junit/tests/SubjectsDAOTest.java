package junit.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.DA.SubjectsDAO;
import backend.model.Employee;
import backend.model.UserAccount;

public class SubjectsDAOTest {
	
	private SubjectsDAO dao = new SubjectsDAO();

	@Test
	public void authTest() {
		UserAccount ua = new UserAccount();
		ua.setUsername("juhan");
		ua.setPassw("juhan");
		Employee employee = dao.auth(ua);
		assertNotNull("Employee is null!", employee);
	}

}
