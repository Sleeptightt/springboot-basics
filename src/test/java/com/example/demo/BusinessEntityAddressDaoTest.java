package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.TransactionSystemException;

import com.example.demo.back.daos.AddressDao;
import com.example.demo.back.daos.BusinessEntityAddressDao;
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;
import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.back.service.AddressTypeServiceImpl;
import com.example.demo.back.service.BusinessEntityServiceImpl;
import com.example.demo.back.service.StateProvinceServiceImpl;

import lombok.extern.java.Log;

@Log
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class BusinessEntityAddressDaoTest {
	
	private BusinessEntityAddressDao benaddrDao;
	
	private Businessentityaddress benaddr;
	
	private AddressDao addrDao;
	
	private Address addr;
	
	private AddressTypeServiceImpl addressTypeService;
	
	private Addresstype addrtype;
	
	private BusinessEntityServiceImpl businessEntityService;

	private Businessentity ben;
	
	private StateProvinceServiceImpl stateProvinceService;
	
	private Stateprovince stprov;
	
	@Autowired
	public BusinessEntityAddressDaoTest(BusinessEntityAddressDao benaddrDao,
			AddressDao addrDao, AddressTypeServiceImpl addressTypeService,
			BusinessEntityServiceImpl businessEntityService, StateProvinceServiceImpl stateProvinceService) {
		this.benaddrDao = benaddrDao;
		this.addrDao = addrDao;
		this.addressTypeService = addressTypeService;
		this.businessEntityService = businessEntityService;
		this.stateProvinceService = stateProvinceService;
	}


	public void saveBusinessEntityAddressStage() {
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
		addr.setAddressline1("Calle 16#25 A 50");
		addr.setCity("Cali");
		addr.setPostalcode("760011");
		addrDao.save(addr);
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		ben = businessEntityService.saveBusinessEntity(ben);
		
		addrtype = new Addresstype();
		addrtype.setName("idk");
		addrtype.setModifieddate(Timestamp.from(Instant.now()));
		addrtype.setRowguid(0);
		addrtype = addressTypeService.saveAddressType(addrtype);
	}
	
	public void updateBusinessEntityAddressStage() {
		saveBusinessEntityAddressStage();
		
		BusinessentityaddressPK key = new BusinessentityaddressPK();
		key.setAddressid(addr.getAddressid());
		key.setAddresstypeid(addrtype.getAddresstypeid());
		key.setBusinessentityid(ben.getBusinessentityid());
		
		benaddr = new Businessentityaddress();
		benaddr.setId(key);
		benaddr.setBusinessentity(ben);
		benaddr.setAddresstype(addrtype);
		benaddr.setAddress(addr);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse("17/10/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		benaddr.setModifieddate(new Timestamp(time));
		
		benaddrDao.save(benaddr);
	}
	
	@Test
	@DisplayName("Successful business entity address creation")
	@Order(1)
	public void saveBusinessEntityAddressTest1() {
		saveBusinessEntityAddressStage();
		
		BusinessentityaddressPK key = new BusinessentityaddressPK();
		key.setAddressid(addr.getAddressid());
		key.setAddresstypeid(addrtype.getAddresstypeid());
		key.setBusinessentityid(ben.getBusinessentityid());
		benaddr = new Businessentityaddress();
		benaddr.setId(key);
		benaddr.setBusinessentity(ben);
		benaddr.setAddresstype(addrtype);
		benaddr.setAddress(addr);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse("17/10/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		benaddr.setModifieddate(new Timestamp(time));
		
		benaddrDao.save(benaddr);
		List<Businessentityaddress> benaddrs = benaddrDao.getAll();
		
		assertTrue(benaddrs.size() == 1);
		
		Businessentityaddress myBEA = benaddrs.get(0);
		
		assertNotNull(myBEA, "The business entity address was saved correctly");
		
	}
	
	@Test
	@DisplayName("Successful address edit")
	@Order(2)
	public void updateBusinessEntityAddressTest1() {
		updateBusinessEntityAddressStage();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("18/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		benaddr.setModifieddate(new Timestamp(time));
		benaddrDao.update(benaddr);
		List<Businessentityaddress> benaddrs = benaddrDao.getAll();
		
		assertTrue(benaddrs.size() == 2);
		
		Businessentityaddress myAddress = benaddrs.get(1);
		assertNotNull(myAddress, "The business entity address was updated successfully");
	
		benaddr.setModifieddate(null); //Illegal
		assertThrows(TransactionSystemException.class, () -> benaddrDao.update(benaddr), "The modified person phone has illegal attributes, commiting should not be succesfull");
	}
	
	@Test
	@DisplayName("Query test")
	@Order(3)
	public void queryPersonTest() {
		insertStuffForTesting();
		
		List<Businessentityaddress> benaddrs = benaddrDao.getAll();
		
		assertTrue(benaddrs.size() == 3);
		
	}
	
	@Test
	@DisplayName("Delete test")
	@Order(4)
	public void deletePersonTest() {
		insertStuffForTesting();
		
		assertTrue(benaddrDao.findById(benaddr.getId()).isPresent());
		benaddrDao.deleteById(benaddr.getId());
		assertFalse(benaddrDao.findById(benaddr.getId()).isPresent());
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
		addr.setAddressline1("Calle 16#25 A 50");
		addr.setCity("Cali");
		addr.setPostalcode("760011");
		addrDao.save(addr);
		
		ben = new Businessentity();
		ben.setModifieddate(Timestamp.from(Instant.now()));
		ben.setRowguid(0);
		ben = businessEntityService.saveBusinessEntity(ben);
		
		addrtype = new Addresstype();
		addrtype.setName("idk");
		addrtype.setModifieddate(Timestamp.from(Instant.now()));
		addrtype.setRowguid(0);
		addrtype = addressTypeService.saveAddressType(addrtype);
		
		BusinessentityaddressPK key = new BusinessentityaddressPK();
		key.setAddressid(addr.getAddressid());
		key.setAddresstypeid(addrtype.getAddresstypeid());
		key.setBusinessentityid(ben.getBusinessentityid());
		
		benaddr = new Businessentityaddress();
		benaddr.setId(key);
		benaddr.setBusinessentity(ben);
		benaddr.setAddresstype(addrtype);
		benaddr.setAddress(addr);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {	date = dateFormat.parse("17/10/2021");} catch (ParseException e) {	e.printStackTrace();}
		long time = date.getTime();
		benaddr.setModifieddate(new Timestamp(time));
		
		benaddrDao.save(benaddr);
		
	}
	
}
