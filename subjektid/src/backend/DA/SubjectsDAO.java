package backend.DA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import backend.model.Address;

public class SubjectsDAO {
	
	String url, user, password;
	
	public SubjectsDAO() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("DBConnection");
			url = bundle.getString("url");
			user = bundle.getString("user");
			password = bundle.getString("password");
			Class.forName(bundle.getString("Driver"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SubjectsDAO dao = new SubjectsDAO();
		dao.findAddresses();
	}
	
	public void findAddresses() {
		List<Address> addresses = null;
		Connection db;
		ResultSet results;
		try {
			db = DriverManager.getConnection(url, user, password);
			Statement st = db.createStatement();
			addresses = new ArrayList<Address>();
			results = st.executeQuery("SELECT * FROM address");
			while(results.next()) {
				Address address = new Address();
				address.setAddress(results.getLong("address"));
				address.setAddressTypeFk(results.getLong("address_type_fk"));
				address.setSubjectFk(results.getLong("subject_fk"));
				address.setSubjectTypeFk(results.getLong("subject_type_fk"));
				address.setCountry(results.getString("country"));
				address.setCounty(results.getString("county"));
				address.setTownVillage(results.getString("town_village"));
				address.setStreetAddress(results.getString("street_address"));
				address.setZipcode(results.getString("zipcode"));
				addresses.add(address);
			}
			db.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			for (int i = 0; i < addresses.size(); i++) {
				Address address = addresses.get(i);
				System.out.printf("%d\t%d\t%d\t%d\t%s\t%s\t%s\t%s\t%s\r\n", 
						address.getAddress(), address.getAddressTypeFk(),
						address.getSubjectFk(), address.getSubjectTypeFk(),
						address.getCountry(), address.getCounty(),
						address.getTownVillage(), address.getStreetAddress(),
						address.getZipcode());
			}
		}

	}
}