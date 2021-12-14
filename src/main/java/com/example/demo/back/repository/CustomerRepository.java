package com.example.demo.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.sales.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	
	List<Customer> findAllByPersonid(Integer personid);
	
}
