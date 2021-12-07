package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

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
import com.example.demo.service.AddressTypeServiceImpl;
import com.example.demo.service.BusinessEntityAddressServiceImpl;
import com.example.demo.service.BusinessEntityServiceImpl;
import com.example.demo.service.PersonPhoneServiceImpl;
import com.example.demo.service.PersonServiceImpl;
import com.example.demo.service.PhoneNumberTypeServiceImpl;
import com.example.demo.service.StateProvinceServiceImpl;

import lombok.extern.java.Log;

@Log
@SpringBootTest
class IntegrationTests {

	private BusinessEntityServiceImpl businessEntityService;
	private Businessentity businessEntityId;
	
	private PersonServiceImpl personService;
	private Person personId;

	private AddressServiceImpl addressService;
	private Address addressId;
	
	private StateProvinceServiceImpl stateProvinceService;
	private Stateprovince stateProvinceId;
	
	private BusinessEntityAddressServiceImpl businessEntityAddressService;
	private Businessentityaddress businessEntityAddressId;
	
	private AddressTypeServiceImpl addressTypeService;
	private Addresstype addressTypeId;
	
	private PersonPhoneServiceImpl personPhoneService;
	private Personphone personPhoneId;
	
	private PhoneNumberTypeServiceImpl phoneNumberTypeService;
	private Phonenumbertype phoneNumberTypeId;
	
	@Autowired
	public IntegrationTests(BusinessEntityServiceImpl businessEntityService, PersonServiceImpl personService,
			AddressServiceImpl addressService, StateProvinceServiceImpl stateProvinceService,
			BusinessEntityAddressServiceImpl businessEntityAddressService, AddressTypeServiceImpl addressTypeService,
			PersonPhoneServiceImpl personPhoneService, PhoneNumberTypeServiceImpl phoneNumberTypeService) {
		this.businessEntityService = businessEntityService;
		this.personService = personService;
		this.addressService = addressService;
		this.stateProvinceService = stateProvinceService;
		this.businessEntityAddressService = businessEntityAddressService;
		this.addressTypeService = addressTypeService;
		this.personPhoneService = personPhoneService;
		this.phoneNumberTypeService = phoneNumberTypeService;
	}

	//Person
	
	public void savePersonStage() {
		Businessentity businessEntity = new Businessentity();
		businessEntity.setModifieddate(Timestamp.from(Instant.now()));
		businessEntity.setRowguid(0);
		businessEntityId = businessEntityService.saveBusinessEntity(businessEntity);
	}

