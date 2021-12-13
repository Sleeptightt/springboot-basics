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
	
	public void delete(BusinessentityaddressPK id);

	public Businessentityaddress updateBusinessEntityAddress(Businessentityaddress benaddr);
	
	public Iterable<Businessentityaddress> findAllByAddress(Integer address);
	
	public Iterable<Businessentityaddress> findAllByAddresstype(Integer addresstype);
	
	public Iterable<Businessentityaddress> findAllByBusinessentity(Integer businessentity);
	
}
