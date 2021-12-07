package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.person.Address;
import com.example.demo.model.person.Businessentity;

@Repository
public interface BusinessEntityRepository extends CrudRepository<Businessentity, Integer>{
	
//	public void saveBusinessEntity(Businessentity businessentity);
//	public Address getBusinessEntity(Long id);
//	public void deleteBusinessEntity(Businessentity businessentity);
//	public void updateBusinessEntity(Businessentity businessentity);
}
