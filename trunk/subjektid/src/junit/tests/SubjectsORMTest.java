package junit.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import backend.DA.SubjectsORM;
import backend.model.Address;
import backend.model.AddressType;

public class SubjectsORMTest {
	
	private SubjectsORM orm = new SubjectsORM();
	
	@Test
	public void testFindAll() {
		List<AddressType> types = orm.findAll(AddressType.class);
		assertNotNull("List<AddressType> types are null!", types);
		assertTrue("types.size = 0", types.size() > 0);
	}

	@Test
	public void testFindById() {
		List<Address> addresses = orm.findByID(Address.class, 1);
//		for (int i = 0; i < addresses.size(); i++) {
//			Address address = addresses.get(i);
//			System.out.printf("%d\t%d\t%d\t%d\t%s\t%s\t%s\t%s\t%s\r\n", 
//					address.getAddress(), address.getAddressTypeFk(),
//					address.getSubjectFk(), address.getSubjectTypeFk(),
//					address.getCountry(), address.getCounty(),
//					address.getTownVillage(), address.getStreetAddress(),
//					address.getZipcode());
//		}
		assertNotNull("List<Address> addresses are null!", addresses);
		assertTrue("addresses.size() = 0", addresses.size() > 0);
	}
	
	@Test
	public void testSaveOrUpdate() {
		List<Address> addresses = orm.findByID(Address.class, 1);
		Address address = addresses.get(2);
		address.setCounty("Stockholm");
		assertTrue("saveOrUpdateAddress() = false",
				orm.saveOrUpdate(address));
	}

}
