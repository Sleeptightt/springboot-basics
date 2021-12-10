package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.TransactionSystemException;

import com.example.demo.back.daos.PersonDao;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Person;
import com.example.demo.back.service.BusinessEntityServiceImpl;

import lombok.extern.java.Log;

@Log
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class PersonDaoTest {

	private PersonDao personDao;
	
	private Person person;
	
	private BusinessEntityServiceImpl businessEntityService;
	
	private Businessentity ben;
	
	@Autowired
	public PersonDaoTest(PersonDao personDao, BusinessEntityServiceImpl businessEntityService) {
		this.personDao = personDao;
		this.businessEntityService = businessEntityService;
	}
	
	private void savePersonStage() {
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		ben = businessEntityService.saveBusinessEntity(ben);
	}
	
	private void updatePersonStage() {
		savePersonStage();
		person = new Person();
		
		person.setBusinessentity(ben);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("Doctor");
		person.setPersontype("Natural");
		person.setFirstname("Cesar");
		person.setMiddlename("Leonardo");
		person.setLastname("Canales");
		personDao.save(person);
	}
	
	@Test
	@DisplayName("Person creation")
	@Order(1)
	public void savePersonTest1() {
		savePersonStage();

		//VALID ATTRIBUTES TEST
		
		person = new Person();
		person.setBusinessentity(ben);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		
		person.setTitle("Doctor");
		person.setPersontype("Natural");
		person.setFirstname("Douglas");
		person.setMiddlename("Danny");
		person.setLastname("Marshall");
		person.setModifieddate(new Timestamp(time));
		personDao.save(person);
		List<Person> pers = personDao.getAll();
		
		assertTrue(pers.size() == 1);
		//log.info(""+pers.get(0).getBusinessentity().getBusinessentityid()+" "+ben.getBusinessentityid());
		assertTrue(personDao.findAllByPersontype("Natural").size() == 1);
		assertTrue(personDao.findAllByTitle("Doctor").size() == 1);
		assertTrue(personDao.findAllByBusinessentityid(ben.getBusinessentityid()).size() == 1);
		
		Person myPerson = pers.get(0);
		assertNotNull(myPerson, "The person was saved successfully");
		assertNotNull(myPerson.getModifieddate(), "The modified date is not null");
		
		assertNotNull(myPerson.getFirstname(), "The first name is not null");
		assertTrue(myPerson.getFirstname().length()>=3, "The first name has at least 3 characters");
		
		assertNotNull(myPerson.getMiddlename(), "The middle name is not null");
		assertTrue(myPerson.getMiddlename().length()>=3, "The middle name has at least 3 characters");
		
		assertNotNull(myPerson.getLastname(), "The last name is not null");
		assertTrue(myPerson.getLastname().length()>=3, "The last name has at least 3 characters");
		
		assertEquals(ben.getBusinessentityid(), ben.getBusinessentityid(), "The person is correctly associated with a business entity");
	
		//NOT VALID ATTRIBUTES TEST
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		ben = businessEntityService.saveBusinessEntity(ben);
		
		person = new Person();
		person.setBusinessentity(ben);
		
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		time = date.getTime();
		
		person.setTitle("Doctor");
		person.setPersontype("Natural");
		person.setFirstname("Douglas");
		person.setMiddlename(""); //illegal
		person.setLastname("Marshall");
		person.setModifieddate(new Timestamp(time));
		assertThrows(TransactionSystemException.class, () -> personDao.save(person), "The person has illegal attributes, commiting should not be successful");
	}
	
	@Test
	@DisplayName("Person update")
	@Order(2)
	public void updatePersonTest() {
		updatePersonStage();
		
		String oldmiddlename = person.getMiddlename();
		person.setMiddlename("Non empty middle name modified");
		personDao.update(person);
		List<Person> pers = personDao.getAll();
		
		assertTrue(pers.size() == 2);
		assertTrue(personDao.findAllByPersontype("Natural").size() == 2);
		assertTrue(personDao.findAllByTitle("Doctor").size() == 2);

		
		assertTrue(personDao.findAllByBusinessentityid(ben.getBusinessentityid()).size() == 1);
		
		Person myPerson = pers.get(1);
		
		assertNotNull(myPerson, "The person was updated successfully");
		assertNotNull(myPerson.getModifieddate(), "The modified date is not null");
		
		assertNotNull(myPerson.getFirstname(), "The first name is not null");
		assertTrue(myPerson.getFirstname().length()>=3, "The first name has at least 3 characters");
		
		assertNotNull(myPerson.getMiddlename(), "The middle name is not null");
		assertTrue(myPerson.getMiddlename().length()>=3, "The middle name has at least 3 characters");
		
		assertNotNull(myPerson.getLastname(), "The last name is not null");
		assertTrue(myPerson.getLastname().length()>=3, "The last name has at least 3 characters");
		
		assertFalse(myPerson.getMiddlename().equals(oldmiddlename), "The title was updated successfully");
		
		assertEquals(ben.getBusinessentityid(), myPerson.getBusinessentityid(), "The person is correctly associated with a business entity");
		
		person.setMiddlename("");
		assertThrows(TransactionSystemException.class, () -> personDao.update(person), "The modified person has illegal attributes, commiting should not be succesfull");
	}
	
	@Test
	@DisplayName("Query test")
	@Order(3)
	public void queryPersonTest() {
		insertStuffForTesting();
		
		List<Person> pers = personDao.getAll();
		
		assertTrue(pers.size() == 5);
		assertTrue(personDao.findAllByPersontype("Natural").size() == 3);
		assertTrue(personDao.findAllByTitle("Dr").size() == 2);
		assertTrue(personDao.findAllByBusinessentityid(ben.getBusinessentityid()).size() == 1);
		
	}
	
	@Test
	@DisplayName("Delete test")
	@Order(4)
	public void deletePersonTest() {
		insertStuffForTesting();
		
		assertTrue(personDao.findById(person.getBusinessentityid()).isPresent());
		personDao.deleteById(person.getBusinessentityid());
		assertFalse(personDao.findById(person.getBusinessentityid()).isPresent());
	}
	
	private void insertStuffForTesting() {
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		businessEntityService.saveBusinessEntity(ben);
		
		person = new Person();
		person.setBusinessentity(ben);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("Patient");
		person.setPersontype("Unnatural");
		person.setFirstname("Doug");
		person.setMiddlename("Danny");
		person.setLastname("Marshall");
		personDao.save(person);
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		businessEntityService.saveBusinessEntity(ben);
		
		person = new Person();
		person.setBusinessentity(ben);
		try { date = dateFormat.parse("18/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("Dr");
		person.setPersontype("Unnatural");
		person.setFirstname("Cesar");
		person.setMiddlename("Leonardo");
		person.setLastname("Canales");
		personDao.save(person);
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		businessEntityService.saveBusinessEntity(ben);
		
		person = new Person();
		person.setBusinessentity(ben);
		try { date = dateFormat.parse("19/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("Dr");
		person.setPersontype("Natural");
		person.setFirstname("Maria");
		person.setMiddlename("De los Angeles");
		person.setLastname("Guzman");
		personDao.save(person);
		
	}
	
}
