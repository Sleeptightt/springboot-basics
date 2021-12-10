package com.example.demo.back.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.back.daos.AddressDao;
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.back.repository.StateProvinceRepository;
import com.example.demo.back.service.interfaces.AddressService;

import lombok.extern.java.Log;

@Log
@Service
public class AddressServiceImpl implements AddressService{
	
	private AddressDao addressDao;
	private StateProvinceRepository stateProvinceRepository;
	
	@Autowired
	public AddressServiceImpl(AddressDao addressDao, StateProvinceRepository stateProvinceRepository) {
		this.addressDao = addressDao;
		this.stateProvinceRepository = stateProvinceRepository;
	}
	
	public Address saveAddress(Address address) {
		if(address == null || address.getStateprovince() == null || !stateProvinceRepository.existsById(address.getStateprovince().getStateprovinceid())) {
			
			return null;
		}
		boolean cond = address.getAddressline1() != null && !address.getAddressline1().isEmpty() && address.getCity() != null 
				&& !address.getCity().isEmpty() && address.getPostalcode()!=null && !address.getPostalcode().isEmpty();
		
		if(!cond) return null;
		addressDao.save(address);
		return address;
	}
	
	@Transactional
	public Address updateAddress(Address address) {
		if(address == null || !addressDao.findById(address.getAddressid()).isPresent()) {
			return null;
		}
		Integer aid = address.getAddressid();
		Address existingAddress = addressDao.findById(aid).get();
		boolean cond = address.getAddressline1() != null && !address.getCity().isEmpty() && address.getPostalcode()!=null;
		if(!cond) return null;
		existingAddress.setAddressline1(address.getAddressline1());
		existingAddress.setAddressline2(address.getAddressline2());
		existingAddress.setCity(address.getCity());
		existingAddress.setPostalcode(address.getPostalcode());;
		existingAddress.setStateprovince(address.getStateprovince());
		return address;
	}

	public boolean existsById(Integer addressid) {
		return addressDao.findById(addressid).isPresent();
	}

	@Override
	public Iterable<Address> findAll() {
		return addressDao.getAll();
	}

	@Override
	public Optional<Address> findById(long id) {
		return addressDao.findById((int) id);
	}

	@Override
	public void delete(Address addr) {
		addressDao.deleteById(addr.getAddressid());
	}

	@Override
	public Iterable<Address> findAllByStateprovince(Stateprovince stateProvince) {
		return addressDao.findAllByStateprovince(stateProvince);
	}

}
