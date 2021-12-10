package com.example.demo.back.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.Stateprovince;

@Repository
public interface StateProvinceRepository extends CrudRepository<Stateprovince, Integer>{
	
//	public void saveStateProvince(Stateprovince stateprovince);
//	public Address getStateProvince(Long id);
//	public void deleteStateProvince(Stateprovince stateprovince);
//	public void updateStateProvince(Stateprovince stateprovince);
}
