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

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.TransactionSystemException;

import com.example.demo.daos.AddressDao;
import com.example.demo.model.person.Address;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Person;
import com.example.demo.model.person.Stateprovince;
import com.example.demo.repository.BusinessEntityRepository;
import com.example.demo.repository.StateProvinceRepository;
import com.example.demo.service.AddressServiceImpl;
import com.example.demo.service.StateProvinceServiceImpl;

import lombok.extern.java.Log;

@Log
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class AddressDaoTest {
	
	private AddressDao addressDao;
	
	private Address addr;
	
	private StateProvinceServiceImpl stateProvinceService;
	
	private Stateprovince stprov;
	
	@Autowired
	public AddressDaoTest(AddressDao addressDao, StateProvinceServiceImpl stateProvinceService) {
		this.addressDao = addressDao;
		this.stateProvinceService = stateProvinceService;
	}
	
	public void saveAddressStage() {
		
		stprov = new Stateprovince();
		stprov.setName("Alaska");
		stprov.setStateprovincecode("AK");
		stprov.setTerritoryid(0);
		stprov.setRowguid(0);
		stprov.setModifieddate(Timestamp.from(Instant.now()));
		stprov.setIsonlystateprovinceflag("flag");
		stateProvinceService.saveStateProvince(stprov);
	}
	
	public void updateAddressStage() {
		saveAddressStage();
		addr = new Address();
		addr.setStateprovince(stprov);
		addr.setAddressline1("Calle 16#25 A 50");
		addr.setCity("Cali");
		addr.setPostalcode("760011");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		addr.setModifieddate(new Timestamp(time));
		addressDao.save(addr);
	}
	
	@Test
	@DisplayName("Successful address creation")
	@Order(1)
	public void saveAddressTest1() {
		saveAddressStage();
		
		//VALID ATTRIBUTES TEST
		
		addr = new Address();
		
		addr.setStateprovince(stprov);
		addr.setAddressline1("Calle 16#25 A 50");
		addr.setCity("Cali");
		addr.setPostalcode("760011");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		addr.setModifieddate(new Timestamp(time));
		
		addressDao.save(addr);
		List<Address> addrs = addressDao.getAll();
		
		assertTrue(addrs.size() == 2);
		assertTrue(addressDao.findAllByAddressid(addr.getAddressid()).size() == 1);
		assertTrue(addressDao.findAllByModifieddate(new Timestamp(time)).size() == 1);
		
		Address myAddress = addrs.get(1);
		
		assertNotNull(myAddress, "The address was saved successfully");
		
		assertNotNull(myAddress.getAddressline1(), "The address line 1 is not null");
		assertFalse(myAddress.getAddressline1().isEmpty(), "The address line 1 is not empty");
		
		assertNotNull(myAddress.getCity(), "The city is not null");
		assertFalse(myAddress.getCity().isEmpty(), "The city is not empty");
		
		assertNotNull(myAddress.getPostalcode(), "The postal code is not null");
		assertFalse(myAddress.getPostalcode().isEmpty(), "The postal code is not empty");
		
		//NOT VALID ATTRIBUTES TEST
		
		stprov = new Stateprovince();
		stprov.setName("Alaska");
		stprov.setStateprovincecode("AK");
		stprov.setTerritoryid(0);
		stprov.setRowguid(0);
		stprov.setModifieddate(Timestamp.from(Instant.now()));
		stprov.setIsonlystateprovinceflag("flag");
		stateProvinceService.saveStateProvince(stprov);
		
		addr = new Address();
		
		addr.setStateprovince(stprov);
		addr.setCity("Cali");
		addr.setAddressline1("");
		addr.setPostalcode("760011");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		time = date.getTime();
		addr.setModifieddate(new Timestamp(time));
		assertThrows(TransactionSystemException.class, () -> addressDao.save(addr), "The address has illegal attributes, commiting should not be successful");
	}
	
	@Test
	@DisplayName("Successful address edit")
	@Order(2)
	public void updateAddressTest1() {
		updateAddressStage();
		
		addr.setAddressline1("Cra 114 #18-66");
		
		addressDao.update(addr);
		List<Address> addrs = addressDao.getAll();
		
		assertTrue(addrs.size() == 3);
		assertTrue(addressDao.findAllByAddressid(addr.getAddressid()).size() == 1);
		
		Address myAddress = addrs.get(2);
		
		assertNotNull(myAddress, "The address was saved successfully");
		
		assertNotNull(myAddress.getAddressline1(), "The address line 1 is not null");
		assertFalse(myAddress.getAddressline1().isEmpty(), "The address line 1 is not empty");
		
		assertNotNull(myAddress.getCity(), "The city is not null");
		assertFalse(myAddress.getCity().isEmpty(), "The city is not empty");
		
		assertNotNull(myAddress.getPostalcode(), "The postal code is not null");
		assertFalse(myAddress.getPostalcode().isEmpty(), "The postal code is not empty");
		
		addr.setAddressline1("");
		assertThrows(TransactionSystemException.class, () -> addressDao.update(addr), "The modified address has illegal attributes, commiting should not be succesfull");
	}
	
	@Test
	@DisplayName("Query test")
	@Order(3)
	public void queryPersonTest() {
		insertStuffForTesting();
		
		List<Address> addrs = addressDao.getAll();
		
		assertTrue(addrs.size() == 6);
		assertTrue(addressDao.findAllByAddressid(addr.getAddressid()).size() == 1);
		
	}
	
	@Test
	@DisplayName("Delete test")
	@Order(4)
	public void deletePersonTest() {
		insertStuffForTesting();
		
		assertTrue(addressDao.findById(addr.getAddressid()).isPresent());
		addressDao.deleteById(addr.getAddressid());
		assertFalse(addressDao.findById(addr.getAddressid()).isPresent());
	}
	
	private void insertStuffForTesting() {
		stprov = new Stateprovince();
		stprov.setName("Alaska");
		stprov.setStateprovincecode("AK");
		stprov.setTerritoryid(0);
		stprov.setRowguid(0);
		stprov.setModifieddate(Timestamp.from(Instant.now()));
		stprov.setIsonlystateprovinceflag("flag");
		stateProvinceService.saveStateProvince(stprov);
		
		addr = new Address();
		
		addr.setStateprovince(stprov);
		addr.setCity("Bogota");
		addr.setAddressline1("Calle 16# 14B50");
		addr.setPostalcode("328303");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		addr.setModifieddate(new Timestamp(time));
		addressDao.save(addr);
		
		stprov = new Stateprovince();
		stprov.setName("Alaska");
		stprov.setStateprovincecode("AK");
		stprov.setTerritoryid(0);
		stprov.setRowguid(0);
		stprov.setModifieddate(Timestamp.from(Instant.now()));
		stprov.setIsonlystateprovinceflag("flag");
		stateProvinceService.saveStateProvince(stprov);
		
		addr = new Address();
		
		addr.setStateprovince(stprov);
		addr.setCity("Medellin");
		addr.setAddressline1("Calle 70 51d42");
		addr.setPostalcode("760011");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		time = date.getTime();
		addr.setModifieddate(new Timestamp(time));
		addressDao.save(addr);
		
		stprov = new Stateprovince();
		stprov.setName("Alaska");
		stprov.setStateprovincecode("AK");
		stprov.setTerritoryid(0);
		stprov.setRowguid(0);
		stprov.setModifieddate(Timestamp.from(Instant.now()));
		stprov.setIsonlystateprovinceflag("flag");
		stateProvinceService.saveStateProvince(stprov);
		
		addr = new Address();
		
		addr.setStateprovince(stprov);
		addr.setCity("Medellin");
		addr.setAddressline1("Carrera 38 # 26 - 13");
		addr.setPostalcode("760011");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date = null;
		try {date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		time = date.getTime();
		addr.setModifieddate(new Timestamp(time));
		addressDao.save(addr);
		
	}
	
}

