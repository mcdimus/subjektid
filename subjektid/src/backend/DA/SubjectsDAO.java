package backend.DA;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import frontend.forms.LoginForm;

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
		form.setPassword(generateMD5(form.getUsername() + form.getPassword()));
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
	
	static String generateMD5(String passw) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			BigInteger bi = new BigInteger(1, m.digest(passw.getBytes()));
			return String.format("%1$032X", bi);
		} catch (NoSuchAlgorithmException e) {
			MyLogger.log("SubjectsDAO.generateMD5(): ", e.getMessage());
			e.printStackTrace();
			return null;
		}
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