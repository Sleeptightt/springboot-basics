package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Businessentity;

public interface BusinessEntityService {
	
	public Iterable<Businessentity> findAll();
	
	public Optional<Businessentity> findById(long id);
	
	public void delete(Integer id);
	
	public Businessentity saveBusinessEntity(Businessentity ben);
	
	public Businessentity updateBusinessEntity(Businessentity ben);
}
