package com.example.demo.back.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.back.service.interfaces.CustomerService;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.sales.Customer;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping
    public Iterable<Customer> getCustomers() {
        return customerService.findAll();
    }
	
    @PostMapping
    public void addCustomer(@RequestBody Customer customer) {
    	customerService.saveCustomer(customer);
    }
	
	@PutMapping
    public void updateCustomer(@RequestBody Customer customer) {
		customerService.updateCustomer(customer);
    }
	
	@GetMapping("/{id}")
    public Customer getById(@PathVariable("id") Integer id) {
        return customerService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        customerService.delete(id);
    }
	
	@GetMapping("/search/findAllByPerson")
    public List<Customer> getAllByPerson(@RequestParam("person") Integer person) {
        return customerService.findAllByPersonid(person);
    }
}
