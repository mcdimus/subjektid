package junit.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import frontend.forms.AddressForm;
import frontend.forms.SearchForm;
import frontend.forms.SearchResult;

import backend.DA.SubjectsORM;
import backend.model.Address;
import backend.model.AddressType;
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

//		Employee employee = new Employee();
//		employee.setEmployee(3);
//		employee.setPersonFk((long) 3);
//		employee.setEnterpriseFk((long) 2);
//		employee.setActive("Y");
//		orm.saveOrUpdate(employee);
	}
	
	@Test
	public void testFormSearchQuery() {
		SearchForm form = new SearchForm();
		form.setSubjectType(0);
		form.setFirstName("");
		form.setLastName("");
		AddressForm aform = new AddressForm();
		aform.setCountry("");
		aform.setCounty("");
		aform.setStreetAddress("");
		aform.setTownVillage("");
		aform.setZipcode("");
		form.setAddressForm(aform);
		String answer = orm.formSearchQuery(form);
		System.out.println(answer);
	}
	
	@Test
	public void testSearch() {
		SearchForm form = new SearchForm();
		form.setSubjectType(0);
		form.setFirstName("");
		form.setLastName("");
		AddressForm aform = new AddressForm();
		aform.setCountry("");
		aform.setCounty("");
		aform.setStreetAddress("");
		aform.setTownVillage("");
		aform.setZipcode("");
		form.setAddressForm(aform);
		ArrayList<SearchResult> results = orm.search(form);
		for (SearchResult res : results) {
			System.out.println(res.getSubjectName());
		}
	}

}
