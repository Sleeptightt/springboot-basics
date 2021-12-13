package com.example.demo.back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.sales.Store;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer>{
	
}