package backend.DA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import frontend.forms.LoginForm;
import general.Utils;

import log.MyLogger;

import backend.model.Employee;

public class SubjectsDAO {
	
	private String url, user, password;
	
	public SubjectsDAO() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("DBConnection");
			url = bundle.getString("url");
			user = bundle.getString("user");
			password = bundle.getString("password");
			Class.forName(bundle.getString("Driver"));
		} catch(Exception e) {
			MyLogger.log("SubjectsDAO(): ", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Employee auth(LoginForm form) {
		form.setPassword(Utils.generateMD5(form.getUsername() + form.getPassword()));
		Connection db;
		ResultSet results;
		Employee employee = null;
		try {
			db = DriverManager.getConnection(url, user, password);
			Statement st = db.createStatement();
			results = st.executeQuery("SELECT e.employee, e.person_fk,"
					+ "e.enterprise_fk, e.struct_unit_fk, e.active FROM"
					+ " employee E INNER JOIN user_account UA ON E.employee"
					+ "= UA.subject_fk WHERE UA.username='" + form.getUsername()
					+ "' AND UA.passw='" + form.getPassword() + "'");
			if (results.next()) {
				employee = new Employee(results.getLong("employee"),
						results.getLong("person_fk"),
						results.getLong("enterprise_fk"),
						results.getLong("struct_unit_fk"),
						results.getString("active"));
			}
			db.close();
		} catch(Exception e) {
			MyLogger.log("SubjectsDAO.auth(): ", e.getMessage());
			e.printStackTrace();
		}
		return employee;
	}
	
	public String deletePerson(String id) {
		String answer = null;
		Connection db;
		ResultSet results;
		try {
			db = DriverManager.getConnection(url, user, password);
			Statement st = db.createStatement();
			results = st.executeQuery("SELECT func_delete_person AS answer FROM func_delete_person("+id+");");
			
			if (results.next()) {
				
				answer = results.getString("answer");
			}
			db.close();
		} catch(Exception e) {
			MyLogger.log("SubjectsDAO.deletePerson(): ", e.getMessage());
			e.printStackTrace();
		}
		
		return answer;
	}
	
	public String deleteEnterprise(String id) {
		String answer = null;
		Connection db;
		ResultSet results;
		try {
			db = DriverManager.getConnection(url, user, password);
			Statement st = db.createStatement();
			results = st.executeQuery("SELECT func_delete_enterprise AS answer FROM func_delete_enterprise("+id+");");
			
			if (results.next()) {
				
				answer = results.getString("answer");
			}
			db.close();
		} catch(Exception e) {
			MyLogger.log("SubjectsDAO.deleteEnterprise(): ", e.getMessage());
			e.printStackTrace();
		}
		
		return answer;
	}

	public String deleteContact(String id) {
		String answer = null;
		Connection db;
		int results = 0;
		try {
			db = DriverManager.getConnection(url, user, password);
			Statement st = db.createStatement();
			results = st.executeUpdate("DELETE FROM contact WHERE contact="+id+";");
			
			MyLogger.logMessage("Deleted " + results + " contacts with id " + id);
			
			if (results > 0) {
				
				answer = "OK";
			}else {
				answer = "FAIL";
			}
			db.close();
		} catch(Exception e) {
			MyLogger.log("SubjectsDAO.deleteEnterprise(): ", e.getMessage());
			e.printStackTrace();
		}
		
		return answer;
	}

	public String deleteAccount(String id) {
		String answer = null;
		Connection db;
		int results = 0;
		try {
			db = DriverManager.getConnection(url, user, password);
			Statement st = db.createStatement();
			results = st.executeUpdate("DELETE FROM user_account WHERE user_account="+id+";");
			
			MyLogger.logMessage("Deleted " + results + " account with id " + id);
			
			if (results > 0) {
				
				answer = "OK";
			} else {
				answer = "FAIL";
			}
			db.close();
		} catch(Exception e) {
			MyLogger.log("SubjectsDAO.deleteEnterprise(): ", e.getMessage());
			e.printStackTrace();
		}
		
		return answer;
	}
	
//	public void findAddresses() {
//		List<Address> addresses = null;
//		Connection db;
//		ResultSet results;
//		try {
//			db = DriverManager.getConnection(url, user, password);
//			Statement st = db.createStatement();
//			addresses = new ArrayList<Address>();
//			results = st.executeQuery("SELECT * FROM address");
//			while(results.next()) {
//				Address address = new Address();
//				address.setAddress(results.getLong("address"));
//				address.setAddressTypeFk(results.getLong("address_type_fk"));
//				address.setSubjectFk(results.getLong("subject_fk"));
//				address.setSubjectTypeFk(results.getLong("subject_type_fk"));
//				address.setCountry(results.getString("country"));
//				address.setCounty(results.getString("county"));
//				address.setTownVillage(results.getString("town_village"));
//				address.setStreetAddress(results.getString("street_address"));
//				address.setZipcode(results.getString("zipcode"));
//				addresses.add(address);
//			}
//			db.close();
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			for (int i = 0; i < addresses.size(); i++) {
//				Address address = addresses.get(i);
//				System.out.printf("%d\t%d\t%d\t%d\t%s\t%s\t%s\t%s\t%s\r\n", 
//						address.getAddress(), address.getAddressTypeFk(),
//						address.getSubjectFk(), address.getSubjectTypeFk(),
//						address.getCountry(), address.getCounty(),
//						address.getTownVillage(), address.getStreetAddress(),
//						address.getZipcode());
//			}
//		}
//	}
	
}