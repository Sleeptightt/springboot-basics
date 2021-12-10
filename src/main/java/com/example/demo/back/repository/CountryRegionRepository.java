package com.example.demo.back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.back.model.person.Countryregion;

@Repository
public interface CountryRegionRepository extends CrudRepository<Countryregion, Integer>{
	
//	public void saveCountryRegion(Countryregion countryregion);
//	public Address getCountryRegion(Long id);
//	public void deleteCountryRegion(Countryregion countryregion);
//	public void updateCountryRegion(Countryregion countryregion);
}
