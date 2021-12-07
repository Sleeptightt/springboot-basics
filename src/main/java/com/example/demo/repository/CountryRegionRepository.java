package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.person.Address;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Countryregion;

@Repository
public interface CountryRegionRepository extends CrudRepository<Countryregion, Integer>{
	
//	public void saveCountryRegion(Countryregion countryregion);
//	public Address getCountryRegion(Long id);
//	public void deleteCountryRegion(Countryregion countryregion);
//	public void updateCountryRegion(Countryregion countryregion);
}
