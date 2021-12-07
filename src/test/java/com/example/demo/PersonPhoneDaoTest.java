package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.TransactionSystemException;

import com.example.demo.daos.PersonDao;
import com.example.demo.daos.PersonPhoneDao;
import com.example.demo.model.person.Address;
import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Businessentityaddress;
import com.example.demo.model.person.BusinessentityaddressPK;
import com.example.demo.model.person.Person;
import com.example.demo.model.person.Personphone;
import com.example.demo.model.person.PersonphonePK;
import com.example.demo.model.person.Phonenumbertype;
import com.example.demo.model.person.Stateprovince;
import com.example.demo.repository.AddressTypeRepository;
import com.example.demo.repository.BusinessEntityRepository;
import com.example.demo.repository.PhoneNumberTypeRepository;
import com.example.demo.repository.StateProvinceRepository;
import com.example.demo.service.AddressServiceImpl;
import com.example.demo.service.BusinessEntityAddressServiceImpl;
import com.example.demo.service.BusinessEntityServiceImpl;
import com.example.demo.service.PersonPhoneServiceImpl;
import com.example.demo.service.PersonServiceImpl;
import com.example.demo.service.PhoneNumberTypeServiceImpl;

import lombok.extern.java.Log;

@Log
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class PersonPhoneDaoTest {
	
	private PersonPhoneDao personPhoneDao;
	
	private Personphone personPhone;
	
	private PersonDao personDao;
	
	private Person person;

	private PhoneNumberTypeServiceImpl phoneNumberTypeService;
	
	private Phonenumbertype phonetype;
	
	private BusinessEntityServiceImpl businessEntityService;
	
	private Businessentity ben;
	
	@Autowired
	public PersonPhoneDaoTest(PersonPhoneDao personPhoneDao, PersonDao personDao,
			PhoneNumberTypeServiceImpl phoneNumberTypeService, BusinessEntityServiceImpl businessEntityService) {
		this.personPhoneDao = personPhoneDao;
		this.personDao = personDao;
		this.phoneNumberTypeService = phoneNumberTypeService;
		this.businessEntityService = businessEntityService;
	}

	public void savePersonPhoneStage() {
		phonetype = new Phonenumbertype();
		phonetype.setName("Cellphone");
		phonetype.setModifieddate(Timestamp.from(Instant.now()));
		phonetype = phoneNumberTypeService.savePhoneNumberType(phonetype);
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		ben = businessEntityService.saveBusinessEntity(ben);
		
		person = new Person();
		person.setBusinessentity(ben);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("");
		person.setFirstname("Douglas");
		person.setMiddlename("Danny");
		person.setLastname("Marshall");
		personDao.save(person);
	}
	
	public void updatePersonPhoneStage() {
		savePersonPhoneStage();
		
		PersonphonePK key = new PersonphonePK();
		key.setBusinessentityid(ben.getBusinessentityid());
		key.setPhonenumbertypeid(phonetype.getPhonenumbertypeid());
		key.setPhonenumber("3128854849");
		
		personPhone = new Personphone();
		personPhone.setPerson(person);
		personPhone.setPhonenumbertype(phonetype);
		personPhone.setId(key);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		personPhone.setModifieddate(new Timestamp(time));
		
		personPhoneDao.save(personPhone);
	}
	
	@Test
	@DisplayName("Successful person phone creation")
	@Order(1)
	public void savePersonPhoneTest1() {
		savePersonPhoneStage();
		
		PersonphonePK key = new PersonphonePK();
		key.setBusinessentityid(ben.getBusinessentityid());
		key.setPhonenumbertypeid(phonetype.getPhonenumbertypeid());
		key.setPhonenumber("3128854847");
		
		personPhone = new Personphone();
		personPhone.setId(key);
		personPhone.setPerson(person);
		personPhone.setPhonenumbertype(phonetype);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse("17/10/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		personPhone.setModifieddate(new Timestamp(time));
		
		personPhoneDao.save(personPhone);
		List<Personphone> phones = personPhoneDao.getAll();
		
		assertTrue(phones.size() == 1);
		assertTrue(personPhoneDao.findAllByPhonenumbertype(phonetype).size() == 1);
		//assertTrue(personPhoneDao.findAllByPhonenumberLike("312").size() == 0);
		
		Personphone myphone = phones.get(0);
		
		assertNotNull(myphone, "The phone number was saved correctly");
		
		assertTrue(myphone.getId().getPhonenumber().length() == 10, "The length of the phone number is exactly 10 digits, so it's correct");
	}
	
	@Test
	@DisplayName("Successful person phone edit")
	@Order(2)
	public void updatePersonPhoneTest1() {
		updatePersonPhoneStage();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse("18/10/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		personPhone.setModifieddate(new Timestamp(time));
		personPhoneDao.update(personPhone);
		List<Personphone> phones = personPhoneDao.getAll();
		
		assertTrue(phones.size() == 2);
		assertTrue(personPhoneDao.findAllByPhonenumbertype(phonetype).size() == 1);
		//assertTrue(personPhoneDao.findAllByPhonenumberLike("312").size() == 2);
		
		Personphone myphone = phones.get(1);
		
		assertNotNull(myphone, "The phone number was updated successfully");
		
		assertTrue(myphone.getId().getPhonenumber().length() == 10, "The length of the phone number is exactly 10 digits, so it's correct");
	
		personPhone.setModifieddate(null); //Illegal
		assertThrows(TransactionSystemException.class, () -> personPhoneDao.update(personPhone), "The modified person phone has illegal attributes, commiting should not be succesfull");
	}
	
	@Test
	@DisplayName("Query test")
	@Order(3)
	public void queryPersonTest() {
		insertStuffForTesting();
		
		List<Personphone> phones = personPhoneDao.getAll();
		
		assertTrue(phones.size() == 3);
		assertTrue(personPhoneDao.findAllByPhonenumbertype(phonetype).size() == 1);
		//assertTrue(personPhoneDao.findAllByPhonenumberLike("312").size() == 4);
		
	}
	
	@Test
	@DisplayName("Delete test")
	@Order(4)
	public void deletePersonTest() {
		insertStuffForTesting();
		
		assertTrue(personPhoneDao.findById(personPhone.getId()).isPresent());
		personPhoneDao.deleteById(personPhone.getId());
		assertFalse(personPhoneDao.findById(personPhone.getId()).isPresent());
	}
	
	private void insertStuffForTesting() {
		phonetype = new Phonenumbertype();
		phonetype.setName("Cellphone");
		phonetype.setModifieddate(Timestamp.from(Instant.now()));
		phonetype = phoneNumberTypeService.savePhoneNumberType(phonetype);
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		ben = businessEntityService.saveBusinessEntity(ben);
		
		person = new Person();
		person.setBusinessentity(ben);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("");
		person.setFirstname("Douglas");
		person.setMiddlename("Danny");
		person.setLastname("Marshall");
		personDao.save(person);
		
		PersonphonePK key = new PersonphonePK();
		key.setBusinessentityid(ben.getBusinessentityid());
		key.setPhonenumbertypeid(phonetype.getPhonenumbertypeid());
		key.setPhonenumber("312493012");
		
		personPhone = new Personphone();
		personPhone.setId(key);
		personPhone.setPerson(person);
		personPhone.setPhonenumbertype(phonetype);
		personPhone.setModifieddate(new Timestamp(time));
		
		personPhoneDao.save(personPhone);
		
	}
	
}