	public void editPersonStage() {
		savePersonStage();
		personId = new Person();
		personId.setBusinessentity(businessEntityId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		personId.setModifieddate(new Timestamp(time));
		personId.setTitle("");
		personId.setFirstname("Douglas");
		personId.setMiddlename("Danny");
		personId.setLastname("Marshall");
		personService.savePerson(personId);
	}
	
	@Test
	@DisplayName("Successful person creation")
	@Order(1)
	public void savePersonTest1() {
		savePersonStage();
		personId = new Person();
		personId.setBusinessentity(businessEntityId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		personId.setModifieddate(new Timestamp(time));
		personId.setTitle("");
		personId.setFirstname("Douglas");
		personId.setMiddlename("Danny");
		personId.setLastname("Marshall");
		
		Person myPerson = personService.savePerson(personId);
		assertNotNull(myPerson, "The person was saved successfully");
		assertNotNull(myPerson.getModifieddate(), "The modified date is not null");
		
		assertNotNull(myPerson.getFirstname(), "The first name is not null");
		assertTrue(myPerson.getFirstname().length()>=3, "The first name has at least 3 characters");
		
		assertNotNull(myPerson.getMiddlename(), "The middle name is not null");
		assertTrue(myPerson.getMiddlename().length()>=3, "The middle name has at least 3 characters");
		
		assertNotNull(myPerson.getLastname(), "The last name is not null");
		assertTrue(myPerson.getLastname().length()>=3, "The last name has at least 3 characters");
		
		assertEquals(businessEntityId, myPerson.getBusinessentity(), "The person is correctly associated with a business entity");
	}
	
	@Test
	@DisplayName("Unvalid person creation")
	@Order(2)
	public void savePersonTest2() {
		savePersonStage();
		personId = new Person();
		personId.setBusinessentity(businessEntityId);
		personId.setTitle("");
		personId.setFirstname("Douglas");
		personId.setMiddlename("Danny");
		personId.setLastname("Marshall");
		Person myPerson = personService.savePerson(personId);
		assertNull(myPerson, "The person was not saved because it has a null modification date");
	}
	
	@Test
	@DisplayName("Successful person edit")
	@Order(3)
	public void editPersonTest1() {
		editPersonStage();
		
		Person oldPerson = new Person();
		oldPerson.setTitle(personId.getTitle());
		oldPerson.setFirstname(personId.getFirstname());
		oldPerson.setMiddlename(personId.getMiddlename());
		oldPerson.setLastname(personId.getLastname());
		oldPerson.setBusinessentity(personId.getBusinessentity());
		oldPerson.setModifieddate(personId.getModifieddate());
		String oldTitle = "";
		
		personId.setTitle("Mi titulito");
		
		Person myPerson = personService.updatePerson(personId);
		assertNotNull(myPerson, "The person was updated successfully");
		assertNotNull(myPerson.getModifieddate(), "The modified date is not null");
		
		assertNotNull(myPerson.getFirstname(), "The first name is not null");
		assertTrue(myPerson.getFirstname().length()>=3, "The first name has at least 3 characters");
		
		assertNotNull(myPerson.getMiddlename(), "The middle name is not null");
		assertTrue(myPerson.getMiddlename().length()>=3, "The middle name has at least 3 characters");
		
		assertNotNull(myPerson.getLastname(), "The last name is not null");
		assertTrue(myPerson.getLastname().length()>=3, "The last name has at least 3 characters");
		
		assertFalse(myPerson.getTitle().equals(oldTitle), "The title was updated successfully");
		
		assertEquals(businessEntityId, myPerson.getBusinessentity(), "The person is correctly associated with a business entity");
	}
	
	@Test
	@DisplayName("Can't edit the person because of middle name")
	@Order(4)
	public void editPersonTest2() {
		editPersonStage();
		
		Person oldPerson = new Person();
		oldPerson.setTitle(personId.getTitle());
		oldPerson.setFirstname(personId.getFirstname());
		oldPerson.setMiddlename(personId.getMiddlename());
		oldPerson.setLastname(personId.getLastname());
		oldPerson.setBusinessentityid(personId.getBusinessentityid());
		oldPerson.setBusinessentity(personId.getBusinessentity());
		oldPerson.setModifieddate(personId.getModifieddate());
		
		personId.setMiddlename("D");
		
		Person myPerson = personService.updatePerson(personId);
		assertNull(myPerson, "The person was not updated because of the middle name");
	}
	
	//Address
	
	public void saveAddressStage() {
		Stateprovince stateProvince = new Stateprovince();
		stateProvince.setName("Alaska");
		stateProvince.setStateprovincecode("AK");
		stateProvince.setTerritoryid(0);
		stateProvince.setRowguid(0);
		stateProvince.setModifieddate(Timestamp.from(Instant.now()));
		stateProvince.setIsonlystateprovinceflag("flag");
		stateProvinceId = stateProvinceService.saveStateProvince(stateProvince);
	}
	
	public void editAddressStage() {
		saveAddressStage();
		addressId = new Address();
		addressId.setStateprovince(stateProvinceId);
		addressId.setAddressline1("Calle 16#25 A 50");
		addressId.setCity("Cali");
		addressId.setPostalcode("760011");
		addressService.saveAddress(addressId);
	}
	
	@Test
	@DisplayName("Successful address creation")
	@Order(5)
	public void saveAddressTest1() {
		saveAddressStage();
		
		addressId = new Address();
		addressId.setStateprovince(stateProvinceId);
		addressId.setAddressline1("Calle 16#25 A 50");
		addressId.setCity("Cali");
		addressId.setPostalcode("760011");
		
		Address myAddress = addressService.saveAddress(addressId);
		assertNotNull(myAddress, "The address was saved successfully");
		
		assertNotNull(myAddress.getAddressline1(), "The address line 1 is not null");
		assertFalse(myAddress.getAddressline1().isEmpty(), "The address line 1 is not empty");
		
		assertNotNull(myAddress.getCity(), "The city is not null");
		assertFalse(myAddress.getCity().isEmpty(), "The city is not empty");
		
		assertNotNull(myAddress.getPostalcode(), "The postal code is not null");
		assertFalse(myAddress.getPostalcode().isEmpty(), "The postal code is not empty");
		
		Integer stateid = stateProvinceId.getStateprovinceid();
		assertTrue(stateProvinceService.existsById(stateid), "The state province specified exists in the database");
	}
	
	@Test
	@DisplayName("Unvalid address creation")
	@Order(6)
	public void saveAddressTest2() {
		saveAddressStage();
		
		Stateprovince aux = stateProvinceId;
		aux.setStateprovinceid(19);
		addressId = new Address();
		addressId.setStateprovince(aux);
		addressId.setAddressline1("Calle 16#25 A 50");
		addressId.setCity("Cali");
		addressId.setPostalcode("760011");
		
		Address myAddress = addressService.saveAddress(addressId);
		assertNull(myAddress, "The address was not saved because the state province doesn't exist in the data base");
	}
	
	@Test
	@DisplayName("Successful address edit")
	@Order(7)
	public void editAddressTest1() {
		editAddressStage();
		
		Address oldAddress = new Address();
		oldAddress.setAddressid(addressId.getAddressid());
		oldAddress.setStateprovince(addressId.getStateprovince());
		oldAddress.setAddressline1(addressId.getAddressline1());
		oldAddress.setCity(addressId.getCity());
		oldAddress.setPostalcode(addressId.getPostalcode());
		String oldAddressLine1 = oldAddress.getAddressline1();
		
		addressId.setAddressline1("Cra 114 #18-66");
		
		Integer aid = addressId.getAddressid();
		Address myAddress = addressService.updateAddress(addressId);
		assertNotNull(myAddress, "The address was updated successfully");
		
		assertNotNull(myAddress.getAddressline1(), "The address line 1 is not null");
		assertFalse(myAddress.getAddressline1().isEmpty(), "The address line 1 is not empty");
		
		assertNotNull(myAddress.getCity(), "The city is not null");
		assertFalse(myAddress.getCity().isEmpty(), "The city is not empty");
		
		assertNotNull(myAddress.getPostalcode(), "The postal code is not null");
		assertFalse(myAddress.getPostalcode().isEmpty(), "The postal code is not empty");
		
		assertNotEquals(oldAddressLine1, myAddress.getAddressline1(), "The address line 1 was updated succesfully");
		
		Integer stateid = stateProvinceId.getStateprovinceid();
		assertTrue(stateProvinceService.existsById(stateid), "The state province specified exists in the database");
	}
	
	@Test
	@DisplayName("Can't edit the address because of empty city")
	@Order(8)
	public void editAddressTest2() {
		editAddressStage();
		
		Address oldAddress = new Address();
		oldAddress.setAddressid(addressId.getAddressid());
		oldAddress.setStateprovince(addressId.getStateprovince());
		oldAddress.setAddressline1(addressId.getAddressline1());
		oldAddress.setCity(addressId.getCity());
		oldAddress.setPostalcode(addressId.getPostalcode());
		
		addressId.setCity("");
		
		Address myAddress = addressService.updateAddress(addressId);
		assertNull(myAddress, "The address was not saved because the city was left empty");
	}
	
	//BusinessEntityAddress

	public void saveBusinessEntityAddressStage() {
		saveAddressStage();
		Address address = new Address();
		address.setStateprovince(stateProvinceId);
		address.setAddressline1("Calle 16#25 A 50");
		address.setCity("Cali");
		address.setPostalcode("760011");
		addressId = addressService.saveAddress(address);
		
		Businessentity businessEntity = new Businessentity();
		businessEntity.setModifieddate(Timestamp.from(Instant.now()));
		businessEntity.setRowguid(0);
		businessEntityId = businessEntityService.saveBusinessEntity(businessEntity);
		
		Addresstype addressType = new Addresstype();
		addressType.setName("idk");
		addressType.setModifieddate(Timestamp.from(Instant.now()));
		addressType.setRowguid(0);
		addressTypeId = addressTypeService.saveAddressType(addressType);
	}
	
	public void editBusinessEntityAddressStage() {
		saveBusinessEntityAddressStage();
		BusinessentityaddressPK key = new BusinessentityaddressPK();
		
		log.info(addressId+"");
		key.setAddressid(addressId.getAddressid());
		key.setAddresstypeid(addressTypeId.getAddresstypeid());
		key.setBusinessentityid(businessEntityId.getBusinessentityid());
		Businessentityaddress businessEntityAddress = new Businessentityaddress();
		businessEntityAddress.setId(key);
		businessEntityAddress.setBusinessentity(businessEntityId);
		businessEntityAddress.setAddresstype(addressTypeId);
		businessEntityAddress.setAddress(addressId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		businessEntityAddress.setModifieddate(new Timestamp(time));
		
		businessEntityAddressId = businessEntityAddressService.saveBusinessEntityAddress(businessEntityAddress);
	}
	
	@Test
	@DisplayName("Successful business entity address creation")
	@Order(9)
	public void saveBusinessEntityAddressTest1() {
		saveBusinessEntityAddressStage();
		
		BusinessentityaddressPK key = new BusinessentityaddressPK();
		key.setAddressid(addressId.getAddressid());
		key.setAddresstypeid(addressTypeId.getAddresstypeid());
		key.setBusinessentityid(businessEntityId.getBusinessentityid());
		Businessentityaddress businessEntityAddress = new Businessentityaddress();
		businessEntityAddress.setId(key);
		businessEntityAddress.setBusinessentity(businessEntityId);
		businessEntityAddress.setAddresstype(addressTypeId);
		businessEntityAddress.setAddress(addressId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		businessEntityAddress.setModifieddate(new Timestamp(time));
		
		Businessentityaddress myBEA = businessEntityAddressService.saveBusinessEntityAddress(businessEntityAddress);
		
		assertNotNull(myBEA, "The business entity address was saved correctly");
		
		Integer addressid = addressId.getAddressid();
		assertTrue(addressService.existsById(addressid), "The address specified exists in the database");
		
		Integer addressTypeid = addressTypeId.getAddresstypeid();
		assertTrue(addressTypeService.existsById(addressTypeid), "The address type specified exists in the database");
		
		Integer businessEntityid = businessEntityId.getBusinessentityid();
		assertTrue(businessEntityService.existsById(businessEntityid), "The business entity specified exists in the database");
	}
	
	@Test
	@DisplayName("Unvalid business entity address creation")
	@Order(10)
	public void saveBusinessEntityAddressTest2() {
		saveBusinessEntityAddressStage();
		Address address = addressId;
		address.setAddressid(19); //Se cambia el id del address para que sea no existente en la base de datos
		
		BusinessentityaddressPK key = new BusinessentityaddressPK();
		key.setAddressid(address.getAddressid());
		key.setAddresstypeid(addressTypeId.getAddresstypeid());
		key.setBusinessentityid(businessEntityId.getBusinessentityid());
		Businessentityaddress businessEntityAddress = new Businessentityaddress();
		businessEntityAddress.setId(key);
		businessEntityAddress.setBusinessentity(businessEntityId);
		businessEntityAddress.setAddresstype(addressTypeId);
		businessEntityAddress.setAddress(address);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		businessEntityAddress.setModifieddate(new Timestamp(time));
		
		Businessentityaddress myBEA = businessEntityAddressService.saveBusinessEntityAddress(businessEntityAddress);
		
		assertNull(myBEA, "The business entity address was not saved because of non existent address");
		
	}
	
	@Test
	@DisplayName("Successful business entity address edit")
	@Order(11)
	public void editBusinessEntityAddressTest1() {
		editBusinessEntityAddressStage();
		
		Businessentityaddress oldAddress = new Businessentityaddress();
		oldAddress.setAddress(businessEntityAddressId.getAddress());
		oldAddress.setAddresstype(businessEntityAddressId.getAddresstype());
		oldAddress.setBusinessentity(businessEntityAddressId.getBusinessentity());
		oldAddress.setId(businessEntityAddressId.getId());
		oldAddress.setModifieddate(businessEntityAddressId.getModifieddate());
		
		Address aux = new Address();
		aux.setAddressid(oldAddress.getAddress().getAddressid());
		aux.setAddressline1("Carrera 3 # 18‑ 45");
		aux.setCity("Bogota");
		aux.setPostalcode("110111");
		
		Businessentityaddress myAddress = businessEntityAddressService.updateBusinessEntityAddress(businessEntityAddressId);
		
		assertNotNull(myAddress, "The business entity address was updated successfully");
		
		Integer addressid = addressId.getAddressid();
		assertTrue(addressService.existsById(addressid), "The address specified exists in the database");
		
		Integer addressTypeid = addressTypeId.getAddresstypeid();
		assertTrue(addressTypeService.existsById(addressTypeid), "The address type specified exists in the database");
		
		Integer businessEntityid = businessEntityId.getBusinessentityid();
		assertTrue(businessEntityService.existsById(businessEntityid), "The business entity specified exists in the database");
	}
	
	//PersonPhone
	
	public void savePersonPhoneStage() {
		Phonenumbertype phoneNumberType = new Phonenumbertype();
		phoneNumberType.setPhonenumbertypeid(1);
		phoneNumberType.setName("Cellphone");
		phoneNumberType.setModifieddate(Timestamp.from(Instant.now()));
		phoneNumberTypeId = phoneNumberTypeService.savePhoneNumberType(phoneNumberType);
		
		Businessentity businessEntity = new Businessentity();
		businessEntity.setBusinessentityid(1);
		businessEntity.setModifieddate(Timestamp.from(Instant.now()));
		businessEntity.setRowguid(0);
		businessEntityId = businessEntityService.saveBusinessEntity(businessEntity);
		
		Person person = new Person();
		person.setBusinessentity(businessEntity);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		person.setModifieddate(new Timestamp(time));
		person.setTitle("");
		person.setFirstname("Douglas");
		person.setMiddlename("Danny");
		person.setLastname("Marshall");
		personId = personService.savePerson(person);
	}
	
	public void editPersonPhoneStage() {
		savePersonPhoneStage();
		
		PersonphonePK key = new PersonphonePK();
		key.setBusinessentityid(businessEntityId.getBusinessentityid());
		key.setPhonenumbertypeid(phoneNumberTypeId.getPhonenumbertypeid());
		key.setPhonenumber("3128854847");
		
		Personphone personPhone = new Personphone();
		personPhone.setId(key);
		personPhone.setPerson(personId);
		personPhone.setPhonenumbertype(phoneNumberTypeId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try { date = dateFormat.parse("17/10/2021"); } catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		personPhone.setModifieddate(new Timestamp(time));
		personPhoneId = personPhoneService.savePersonPhone(personPhone);
	}
	
	@Test
	@DisplayName("Successful person phone creation")
	@Order(12)
	public void savePersonPhoneTest1() {
		savePersonPhoneStage();
		
		PersonphonePK key = new PersonphonePK();
		key.setBusinessentityid(businessEntityId.getBusinessentityid());
		key.setPhonenumbertypeid(phoneNumberTypeId.getPhonenumbertypeid());
		key.setPhonenumber("3128854847");
		
		Personphone personPhone = new Personphone();
		personPhone.setId(key);
		personPhone.setPerson(personId);
		personPhone.setPhonenumbertype(phoneNumberTypeId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		personPhone.setModifieddate(new Timestamp(time));
		
		Personphone myphone = personPhoneService.savePersonPhone(personPhone);
		
		assertNotNull(myphone, "The phone number was saved correctly");
		
		assertTrue(myphone.getId().getPhonenumber().length() == 10, "The length of the phone number is exactly 10 digits, so it's correct");
		
		Integer personid = personId.getBusinessentityid();
		assertTrue(personService.existsById(personid), "The person specified exists in the database");
		
		Integer businessEntityid = businessEntityId.getBusinessentityid();
		assertTrue(businessEntityService.existsById(businessEntityid), "The specified business entity exists in the database");
		
		Integer phoneNumberTypeid = phoneNumberTypeId.getPhonenumbertypeid();
		assertTrue(phoneNumberTypeService.existsById(phoneNumberTypeid), "The phone number type specified, exists in the database");
	}
	
	@Test
	@DisplayName("Unvalid person phone creation")
	@Order(13)
	public void savePersonPhoneTest2() {
		savePersonPhoneStage();
		
		PersonphonePK key = new PersonphonePK();
		key.setBusinessentityid(businessEntityId.getBusinessentityid());
		key.setPhonenumbertypeid(phoneNumberTypeId.getPhonenumbertypeid());
		key.setPhonenumber("3372206");
		
		Personphone personPhone = new Personphone();
		personPhone.setId(key);
		personPhone.setPerson(personId);
		personPhone.setPhonenumbertype(phoneNumberTypeId);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		personPhone.setModifieddate(new Timestamp(time));
		
		Personphone myphone = personPhoneService.savePersonPhone(personPhone);
		
		assertNull(myphone, "The phone number was not saved");
		
		assertTrue(personPhone.getId().getPhonenumber().length() != 10, "The length of the phone number is less than 10 digits, so it's incorrect");
	}
	
	@Test
	@DisplayName("Successful person phone edit")
	@Order(14)
	public void editPersonPhoneTest1() {
		editPersonPhoneStage();
		
		String oldPhoneNumber = personPhoneId.getId().getPhonenumber();
		
		PersonphonePK aux = new PersonphonePK();
		aux.setBusinessentityid(personPhoneId.getId().getBusinessentityid());
		aux.setPhonenumber("3168379049");
		aux.setPhonenumbertypeid(personPhoneId.getId().getPhonenumbertypeid());
		personPhoneId.setId(aux);
		
		Personphone myphone = personPhoneService.updatePersonPhone(personPhoneId);
		
		assertNotNull(myphone, "The phone number was updated successfully");
		
		assertTrue(myphone.getId().getPhonenumber().length() == 10, "The length of the phone number is exactly 10 digits, so it's correct");
		
		Integer personid = personId.getBusinessentityid();
		assertTrue(personService.existsById(personid), "The person specified exists in the database");
		
		Integer businessEntityid = businessEntityId.getBusinessentityid();
		assertTrue(businessEntityService.existsById(businessEntityid), "The specified business entity exists in the database");
		
		Integer phoneNumberTypeid = phoneNumberTypeId.getPhonenumbertypeid();
		assertTrue(phoneNumberTypeService.existsById(phoneNumberTypeid), "The phone number type specified, exists in the database");
		
		assertNotEquals(myphone.getId().getPhonenumber(), oldPhoneNumber, "The phone number attribute was updated correctly");
	}
}
