package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Addresstype;

public interface AddressTypeService {

	public Iterable<Addresstype> findAll();
	
	public Addresstype saveAddressType(Addresstype addrtype);

	public Optional<Addresstype> findById(long id);
	
	public void delete(Integer id);

	public Addresstype updateAddressType(Addresstype addrtype);
	
}
