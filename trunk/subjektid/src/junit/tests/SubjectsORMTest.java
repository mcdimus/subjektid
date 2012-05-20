package junit.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import frontend.forms.SearchForm;

import backend.DA.SubjectsORM;
import backend.model.Address;
import backend.model.AddressType;
import backend.model.Person;
import backend.model.SubjectAttributeType;

public class SubjectsORMTest {
	
	private SubjectsORM orm = new SubjectsORM();
	
	@Test
	public void testFindAll() {
		List<AddressType> types = orm.findAll(AddressType.class);
		assertNotNull("List<AddressType> types are null!", types);
		assertTrue("types.size = 0", types.size() > 0);
	}

	@Test
	public void testFindByIdFirst() {
		Address address = orm.findByID(Address.class, 3);
//		for (int i = 0; i < addresses.size(); i++) {
//			Address address = addresses.get(i);
//			System.out.printf("%d\t%d\t%d\t%d\t%s\t%s\t%s\t%s\t%s\r\n", 
//					address.getAddress(), address.getAddressTypeFk(),
//					address.getSubjectFk(), address.getSubjectTypeFk(),
//					address.getCountry(), address.getCounty(),
//					address.getTownVillage(), address.getStreetAddress(),
//					address.getZipcode());
//		}
		assertNotNull("List<Address> addresses are null!", address);
	}
	
	@Test
	public void testFindByIdSecond() {
		List<SubjectAttributeType> types = orm.findByID(
				SubjectAttributeType.class, "subjectTypeFk", 1);
//		for (SubjectAttributeType type : types) {
//			System.out.println(type.getTypeName());
//		}
		assertNotNull("List<AddressType> types are null!", types);
		assertTrue("types.size = 0", types.size() > 0);
	}
	
	@Test
	public void testSaveOrUpdate() {
		Address address = orm.findByID(Address.class, 3);
		address.setCounty("Stockholm");
		assertTrue("saveOrUpdateAddress() = false",
				orm.saveOrUpdate(address));
	}
	
	@Test
	public void testSearch() {
		SearchForm form = new SearchForm();
		HashMap<String, String> criterias = new HashMap<String, String>();
//		criterias.put("birthDate", "11-11-1960 11-11-1970");
//		form.setMap(criterias, 3);
		criterias.put("identityCode", "5");
		form.setMap(criterias, 1);
		List<Person> persons = orm.search(form, Person.class);
		assertNotNull("List<Person> persons are null!", persons);
		assertTrue("types.size = 0", persons.size() > 0);
//		for (Person p : persons) {
//			System.out.println(p.getFirstName());
//		}
	}

}
