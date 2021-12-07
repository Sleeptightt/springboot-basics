package com.example.demo.service.interfaces;

import java.util.Optional;

import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Stateprovince;

public interface AddressTypeService {

	public Iterable<Addresstype> findAll();
	
	public Addresstype saveAddressType(Addresstype addrtype);

	public Optional<Addresstype> findById(long id);
	
	public void delete(Addresstype addrtype);

	public Addresstype updateAddressType(Addresstype addrtype);
	
}
