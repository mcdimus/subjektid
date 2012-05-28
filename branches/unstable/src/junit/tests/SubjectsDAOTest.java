package junit.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import frontend.forms.LoginForm;

import backend.DA.SubjectsDAO;
import backend.model.Employee;

public class SubjectsDAOTest {
	
	private SubjectsDAO dao = new SubjectsDAO();

	@Test
	public void authTest() {
		LoginForm form = new LoginForm();
		form.setUsername("juhan");
		form.setPassword("juhan");
		Employee employee = dao.auth(form);
		assertNotNull("Employee is null!", employee);
	}

}
