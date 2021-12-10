package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Stateprovince;

public interface AddressService {
	
	public Iterable<Address> findAll();
	
	public Iterable<Address> findAllByStateprovince(Stateprovince stateProvince);
	
	public Address saveAddress(Address addr);

	public Optional<Address> findById(long id);
	
	public void delete(Address addr);

	public Address updateAddress(Address addr);
	
	
	
}
