package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;

public interface BusinessEntityAddressService {

	public Iterable<Businessentityaddress> findAll();
	
	public Businessentityaddress saveBusinessEntityAddress(Businessentityaddress benaddr);

	public Optional<Businessentityaddress> findById(BusinessentityaddressPK id);
	
	public void delete(Businessentityaddress benaddr);

	public Businessentityaddress updateBusinessEntityAddress(Businessentityaddress benaddr);
	
	public Iterable<Businessentityaddress> findAllByAddress(Address addr);
	
	public Iterable<Businessentityaddress> findAllByAddresstype(Addresstype addrtype);
	
	public Iterable<Businessentityaddress> findAllByBusinessentity(Businessentity ben);
	
}
