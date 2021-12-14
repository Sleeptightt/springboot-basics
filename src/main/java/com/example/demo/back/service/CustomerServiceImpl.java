package com.example.demo.back.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.back.repository.CustomerRepository;
import com.example.demo.back.service.interfaces.CustomerService;
import com.example.demo.front.model.sales.Customer;

@Service
public class CustomerServiceImpl implements CustomerService{

	private CustomerRepository CustomerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository CustomerRepository) {
		this.CustomerRepository = CustomerRepository;
	}
	
	@Transactional
	public Customer updateCustomer(Customer customer) {
		if(customer == null)
			return null;
		Customer sp = CustomerRepository.findById(customer.getCustomerid()).get();
		sp.setModifieddate(customer.getModifieddate());
		return customer;
	}

	public boolean existsById(Integer stateid) {
		return CustomerRepository.existsById(stateid);
	}

	@Override
	public Iterable<Customer> findAll() {
		return CustomerRepository.findAll();
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return CustomerRepository.save(customer);
	}

	@Override
	public Optional<Customer> findById(long id) {
		return CustomerRepository.findById((int) id);
	}

	@Override
	public void delete(Integer id) {
		CustomerRepository.deleteById(id);
	}

	@Override
	public List<Customer> findAllByPersonid(Integer personid) {
		return CustomerRepository.findAllByPersonid(personid);
	}
}
