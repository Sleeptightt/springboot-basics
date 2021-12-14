package com.example.demo.front.controller.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.businessdelegate.interfaces.BusinessDelegate;
import com.example.demo.front.controller.interfaces.CustomerController;
import com.example.demo.front.model.sales.Customer;

@Controller
public class CustomerControllerImpl implements CustomerController{

	@Autowired
	private BusinessDelegate businessDelegate;
	
	public CustomerControllerImpl() {
		
	}

	@Override
	@GetMapping("/customer/")
	public String indexCustomer(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("customers", businessDelegate.findAllCustomers());
		}else {
			model.addAttribute("customers", businessDelegate.findAllCustomersByPerson(id.intValue()));
		}
		return "customer/index";
	}

	@Override
	@GetMapping("/customer/add")
	public String addCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		model.addAttribute("stores", businessDelegate.findAllStores());
		model.addAttribute("persons", businessDelegate.findAllPersons());
		return "customer/add-customer";
	}

	@Override
	@PostMapping("/customer/add")
	public String saveCustomer(@ModelAttribute("customer") @Validated Customer customer, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("customer", customer);
				model.addAttribute("stores", businessDelegate.findAllStores());
				model.addAttribute("persons", businessDelegate.findAllPersons());
				return "customer/add-customer";
			}
			businessDelegate.saveCustomer(customer);
		}
		return "redirect:/customer/";
	}

	@Override
	@GetMapping("/customer/del/{id}")
	public String deleteCustomer(@PathVariable("id") long id, Model model) {
		Customer customer = businessDelegate.findCustomerById((int)id);
		businessDelegate.deleteCustomer(customer);
		model.addAttribute("customers", businessDelegate.findAllCustomers());
		return "customer/index";
	}

	@Override
	@GetMapping("/customer/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Customer customer = businessDelegate.findCustomerById((int)id);
		model.addAttribute("customer", customer);
		model.addAttribute("stores", businessDelegate.findAllStores());
		model.addAttribute("persons", businessDelegate.findAllPersons());
		return "customer/update-customer";
	}

	@Override
	@PostMapping("/customer/edit/{id}")
	public String updateCustomer(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("customer") @Validated Customer customer, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("customer", customer);
				model.addAttribute("stores", businessDelegate.findAllStores());
				model.addAttribute("persons", businessDelegate.findAllPersons());
				return "customer/update-customer";		
			}
			businessDelegate.editCustomer(customer);
			model.addAttribute("customers", businessDelegate.findAllCustomers());
		}
		return "redirect:/customer/";
	}

	
}
