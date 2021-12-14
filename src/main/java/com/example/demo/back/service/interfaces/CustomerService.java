package com.example.demo.back.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.demo.front.model.sales.Customer;


public interface CustomerService {

	public Iterable<Customer> findAll();
	
	public Customer saveCustomer(Customer customer);

	public Optional<Customer> findById(long id);
	
	public void delete(Integer id);

	public Customer updateCustomer(Customer customer);
	
	public List<Customer> findAllByPersonid(Integer personid);
}
