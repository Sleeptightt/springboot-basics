package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Stateprovince;

public interface StateProvinceService {
	
	public Iterable<Stateprovince> findAll();
	
	public Stateprovince saveStateprovince(Stateprovince stprov);

	public Optional<Stateprovince> findById(long id);
	
	public void delete(Integer id);

	public Stateprovince updateStateprovince(Stateprovince stprov);
}
