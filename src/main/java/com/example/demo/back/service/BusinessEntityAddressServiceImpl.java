package com.example.demo.back.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.back.daos.AddressDao;
import com.example.demo.back.daos.BusinessEntityAddressDao;
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;
import com.example.demo.back.repository.AddressTypeRepository;
import com.example.demo.back.repository.BusinessEntityRepository;
import com.example.demo.back.service.interfaces.BusinessEntityAddressService;

import lombok.extern.java.Log;

@Log
@Service
public class BusinessEntityAddressServiceImpl implements BusinessEntityAddressService {

	private BusinessEntityAddressDao businessEntityAddressDao;
	
	private AddressDao addressDao;
	
	private AddressTypeRepository addressTypeRepository;
	
	private BusinessEntityRepository businessEntityRepository;
	
	@Autowired
	public BusinessEntityAddressServiceImpl(BusinessEntityAddressDao businessEntityAddressDao,
			AddressDao addressDao, AddressTypeRepository addressTypeRepository,
			BusinessEntityRepository businessEntityRepository) {
		this.businessEntityAddressDao = businessEntityAddressDao;
		this.addressDao = addressDao;
		this.addressTypeRepository = addressTypeRepository;
		this.businessEntityRepository = businessEntityRepository;
	}
	
	public Businessentityaddress saveBusinessEntityAddress(Businessentityaddress businessEntityAddress) {
		if(businessEntityAddress == null || businessEntityAddress.getAddress() == null || businessEntityAddress.getAddress().getAddressid() == null
				|| businessEntityAddress.getAddresstype() ==null || businessEntityAddress.getAddresstype().getAddresstypeid() ==null
				|| businessEntityAddress.getBusinessentity() == null || businessEntityAddress.getBusinessentity().getBusinessentityid() == null) {
			return null;
		}
		Integer addressid = businessEntityAddress.getAddress().getAddressid();
		Integer addressTypeid = businessEntityAddress.getAddresstype().getAddresstypeid();
		Integer businessEntityid = businessEntityAddress.getBusinessentity().getBusinessentityid();
		if(!addressDao.findById(addressid).isPresent() || !addressTypeRepository.existsById(addressTypeid) || !businessEntityRepository.existsById(businessEntityid)) 
			return null;
		businessEntityAddressDao.save(businessEntityAddress);
		return businessEntityAddress;
	}
	
	@Transactional
	public Businessentityaddress updateBusinessEntityAddress(Businessentityaddress businessEntityAddress) {
		if(businessEntityAddress == null || !businessEntityAddressDao.findById(businessEntityAddress.getId()).isPresent()|| businessEntityAddress.getAddress() == null || businessEntityAddress.getAddress().getAddressid() == null
				|| businessEntityAddress.getAddresstype() ==null || businessEntityAddress.getAddresstype().getAddresstypeid() ==null
				|| businessEntityAddress.getBusinessentity() == null || businessEntityAddress.getBusinessentity().getBusinessentityid() == null) {
			return null;
		}
		Integer addressid = businessEntityAddress.getAddress().getAddressid();
		Integer addressTypeid = businessEntityAddress.getAddresstype().getAddresstypeid();
		Integer businessEntityid = businessEntityAddress.getBusinessentity().getBusinessentityid();
		if(!addressDao.findById(addressid).isPresent() || !addressTypeRepository.existsById(addressTypeid) || !businessEntityRepository.existsById(businessEntityid)) 
			return null;
		BusinessentityaddressPK id = businessEntityAddress.getId();
		Businessentityaddress existingAddress = businessEntityAddressDao.findById(id).get();
		existingAddress.setAddress(businessEntityAddress.getAddress());
		existingAddress.setAddresstype(businessEntityAddress.getAddresstype());
		existingAddress.setBusinessentity(businessEntityAddress.getBusinessentity());
		return businessEntityAddress;
	}

	@Override
	public Iterable<Businessentityaddress> findAll() {
		return businessEntityAddressDao.getAll();
	}

	@Override
	public Optional<Businessentityaddress> findById(BusinessentityaddressPK id) {
		return businessEntityAddressDao.findById(id);
	}

	@Override
	public void delete(BusinessentityaddressPK id) {
		businessEntityAddressDao.deleteById(id);
	}

	@Override
	public Iterable<Businessentityaddress> findAllByAddress(Integer addressid) {
		return businessEntityAddressDao.findAllByAddress(addressid);
	}

	@Override
	public Iterable<Businessentityaddress> findAllByAddresstype(Integer addresstypeid) {
		return businessEntityAddressDao.findAllByAddresstype(addresstypeid);
	}

	@Override
	public Iterable<Businessentityaddress> findAllByBusinessentity(Integer businessentityid) {
		return businessEntityAddressDao.findAllByBusinessentity(businessentityid);
	}

	

	

}
