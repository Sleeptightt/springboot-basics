package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.front.model.sales.Store;

public interface StoreService {

	public Iterable<Store> findAll();
	
	public Store saveStore(Store store);

	public Optional<Store> findById(Integer id);
	
	public void delete(Integer id);

	public Store updateStore(Store store);
}
