package com.example.demo.service.interfaces;

import java.util.Optional;

import com.example.demo.model.person.Person;
import com.example.demo.model.person.Stateprovince;

public interface StateProvinceService {
	
	public Iterable<Stateprovince> findAll();
	
	public Stateprovince saveStateprovince(Stateprovince stprov);

	public Optional<Stateprovince> findById(long id);
	
	public void delete(Stateprovince stprov);

	public Stateprovince updateStateprovince(Stateprovince stprov);
}
